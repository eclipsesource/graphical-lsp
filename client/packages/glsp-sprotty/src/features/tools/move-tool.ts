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
// tslint:disable-next-line:max-line-length
import { Action, BoundsAware, ElementAndBounds, findParentByFeature, isBoundsAware, isMoveable, isSelectable, isViewport, MouseListener, MouseTool, Point, SModelElement } from "sprotty/lib";
import { Tool } from "./tool-manager";

/**
 * A mouse tool that is optimized for Client/Server operation.
 * Once a move is peformed, a `ChangeBoundsAction` is sent to the server
 * only at the end of the interaction.
 * This is differnet from Sprotty's implementation of the move, as it
 * sends client-side live updates with `ChangeBoundsAction`.
 */
@injectable()
export class MoveTool implements Tool {

    static ID = "glsp.movetool";
    readonly id = MoveTool.ID;

    protected moveMouseListener: MoveMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool) { }

    enable() {
        this.moveMouseListener = new MoveMouseListener();
        this.mouseTool.register(this.moveMouseListener);
    }

    disable() {
        this.mouseTool.deregister(this.moveMouseListener);
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

        // rely on the Sprotty move tool to update the element bounds and simply collect the selected elements
        // if the Sprotty move tool is not used at some point, we need to update the bounds with the position delta
        const newBounds: ElementAndBounds[] = [];
        target.root.index.all()
            .filter(element => isBoundsAware(element) && isSelected(element))
            .map(element => element as SModelElement & BoundsAware)
            .forEach(element => newBounds.push({
                elementId: element.id,
                newBounds: {
                    x: element.bounds.x,
                    y: element.bounds.y,
                    width: element.bounds.width,
                    height: element.bounds.height
                }
            }));
        this.resetPosition();
        const changeBoundsAction = new ChangeBoundsAction(newBounds);
        return [changeBoundsAction];
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

function isSelected(element: SModelElement) {
    return isSelectable(element) && element.selected
}

export class ChangeBoundsAction implements Action {

    public static readonly KIND = "executeOperation_change-bounds";
    public kind = ChangeBoundsAction.KIND;

    constructor(public newBounds: ElementAndBounds[]) { }
}