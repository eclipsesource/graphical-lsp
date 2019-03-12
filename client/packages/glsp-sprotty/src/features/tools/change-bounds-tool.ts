/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
import { Action } from "sprotty/lib";
import { Bounds } from "sprotty/lib";
import { BoundsAware } from "sprotty/lib";
import { ChangeBoundsOperationAction } from "../operation/operation-actions";
import { ElementAndBounds } from "sprotty/lib";
import { FeedbackMoveMouseListener } from "../tool-feedback/change-bounds-tool-feedback";
import { GLSP_TYPES } from "../../types";
import { HideChangeBoundsToolResizeFeedbackAction } from "../tool-feedback/change-bounds-tool-feedback";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import { KeyTool } from "sprotty/lib";
import { MouseTool } from "sprotty/lib";
import { Point } from "sprotty/lib";
import { ResizeHandleLocation } from "../change-bounds/model";
import { SelectionTracker } from "../select/selection-tracker";
import { SetBoundsAction } from "sprotty/lib";
import { ShowChangeBoundsToolResizeFeedbackAction } from "../tool-feedback/change-bounds-tool-feedback";
import { SModelElement } from "sprotty/lib";
import { SParentElement } from "sprotty/lib";
import { SResizeHandle } from "../change-bounds/model";
import { Tool } from "sprotty/lib";

import { findParentByFeature } from "sprotty/lib";
import { forEachElement } from "../../utils/smodel-util";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isBoundsAwareMoveable } from "../change-bounds/model";
import { isResizeable } from "../change-bounds/model";
import { isSelectedBoundsAware } from "../../utils/smodel-util";
import { isViewport } from "sprotty/lib";

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
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher) { }

    enable() {
        // install feedback move mouse listener for client-side move updates
        this.feedbackMoveMouseListener = new FeedbackMoveMouseListener();
        this.mouseTool.register(this.feedbackMoveMouseListener);

        // instlal change bounds listener for client-side resize updates and server-side updates
        this.changeBoundsListener = new ChangeBoundsListener(this);
        this.mouseTool.register(this.changeBoundsListener);
        this.keyTool.register(this.changeBoundsListener);
        this.feedbackDispatcher.registerFeedback(this, [new ShowChangeBoundsToolResizeFeedbackAction])
    }

    disable() {
        this.mouseTool.deregister(this.changeBoundsListener);
        this.keyTool.deregister(this.changeBoundsListener);
        this.mouseTool.deregister(this.feedbackMoveMouseListener);
        this.feedbackDispatcher.deregisterFeedback(this, [new HideChangeBoundsToolResizeFeedbackAction])
    }

    dispatchFeedback(actions: Action[]) {
        this.feedbackDispatcher.registerFeedback(this, actions);
    }
}

class ChangeBoundsListener extends SelectionTracker {
    // members for calculating the correct position change
    private lastDragPosition: Point | undefined = undefined;
    private positionDelta: Point = { x: 0, y: 0 };

    // members for resize mode
    private activeResizeElementId: string | undefined = undefined;
    private activeResizeHandle: SResizeHandle | undefined = undefined;

    constructor(protected tool: ChangeBoundsTool) {
        super();
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
                this.tool.dispatchFeedback([new HideChangeBoundsToolResizeFeedbackAction()]);
                const moveableElement = findParentByFeature(target, isBoundsAwareMoveable);
                if (moveableElement) {
                    // only allow one element to have the element resize handles
                    this.activeResizeElementId = moveableElement.id;
                    this.tool.dispatchFeedback([new ShowChangeBoundsToolResizeFeedbackAction(this.activeResizeElementId)]);
                }
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
            this.resetPosition();
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
                this.tool.dispatchFeedback([new HideChangeBoundsToolResizeFeedbackAction()]);
            }
        }
        return actions;
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