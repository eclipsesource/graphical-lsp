/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 *  Philip Langer - migration to tool manager API
 *  Martin Fleck - migration to use of change bounds action
 ******************************************************************************/
import { inject, injectable } from "inversify";
import {
    Action, ElementAndBounds, findParentByFeature, isBoundsAware, isMoveable, isViewport, MouseListener, //
    MouseTool, Point, SModelElement
} from "sprotty/lib";
import { forEachElement, isSelectedBoundsAware } from "../../utils/smodel-util";
import { ChangeBoundsOperationAction } from "../operation/operation-actions";
import { FeedbackMoveMouseListener } from "../tool-feedback/move-tool-feedback";
import { Tool } from "../tool-manager/tool";

/**
 * A mouse tool that is optimized for Client/Server operation.
 * Once a move is peformed, a `ChangeBoundsAction` is sent to the server
 * only at the end of the interaction.
 * This is differnet from Sprotty's implementation of the move, as it
 * sends client-side live updates with `ChangeBoundsAction`.
 * To provide visual feedback, we install the `FeedbackMoveMouseListener`,
 * which updates the bounds by sending `MoveAction`s.
 */
@injectable()
export class MoveTool implements Tool {
    static ID = "glsp.movetool";
    readonly id = MoveTool.ID;

    protected moveMouseListener: MoveMouseListener;
    protected feedbackMoveMouseListener: FeedbackMoveMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool) { }

    enable() {
        this.feedbackMoveMouseListener = new FeedbackMoveMouseListener();
        this.mouseTool.register(this.feedbackMoveMouseListener);
        this.moveMouseListener = new MoveMouseListener();
        this.mouseTool.register(this.moveMouseListener);
    }

    disable() {
        this.mouseTool.deregister(this.moveMouseListener);
        this.mouseTool.deregister(this.feedbackMoveMouseListener);
    }

}

class MoveMouseListener extends MouseListener {
    private lastDragPosition: Point | undefined;
    private positionDelta: Point = { x: 0, y: 0 };

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        if (event.button === 0) {
            const moveable = findParentByFeature(target, isMoveable);
            if (moveable && isMoveable(moveable) && isBoundsAware(moveable)) {
                this.initPosition(event);
            } else {
                this.resetPosition();
            }
        }
        return [];
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        if (event.buttons === 0) {
            this.mouseUp(target, event);
        } else if (this.lastDragPosition) {
            this.updatePosition(target, event);
        }
        return [];
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        if (!this.hasPositionDelta()) {
            return [];
        }

        // rely on the FeedbackMoveMouseListener to update the element bounds and simply collect the selected elements
        const newBounds: ElementAndBounds[] = [];
        forEachElement(target, isSelectedBoundsAware, element => newBounds.push({
            elementId: element.id,
            newBounds: {
                x: element.bounds.x,
                y: element.bounds.y,
                width: element.bounds.width,
                height: element.bounds.height
            }
        }));

        this.resetPosition();
        return [new ChangeBoundsOperationAction(newBounds)];
    }

    private initPosition(event: MouseEvent) {
        this.lastDragPosition = { x: event.pageX, y: event.pageY };
    }

    private updatePosition(target: SModelElement, event: MouseEvent) {
        if (this.lastDragPosition) {
            const viewport = findParentByFeature(target, isViewport);
            const zoom = viewport ? viewport.zoom : 1;
            const dx = (event.pageX - this.lastDragPosition.x) / zoom;
            const dy = (event.pageY - this.lastDragPosition.y) / zoom;

            this.positionDelta = { x: this.positionDelta.x + dx, y: this.positionDelta.y + dy };
            this.lastDragPosition = { x: event.pageX, y: event.pageY };
        }
    }

    private resetPosition() {
        this.lastDragPosition = undefined;
        this.positionDelta = { x: 0, y: 0 };
    }

    private hasPositionDelta(): boolean {
        return this.positionDelta.x !== 0 || this.positionDelta.y !== 0;
    }
}
