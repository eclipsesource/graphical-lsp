/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Camille Letavernier - initial API and implementation
 *  Philip Langer - migration to tool manager API
 *  Martin Fleck - migration to use of change bounds action
 ******************************************************************************/
import { inject, injectable, optional } from "inversify";
import {
    Action, Bounds, BoundsAware, ButtonHandlerRegistry, ElementAndBounds, findParentByFeature, isViewport, KeyTool, MouseTool, Point, //
    SetBoundsAction, SModelElement, SModelRoot, SParentElement
} from "sprotty/lib";
import { CommandStackObserver, ObservableCommandStack } from "../../base/command-stack";
import { forEachElement, getIndex, isSelectedBoundsAware } from "../../utils/smodel-util";
import { isBoundsAwareMoveable } from "../change-bounds/model";
import { ChangeBoundsOperationAction } from "../operation/operation-actions";
import { isResizeable, ResizeHandleLocation, SResizeHandle } from "../resize/model";
import { addResizeHandles, SwitchResizeModeAction } from "../resize/resize";
import { SelectionTracker } from "../select/selection-tracker";
import { FeedbackMoveMouseListener } from "../tool-feedback/move-tool-feedback";
import { Tool } from "../tool-manager/tool";

/**
 * The change bounds tool has the license to move multiple elements or resize a single element by implementing the ChangeBounds operation.
 * In contrast to Sprotty's implementation this tool only sends a `ChangeBoundsOperationAction` when an operation has finished and does not
 * provide client-side live updates to improve performance.
 *
 * | Operation | Client Update    | Server Update
 * +-----------+------------------+----------------------------
 * | Move      | MoveAction       | ChangeBoundsOperationAction
 * | Resize    | SetBoundsAction  | ChangeBoundsOperationAction
 *
 * To provide a visual client updates during move we install the `FeedbackMoveMouseListener` and to provide visual client updates during resize
 * and send the server updates we install the `ChangeBoundsListener`.
 */
@injectable()
export class ChangeBoundsTool implements Tool {
    static ID = "glsp.change-bounds-tool";
    readonly id = ChangeBoundsTool.ID;

    protected feedbackMoveMouseListener: FeedbackMoveMouseListener;
    protected changeBoundsListener: ChangeBoundsListener;
    protected selectionTracker: SelectionTracker;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(KeyTool) protected keyTool: KeyTool,
        @inject(ObservableCommandStack) protected commandStack: ObservableCommandStack,
        @inject(ButtonHandlerRegistry) @optional() protected buttonHandlerRegistry: ButtonHandlerRegistry) { }

    enable() {
        // install feedback move mouse listener for client-side move updates
        this.feedbackMoveMouseListener = new FeedbackMoveMouseListener();
        this.mouseTool.register(this.feedbackMoveMouseListener);

        // instlal change bounds listener for client-side resize updates and server-side updates
        this.changeBoundsListener = new ChangeBoundsListener(this.buttonHandlerRegistry);
        this.mouseTool.register(this.changeBoundsListener);
        this.commandStack.registerObserver(this.changeBoundsListener);
        this.keyTool.register(this.changeBoundsListener);
    }

    disable() {
        this.mouseTool.deregister(this.changeBoundsListener);
        this.commandStack.deregisterObserver(this.changeBoundsListener);
        this.keyTool.deregister(this.changeBoundsListener);
        this.mouseTool.deregister(this.feedbackMoveMouseListener);
    }
}

class ChangeBoundsListener extends SelectionTracker implements CommandStackObserver {
    // members for calculating the correct position change
    private lastDragPosition: Point | undefined = undefined;
    private positionDelta: Point = { x: 0, y: 0 };

    // members for resize mode
    private activeResizeElementId: string | undefined = undefined;
    private activeResizeHandle: SResizeHandle | undefined = undefined;

    constructor(buttonHandlerRegistry: ButtonHandlerRegistry) {
        super(buttonHandlerRegistry);
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        super.mouseDown(target, event);
        const actions: Action[] = [];
        if (event.button === 0) {
            let active: boolean = false;
            // check if we have a resize handle (only single-selection)
            if (target instanceof SResizeHandle) {
                this.activeResizeHandle = target;
                active = true;
            } else {
                // check if we have a moveable element (multi-selection allowed)
                const moveableElement = findParentByFeature(target, isBoundsAwareMoveable);

                // check if we have a resizeable element (only single-selection)
                const action = createSwitchResizeModeAction(target, this.getSelectedElementIDs());
                this.activeResizeElementId = action.elementsToActivate.length > 0 ? action.elementsToActivate[0] : undefined;
                actions.push(action);

                active = moveableElement !== undefined || this.activeResizeElementId !== undefined;
            }
            if (active) {
                this.initPosition(event);
            } else {
                this.resetPosition();
            }
        }
        return actions;
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        if (this.updatePosition(target, event)) {
            // rely on the FeedbackMoveMouseListener to update the element bounds of selected elements
            // consider resize handles ourselves
            return this.handleElementResize();
        }
        return [];
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        super.mouseUp(target, event);
        if (!this.hasPositionDelta()) {
            return [];
        }

        // no further bound changing, simply send the latest data to the server using a single change bounds action for all relevant elements
        const actions: Action[] = [];
        if (this.activeResizeHandle) {
            // An action. Resize, not move.
            const resizeElement = findParentByFeature(this.activeResizeHandle, isResizeable);
            if (this.isActiveResizeElement(resizeElement)) {
                createChangeBoundsAction(resizeElement).forEach(action => actions.push(action));
            }
        } else {
            // Bounds... Change Bounds.
            const newBounds: ElementAndBounds[] = [];
            forEachElement(target, isSelectedBoundsAware, element =>
                createElementAndBounds(element).forEach(bounds => newBounds.push(bounds)));
            if (newBounds.length > 0) {
                actions.push(new ChangeBoundsOperationAction(newBounds));
            }
        }
        this.resetPosition();
        return actions;
    }

    keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
        const actions: Action[] = [];
        if (this.activeResizeElementId) {
            super.keyDown(element, event);
            if (this.isMultiSelection()) {
                // no element should be in resize mode
                actions.push(createSwitchResizeModeAction(element, this.getSelectedElementIDs()));
            }
        }
        return actions;
    }

    beforeServerUpdate(model: SModelRoot): void {
        const isResizeElement = (element: SModelElement): element is SModelElement => this.isActiveResizeElement(element);
        forEachElement(model, isResizeElement, addResizeHandles);
    }

    private isActiveResizeElement(element: SModelElement | undefined): element is SParentElement & BoundsAware {
        return element !== undefined && element.id === this.activeResizeElementId;
    }

    private initPosition(event: MouseEvent) {
        this.lastDragPosition = { x: event.pageX, y: event.pageY };
    }

    private updatePosition(target: SModelElement, event: MouseEvent): boolean {
        if (this.lastDragPosition) {
            const viewport = findParentByFeature(target, isViewport);
            const zoom = viewport ? viewport.zoom : 1;
            const dx = (event.pageX - this.lastDragPosition.x) / zoom;
            const dy = (event.pageY - this.lastDragPosition.y) / zoom;

            this.positionDelta = { x: dx, y: dy };
            this.lastDragPosition = { x: event.pageX, y: event.pageY };
            return true;
        }
        return false;
    }

    private resetPosition() {
        this.activeResizeHandle = undefined;
        this.lastDragPosition = undefined;
        this.positionDelta = { x: 0, y: 0 };
    }

    private hasPositionDelta(): boolean {
        return this.positionDelta.x !== 0 || this.positionDelta.y !== 0;
    }

    private handleElementResize(): Action[] {
        if (!this.activeResizeHandle) {
            return [];
        }

        const actions: Action[] = [];
        const resizeElement = findParentByFeature(this.activeResizeHandle, isResizeable);
        if (this.isActiveResizeElement(resizeElement)) {
            switch (this.activeResizeHandle.location) {
                case ResizeHandleLocation.TopLeft:
                    createSetBoundsAction(resizeElement,
                        resizeElement.bounds.x + this.positionDelta.x,
                        resizeElement.bounds.y + this.positionDelta.y,
                        resizeElement.bounds.width - this.positionDelta.x,
                        resizeElement.bounds.height - this.positionDelta.y)
                        .forEach(action => actions.push(action));
                    break;
                case ResizeHandleLocation.TopRight:
                    createSetBoundsAction(resizeElement,
                        resizeElement.bounds.x,
                        resizeElement.bounds.y + this.positionDelta.y,
                        resizeElement.bounds.width + this.positionDelta.x,
                        resizeElement.bounds.height - this.positionDelta.y)
                        .forEach(action => actions.push(action));
                    break;
                case ResizeHandleLocation.BottomLeft:
                    createSetBoundsAction(resizeElement,
                        resizeElement.bounds.x + this.positionDelta.x,
                        resizeElement.bounds.y,
                        resizeElement.bounds.width - this.positionDelta.x,
                        resizeElement.bounds.height + this.positionDelta.y)
                        .forEach(action => actions.push(action));
                    break;
                case ResizeHandleLocation.BottomRight:
                    createSetBoundsAction(resizeElement,
                        resizeElement.bounds.x,
                        resizeElement.bounds.y,
                        resizeElement.bounds.width + this.positionDelta.x,
                        resizeElement.bounds.height + this.positionDelta.y)
                        .forEach(action => actions.push(action));
                    break;
            }
        }
        return actions;
    }
}

function createSwitchResizeModeAction(target: SModelElement, selectedElements: Set<string>): SwitchResizeModeAction {
    // all elements are deactivated by default
    const unresizeable: string[] = [];
    getIndex(target).all().forEach(element => unresizeable.push(element.id));

    // mark single resizeable element active if possible
    const resizeable: string[] = [];
    if (selectedElements.size === 1) {
        const resizeableElement = findParentByFeature(target, isResizeable);
        if (resizeableElement && selectedElements.has(resizeableElement.id)) {
            resizeable.push(resizeableElement.id);
        }
    }
    return new SwitchResizeModeAction(resizeable, unresizeable);
}

function createChangeBoundsAction(element: SModelElement & BoundsAware): Action[] {
    if (isValidBoundChange(element, element.bounds)) {
        return [new ChangeBoundsOperationAction([{ elementId: element.id, newBounds: element.bounds }])];
    }
    return [];
}

function createElementAndBounds(element: SModelElement & BoundsAware): ElementAndBounds[] {
    if (isValidBoundChange(element, element.bounds)) {
        return [{ elementId: element.id, newBounds: element.bounds }];
    }
    return [];
}

function createSetBoundsAction(element: SModelElement & BoundsAware, x: number, y: number, width: number, height: number): Action[] {
    const bounds = { x: x, y: y, width: width, height: height };
    if (isValidBoundChange(element, bounds)) {
        return [new SetBoundsAction([{ elementId: element.id, newBounds: bounds }])];
    }
    return [];
}

function isValidBoundChange(element: SModelElement & BoundsAware, bounds: Bounds): boolean {
    return bounds.width >= minWidth(element) && bounds.height >= minHeight(element);
}

function minWidth(element: SModelElement & BoundsAware): number {
    // currently there are no element-specific constraints
    return 1;
}

function minHeight(element: SModelElement & BoundsAware): number {
    // currently there are no element-specific constraints
    return 1;
}