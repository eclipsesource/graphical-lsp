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
import { inject, injectable } from "inversify";
import { VNode } from "snabbdom/vnode";
import {
    Action,
    Bounds,
    CommandExecutionContext,
    CommandResult,
    ElementMove,
    findParentByFeature,
    includes,
    isMoveable,
    isSelectable,
    isViewport,
    MouseListener,
    MoveAction,
    Point,
    SModelElement,
    SModelRoot,
    TYPES
} from "sprotty/lib";

import { GLSPViewerOptions } from "../../base/views/viewer-options";
import { isNotUndefined } from "../../utils/smodel-util";
import { getAbsolutePosition } from "../../utils/viewpoint-util";
import { addResizeHandles, isResizeable, removeResizeHandles } from "../change-bounds/model";
import { ApplyCursorCSSFeedbackAction, CursorCSS } from "./cursor-feedback";
import { FeedbackCommand } from "./model";

export class ShowChangeBoundsToolResizeFeedbackAction implements Action {
    kind = ShowChangeBoundsToolResizeFeedbackCommand.KIND;
    constructor(readonly elementId?: string) { }
}

export class HideChangeBoundsToolResizeFeedbackAction implements Action {
    kind = HideChangeBoundsToolResizeFeedbackCommand.KIND;
    constructor() { }
}

@injectable()
export class ShowChangeBoundsToolResizeFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'showChangeBoundsToolResizeFeedback';

    constructor(@inject(TYPES.Action) protected action: ShowChangeBoundsToolResizeFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): CommandResult {
        const index = context.root.index;
        index.all().filter(isResizeable).forEach(removeResizeHandles);

        if (isNotUndefined(this.action.elementId)) {
            const resizeElement = index.getById(this.action.elementId);
            if (isNotUndefined(resizeElement) && isResizeable(resizeElement)) {
                addResizeHandles(resizeElement);
            }
        }
        return context.root;
    }
}

@injectable()
export class HideChangeBoundsToolResizeFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'hideChangeBoundsToolResizeFeedback';

    constructor(@inject(TYPES.Action) protected action: HideChangeBoundsToolResizeFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): CommandResult {
        const index = context.root.index;
        index.all().filter(isResizeable).forEach(removeResizeHandles);
        return context.root;
    }
}

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
    hasCollided = false;
    constructor(protected glspViewerOptions: GLSPViewerOptions) { super(); }
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




    /**
    * Used to return the collision target(s) or the collision chain in case of multiple selected elements
    */
    getCollisionChain(target: SModelElement, element: SModelElement, dx: number, dy: number, collisionChain: SModelElement[]): SModelElement[] {
        if (isMoveable(element) && isResizeable(element)) {
            target.root.index.all()
                .filter(candidate => isSelectable(candidate) && element.id !== candidate.id && collisionChain.indexOf(candidate) < 0)
                .forEach(candidate => {
                    if (isMoveable(element) && isMoveable(candidate)) {
                        if (isResizeable(element) && isResizeable(candidate)) {
                            const futureBounds: Bounds = {
                                x: element.position.x + dx,
                                y: element.position.y + dy,
                                width: element.size.width,
                                height: element.size.height
                            };
                            if (isOverlappingBounds(futureBounds, candidate.bounds) && !isOverlappingBounds(element.bounds, candidate.bounds)) {
                                collisionChain.push(candidate);
                                if (candidate.selected) {
                                    // Check what the selected candidate will collide with and add it to the chain
                                    collisionChain.push.apply(collisionChain, this.getCollisionChain(target, candidate, dx, dy, collisionChain));

                                }
                            }
                        }
                    }
                });
        }

        return collisionChain;
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        if (event.buttons === 0)
            this.mouseUp(target, event);
        else if (this.lastDragPosition) {
            const viewport = findParentByFeature(target, isViewport);
            this.hasDragged = true;
            const zoom = viewport ? viewport.zoom : 1;
            const mousePoint: Point = getAbsolutePosition(target, event);
            const dx = (event.pageX - this.lastDragPosition.x) / zoom;
            const dy = (event.pageY - this.lastDragPosition.y) / zoom;
            const nodeMoves: ElementMove[] = [];
            let willCollide: boolean = false;
            let mouseOverElement: boolean = false;
            const collisionOccured: boolean = false;

            target.root.index.all()
                .filter(element => isSelectable(element) && element.selected)
                .forEach(element => {
                    if (isMoveable(element) && isResizeable(element)) {
                        // If noElementOverlap Option is set perform collision detection
                        if (this.glspViewerOptions.noElementOverlap) {
                            // After collision the mouse is back inside the element => change cursor back to default
                            if (this.hasCollided && includes(element.bounds, mousePoint)) {
                                mouseOverElement = true;
                                result.push(new ApplyCursorCSSFeedbackAction(CursorCSS.DEFAULT));
                            }
                            // Get only the valid, non-slected collision targets to avoid in-selection collisions
                            const collisionTargets: SModelElement[] = this.getCollisionChain(target, element, dx, dy, [])
                                .filter(collidingElement => isSelectable(collidingElement) && !collidingElement.selected);

                            if (collisionTargets.length > 0) {
                                collisionTargets.forEach(collisionTarget => {
                                    if (isResizeable(collisionTarget)) {
                                        willCollide = true;
                                        this.hasCollided = true;
                                        result.push(new ApplyCursorCSSFeedbackAction(CursorCSS.OVERLAP_FORBIDDEN));
                                    }
                                });
                            }
                        }
                    }
                    if (isMoveable(element) && !collisionOccured && ((!willCollide && !this.hasCollided) ||
                        (this.hasCollided && !willCollide && mouseOverElement))) {
                        this.hasCollided = false;
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
            if (nodeMoves.length > 0 && !this.hasCollided) {
                result.push(new MoveAction(nodeMoves, false));
            }
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


/**
* Used to check if 1D boxes (lines) overlap
*/
export function isOverlapping1Dimension(x1: number, width1: number, x2: number, width2: number): boolean {
    return x1 + width1 >= x2 && x2 + width2 >= x1;
}

/**
* Used to check if 2 bounds are overlapping
*/
export function isOverlappingBounds(bounds1: Bounds, bounds2: Bounds): boolean {
    return isOverlapping1Dimension(bounds1.x, bounds1.width, bounds2.x, bounds2.width) &&
        isOverlapping1Dimension(bounds1.y, bounds1.height, bounds2.y, bounds2.height);

}
