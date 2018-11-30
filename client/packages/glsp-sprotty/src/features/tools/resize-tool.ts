/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Martin Fleck - initial API and implementation
 ******************************************************************************/
import { inject, injectable } from "inversify";
import {
    Action, ActionDispatcher, Bounds, BoundsAware, ElementAndBounds, findParentByFeature, isViewport, MouseListener, MouseTool, Point, //
    SetBoundsAction, SModelElement, SModelRoot, SParentElement, TYPES
} from "sprotty/lib";
import { CommandStackObserver, ObservableCommandStack } from "../../base/command-stack";
import { forEachElement, getIndex } from "../../utils/smodel-util";
import { ChangeBoundsOperationAction } from "../operation/operation-actions";
import { isResizeable, ResizeHandleLocation, SResizeHandle } from "../resize/model";
import { addResizeHandles, SwitchResizeModeAction } from "../resize/resize";
import { FeedbackMoveMouseListener } from "../tool-feedback/move-tool-feedback";
import { Tool } from "../tool-manager/tool";

/**
 * A tool that allows to resize elements and is optimized for Client/Server operation.
 * Once a resize is peformed, a `ChangeBoundsAction` is sent to the server only at the end of the interaction.
 * Specifically, this tool supports resizing through mouse wheel and keyboard interaction.
 * On the client side we use `SetBoundsAction` to trigger a re-rendering.
 */
@injectable()
export class ResizeTool implements Tool {

    static ID = "glsp.resizetool";
    readonly id = ResizeTool.ID;

    protected feedbackMoveMouseListener: FeedbackMoveMouseListener;
    protected resizeListener: ResizeListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(TYPES.IActionDispatcher) protected actionDispatcher: ActionDispatcher,
        @inject(ObservableCommandStack) protected commandStack: ObservableCommandStack) { }

    enable() {
        this.feedbackMoveMouseListener = new FeedbackMoveMouseListener();
        this.mouseTool.register(this.feedbackMoveMouseListener);

        this.resizeListener = new ResizeListener(this.actionDispatcher);
        this.mouseTool.register(this.resizeListener);
        this.commandStack.registerObserver(this.resizeListener);
    }

    disable() {
        this.mouseTool.deregister(this.resizeListener);
        this.commandStack.deregisterObserver(this.resizeListener);

        this.mouseTool.deregister(this.feedbackMoveMouseListener);
    }
}

class ResizeListener extends MouseListener implements CommandStackObserver {
    private activeResizeElementId: string | undefined = undefined;
    private resizeHandle: SResizeHandle | undefined = undefined;
    private lastDragPosition: Point | undefined;

    constructor(protected actionDispatcher: ActionDispatcher) {
        super();
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        const actions: Action[] = [];
        if (event.button === 0) {
            if (target instanceof SResizeHandle) {
                this.resizeHandle = target;
                this.lastDragPosition = { x: event.pageX, y: event.pageY };
            } else {
                // all elements are deactivated by default
                const unresizeable: string[] = [];
                getIndex(target).all().forEach(element => unresizeable.push(element.id));

                // mark single resizeable element active if possible
                const resizeable: string[] = [];
                const resizeableElement = findParentByFeature(target, isResizeable);
                if (resizeableElement) {
                    resizeable.push(resizeableElement.id);
                    this.activeResizeElementId = resizeableElement.id;
                } else {
                    this.activeResizeElementId = undefined;
                }
                actions.push(new SwitchResizeModeAction(resizeable, unresizeable));
            }
        }
        return actions;
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        const actions: Action[] = [];
        if (this.resizeHandle) {
            console.log("resize mouse up: " + event.button);
            const resizeElement = findParentByFeature(this.resizeHandle, isResizeable);
            if (this.isActiveResizeElement(resizeElement)) {
                // no further resizing, simply send the latest data to the server
                createResizeActions(serverAction, resizeElement,
                    resizeElement.bounds.x, resizeElement.bounds.y, resizeElement.bounds.width, resizeElement.bounds.height)
                    .forEach(action => actions.push(action));
            }
            this.resetDraggingHandle();
        }
        return actions;
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        const actions: Action[] = [];
        if (event.buttons === 0) {
            this.mouseUp(target, event);
        } else if (this.resizeHandle && this.lastDragPosition) {
            const resizeElement = findParentByFeature(this.resizeHandle, isResizeable);
            if (this.isActiveResizeElement(resizeElement)) {
                const viewport = findParentByFeature(target, isViewport);
                const zoom = viewport ? viewport.zoom : 1;
                const dx = (event.pageX - this.lastDragPosition.x) / zoom;
                const dy = (event.pageY - this.lastDragPosition.y) / zoom;

                // TODO: What to do with negative width and height?
                switch (this.resizeHandle.location) {
                    case ResizeHandleLocation.TopLeft:
                        createResizeActions(clientAction, resizeElement,
                            resizeElement.bounds.x + dx,
                            resizeElement.bounds.y + dy,
                            resizeElement.bounds.width - dx,
                            resizeElement.bounds.height - dy)
                            .forEach(action => actions.push(action));
                        break;
                    case ResizeHandleLocation.TopRight:
                        createResizeActions(clientAction, resizeElement,
                            resizeElement.bounds.x,
                            resizeElement.bounds.y + dy,
                            resizeElement.bounds.width + dx,
                            resizeElement.bounds.height - dy)
                            .forEach(action => actions.push(action));
                        break;
                    case ResizeHandleLocation.BottomLeft:
                        createResizeActions(clientAction, resizeElement,
                            resizeElement.bounds.x + dx,
                            resizeElement.bounds.y,
                            resizeElement.bounds.width - dx,
                            resizeElement.bounds.height + dy)
                            .forEach(action => actions.push(action));
                        break;
                    case ResizeHandleLocation.BottomRight:
                        createResizeActions(clientAction, resizeElement,
                            resizeElement.bounds.x,
                            resizeElement.bounds.y,
                            resizeElement.bounds.width + dx,
                            resizeElement.bounds.height + dy)
                            .forEach(action => actions.push(action));
                        break;
                }
                this.lastDragPosition = { x: event.pageX, y: event.pageY };
                console.log("send actions: " + actions.length);
            }
        }
        return actions;
    }

    beforeServerUpdate(model: SModelRoot): void {
        const isResizeElement = (element: SModelElement): element is SModelElement => this.isActiveResizeElement(element);
        forEachElement(model, isResizeElement, addResizeHandles);
    }

    private resetDraggingHandle() {
        this.resizeHandle = undefined;
        this.lastDragPosition = undefined;
    }

    private isActiveResizeElement(element: SModelElement | undefined): element is SParentElement & BoundsAware {
        return element !== undefined && element.id === this.activeResizeElementId;
    }
}

const clientAction = SetBoundsAction;
const serverAction = ChangeBoundsOperationAction;

function createResizeActions(ActionClass: (new (bounds: ElementAndBounds[]) => Action), element: SModelElement & SParentElement & BoundsAware,
    x: number, y: number, width: number, height: number): Action[] {
    const bounds = { x: x, y: y, width: width, height: height };
    if (isValidResize(element, bounds)) {
        return [new ActionClass([{ elementId: element.id, newBounds: bounds }])];
    }
    return [];
}

function isValidResize(element: SModelElement & SParentElement & BoundsAware, bounds: Bounds): boolean {
    return bounds.width >= minWidth(element) && bounds.height >= minHeight(element);
}

function minWidth(element: SModelElement & SParentElement & BoundsAware): number {
    // currently there are no element-specific constraints
    return 1;
}

function minHeight(element: SModelElement & SParentElement & BoundsAware): number {
    // currently there are no element-specific constraints
    return 1;
}