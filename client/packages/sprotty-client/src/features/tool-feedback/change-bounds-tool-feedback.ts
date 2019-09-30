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
    BoundsAware,
    CommandExecutionContext,
    CommandResult,
    ElementMove,
    findParentByFeature,
    includes,
    isMoveable,
    isSelectable,
    isViewport,
    Locateable,
    MouseListener,
    MoveAction,
    Point,
    PointToPointLine,
    SModelElement,
    SModelRoot,
    TYPES
} from "sprotty/lib";
import { FluentIterable, toArray } from "sprotty/lib/utils/iterable";

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
                            if (isOverlappingBounds(futureBounds, candidate.bounds) && (!isOverlappingBounds(element.bounds, candidate.bounds) || element.type === "Ghost")) {
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

    /**
    * Returns bounds centered around the point
    */
    getCenteredBoundsToPointer(mousePoint: Point, bounds: Bounds): Bounds {
        const middleX = mousePoint.x - bounds.width / 2;
        const middleY = mousePoint.y - bounds.height / 2;
        const shiftedBounds: Bounds = { x: middleX, y: middleY, width: bounds.width, height: bounds.height };
        return shiftedBounds;
    }

    // Remove this and use the one from the improved routing branch
    getDistanceBetweenParallelLines(p1: Point, p2: Point, secondLine: PointToPointLine): Number {
        const numerator: number = Math.abs((secondLine.a * p1.x) + (secondLine.b * p1.y) - secondLine.c);
        const denominator: number = Math.sqrt(Math.pow(secondLine.a, 2) + Math.pow(secondLine.b, 2));
        return numerator / denominator;
    }

    /**
     * Snaps the element to the target in case of a collision
     */
    getSnappedBounds(element: SModelElement & BoundsAware, target: SModelElement & BoundsAware): Bounds {
        let snappedBounds = element.bounds;

        // Build corner points
        const elementTopLeft = {
            x: element.bounds.x,
            y: element.bounds.y
        };
        const elementTopRight = {
            x: element.bounds.x + element.bounds.width,
            y: element.bounds.y
        };
        const elementBottomLeft = {
            x: element.bounds.x,
            y: element.bounds.y + element.bounds.height
        };
        const elementBottomRight = {
            x: element.bounds.x + element.bounds.width,
            y: element.bounds.y + element.bounds.height
        };
        const targetTopLeft = {
            x: target.bounds.x,
            y: target.bounds.y
        };
        const targetTopRight = {
            x: target.bounds.x + target.bounds.width,
            y: target.bounds.y
        };
        const targetBottomLeft = {
            x: target.bounds.x,
            y: target.bounds.y + target.bounds.height
        };
        const targetBottomRight = {
            x: target.bounds.x + target.bounds.width,
            y: target.bounds.y + target.bounds.height
        };

        // Build lines
        const targetTopLine = new PointToPointLine(targetTopLeft, targetTopRight);
        const targetBottomLine = new PointToPointLine(targetBottomLeft, targetBottomRight);
        const targetLeftLine = new PointToPointLine(targetTopLeft, targetBottomLeft);
        const targetRightLine = new PointToPointLine(targetTopRight, targetBottomRight);

        // Compute distances
        const distanceTop = this.getDistanceBetweenParallelLines(elementBottomLeft, elementBottomRight, targetTopLine);
        const distanceBottom = this.getDistanceBetweenParallelLines(elementTopLeft, elementTopRight, targetBottomLine);
        const distanceLeft = this.getDistanceBetweenParallelLines(elementTopLeft, elementBottomLeft, targetRightLine);
        const distanceRight = this.getDistanceBetweenParallelLines(elementTopRight, elementBottomRight, targetLeftLine);

        const minimumCandidates: number[] = [];

        // Overlap on the horizontal lines
        if (isOverlapping1Dimension(element.bounds.x, element.bounds.width, target.bounds.x, target.bounds.width)) {
            minimumCandidates.push(distanceTop.valueOf());
            minimumCandidates.push(distanceBottom.valueOf());
        }
        // Overlap on the horizontal lines
        if (isOverlapping1Dimension(element.bounds.y, element.bounds.height, target.bounds.y, target.bounds.height)) {
            minimumCandidates.push(distanceLeft.valueOf());
            minimumCandidates.push(distanceRight.valueOf());
        }

        // Get minimum distance and then snap accordingly
        minimumCandidates.sort((a, b) => a - b);
        const minimumDistance = minimumCandidates[0];

        if (minimumDistance === distanceTop) {
            snappedBounds = {
                x: element.bounds.x,
                y: target.bounds.y - 1 - element.bounds.height,
                width: element.bounds.width,
                height: element.bounds.height
            };
        }
        if (minimumDistance === distanceBottom) {
            snappedBounds = {
                x: element.bounds.x,
                y: target.bounds.y + target.bounds.height + 1,
                width: element.bounds.width,
                height: element.bounds.height
            };
        }
        if (minimumDistance === distanceLeft) {
            snappedBounds = {
                x: target.bounds.x + target.bounds.width + 1,
                y: element.bounds.y,
                width: element.bounds.width,
                height: element.bounds.height
            };
        }
        if (minimumDistance === distanceRight) {
            snappedBounds = {
                x: target.bounds.x - 1 - element.bounds.width,
                y: element.bounds.y,
                width: element.bounds.width,
                height: element.bounds.height
            };
        }
        return snappedBounds;
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
            let isValidMove: boolean = true;

            target.root.index.all()
                .filter(element => isSelectable(element) && element.selected)
                .forEach(element => {
                    if (isMoveable(element) && isResizeable(element)) {
                        // If noElementOverlap Option is set perform collision detection
                        if (this.glspViewerOptions.noElementOverlap) {
                            isValidMove = this.attemptNonCollidingMove(element, mousePoint, target, dx, dy, result);
                        }
                    }
                    if (isMoveable(element) && isValidMove) {
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

    /*
    * Attempts to perform a non-colliding element move. Returns true if successful and false otherwise
    */
    attemptNonCollidingMove(element: SModelElement & BoundsAware & Locateable, mousePoint: Point, target: SModelElement, dx: number, dy: number, result: Action[]): boolean {
        let mouseOverElement: boolean = false;
        let willOverlap: boolean = false;
        // Create ghost element to check possible bounds
        const ghostElement = Object.create(element);

        ghostElement.bounds = this.getCenteredBoundsToPointer(mousePoint, element.bounds);
        // Set type to Ghost to keep tracking it through elements
        ghostElement.type = "Ghost";
        ghostElement.id = element.id;
        // Check collision for gost element (to see when it has passed beyond obstacle)
        const collisionTargetsGhost: SModelElement[] = this.getCollisionChain(target, ghostElement, dx, dy, [])
            .filter(collidingElement => isSelectable(collidingElement) && !collidingElement.selected);

        // After collision the mouse is back inside the element => change cursor back to default
        if (this.hasCollided && includes(element.bounds, mousePoint)) {
            mouseOverElement = true;
            result.push(new ApplyCursorCSSFeedbackAction(CursorCSS.DEFAULT));
        }

        const selectedElements: FluentIterable<SModelElement> = target.root.index.all()
            .filter(selected => isSelectable(selected) && selected.selected);

        // If the ghost element has moved beyond the obstacle move the actual element there aswell
        // But only if a single element is selected (multi-selection jumps are not supported)
        if (this.hasCollided && collisionTargetsGhost.length === 0 && toArray(selectedElements).length === 1) {
            mouseOverElement = true;
            result.push(new ApplyCursorCSSFeedbackAction(CursorCSS.DEFAULT));

            if (element.id === ghostElement.id) {
                element.bounds = ghostElement.bounds;
            }
        }
        // Get only the valid, non-slected collision targets to avoid in-selection collisions
        const collisionTargets: SModelElement[] = this.getCollisionChain(target, element, dx, dy, [])
            .filter(collidingElement => isSelectable(collidingElement) && !collidingElement.selected);

        if (collisionTargets.length > 0) {
            collisionTargets.forEach(collisionTarget => {
                if (isResizeable(collisionTarget)) {
                    // Only snap on first collision to avoid erratic jumps
                    if (!this.hasCollided) {
                        const snappedBounds = this.getSnappedBounds(element, collisionTarget);
                        const snapMoves: ElementMove[] = [];
                        snapMoves.push({
                            elementId: element.id,
                            fromPosition: {
                                x: element.position.x,
                                y: element.position.y
                            },
                            toPosition: {
                                x: snappedBounds.x,
                                y: snappedBounds.y
                            }
                        });
                        result.push(new MoveAction(snapMoves, false));
                    }
                    willOverlap = true;
                    this.hasCollided = true;
                    result.push(new ApplyCursorCSSFeedbackAction(CursorCSS.OVERLAP_FORBIDDEN));
                }
            });
        }
        if ((!willOverlap && !this.hasCollided) ||
            (this.hasCollided && !willOverlap && mouseOverElement)) {
            this.hasCollided = false;
            return true;
        }
        return false;
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
