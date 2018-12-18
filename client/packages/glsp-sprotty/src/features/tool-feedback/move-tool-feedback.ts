/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Philip Langer - initial API and implementation
 ******************************************************************************/
import { VNode } from "snabbdom/vnode";
import {
    Action, ElementMove, findParentByFeature, isMoveable, isSelectable, //
    isViewport, MouseListener, MoveAction, Point, SModelElement, SModelRoot
} from "sprotty/lib";

/**
 * This mouse listener provides visual feedback for moving by sending client-side
 * `MoveAction`s while elements are selected and dragged. This will also update
 * their bounds, which is important, as it is not only required for rendering
 * the visual feedback but also the basis for sending the change to the server
 * (see also `tools/MoveTool`).
 */
export class FeedbackMoveMouseListener extends MouseListener {

    hasDragged = false;
    lastDragPosition: Point | undefined;

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        if (event.button === 0) {
            const moveable = findParentByFeature(target, isMoveable);
            if (moveable !== undefined) {
                this.lastDragPosition = { x: event.pageX, y: event.pageY };
            } else {
                this.lastDragPosition = undefined;
            }
            this.hasDragged = false;
        }
        return [];
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        if (event.buttons === 0)
            this.mouseUp(target, event);
        else if (this.lastDragPosition) {
            const viewport = findParentByFeature(target, isViewport);
            this.hasDragged = true;
            const zoom = viewport ? viewport.zoom : 1;
            const dx = (event.pageX - this.lastDragPosition.x) / zoom;
            const dy = (event.pageY - this.lastDragPosition.y) / zoom;
            const nodeMoves: ElementMove[] = [];
            target.root.index.all()
                .filter(element => isSelectable(element) && element.selected)
                .forEach(element => {
                    if (isMoveable(element)) {
                        nodeMoves.push({
                            elementId: element.id,
                            fromPosition: {
                                x: element.position.x,
                                y: element.position.y
                            },
                            toPosition: {
                                x: element.position.x + dx,
                                y: element.position.y + dy
                            }
                        });
                    }
                });
            this.lastDragPosition = { x: event.pageX, y: event.pageY };
            if (nodeMoves.length > 0)
                result.push(new MoveAction(nodeMoves, false));
        }
        return result;
    }

    mouseEnter(target: SModelElement, event: MouseEvent): Action[] {
        if (target instanceof SModelRoot && event.buttons === 0)
            this.mouseUp(target, event);
        return [];
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        this.hasDragged = false;
        this.lastDragPosition = undefined;
        return [];
    }

    decorate(vnode: VNode, element: SModelElement): VNode {
        return vnode;
    }
}