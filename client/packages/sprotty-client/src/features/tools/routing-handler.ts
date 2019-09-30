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
import {
    almostEquals,
    Bounds,
    BoundsAware,
    EdgeRouterRegistry,
    ElementAndBounds,
    isBoundsAware,
    isConnectable,
    isMoveable,
    isSelectable,
    Point,
    PointToPointLine,
    RoutedPoint,
    SEdge,
    Selectable,
    Side,
    SModelElement
} from "sprotty";

import { forEachElement, isSelected } from "../../utils/smodel-util";
import { isResizeable } from "../change-bounds/model";
import { isRoutable } from "../reconnect/model";
import { SwitchRoutingModeAction } from "../tool-feedback/edge-edit-tool-feedback";
import { ChangeBoundsTool, createElementAndBounds, ElementAndRoutingPoints, SideAndDistance } from "./change-bounds-tool";

@injectable()
export class RoutingHandler {

    constructor(@inject(EdgeRouterRegistry) protected edgeRouterRegistry: EdgeRouterRegistry) { }

    // Spacing unit used for rerouting in case of collision
    private SPACING: number = 5;

    getNewRoutingPoints(target: SModelElement, newBounds: ElementAndBounds[], tool: ChangeBoundsTool): ElementAndRoutingPoints[] {
        const updatedRoutingPoints: ElementAndRoutingPoints[] = [];

        forEachElement(target, isNonRoutableBoundsAware, element => {

            createElementAndBounds(element).forEach(bounds => {
                // Push new bounds only for selected elements
                if (isSelected(element)) {
                    newBounds.push(bounds);
                }
            });

            updatedRoutingPoints.push.apply(updatedRoutingPoints, this.getConnectedRoutingPoints(element));
            // Going through the routing points
            updatedRoutingPoints.forEach(elementAndRoutingPoint => {
                tool.dispatchFeedback([new SwitchRoutingModeAction([elementAndRoutingPoint.elementId])]);
                // In case of a straight edge, no routing points
                if (elementAndRoutingPoint.routingPoints.length === 0) {
                    this.checkOverlapBetweenAnchors(target, elementAndRoutingPoint);
                }
                // Check Overlap between the source and first routing point
                this.checkOverlapBetweenSourceAndPoint(target, elementAndRoutingPoint);
                // Check Overlap between the target and the last routing point
                this.checkOverlapBetweenTargetAndPoint(target, elementAndRoutingPoint);
                const nrRoutingPoints = elementAndRoutingPoint.routingPoints.length;
                for (let i = 0; i < nrRoutingPoints - 1; i++) {
                    // Take a segment between 2 routing points and check it
                    const firstPoint: Point = elementAndRoutingPoint.routingPoints[i];
                    const secondPoint: Point = elementAndRoutingPoint.routingPoints[i + 1];
                    target.root.index.all()
                        .filter(element2 => isSelectable(element2))
                        .forEach(element2 => {
                            if (isMoveable(element2) && isResizeable(element2)) {
                                const isOverlapping = this.isLineOverlappingElement(firstPoint, secondPoint, element2.bounds);
                                if (isOverlapping) {
                                    const closestSideAndDistance = this.getClosestSideAndDistance(firstPoint, secondPoint, element2.bounds);
                                    const shiftedPoints: Point[] = this.getShiftedRoutingPoints(firstPoint, secondPoint, closestSideAndDistance);
                                    elementAndRoutingPoint.routingPoints[i] = shiftedPoints[0];
                                    elementAndRoutingPoint.routingPoints[i + 1] = shiftedPoints[1];
                                }
                            }
                        });
                }
            });
        });
        return updatedRoutingPoints;
    }

    generateNewRoutingPoint(sideAndDistance: SideAndDistance, anchor: Point, routingPoint: Point, bounds: Bounds): Point {
        let newPoint: Point;
        // Create additional point
        if (sideAndDistance.side === Side.BOTTOM || sideAndDistance.side === Side.TOP) {
            // Add point left of the bounds
            if (routingPoint.x > anchor.x) {
                newPoint = {
                    x: bounds.x - this.SPACING,
                    y: anchor.y
                };
            } else {
                // Add point right of the bounds
                newPoint = {
                    x: bounds.x + bounds.width + this.SPACING,
                    y: anchor.y
                };
            }
        } else {
            // Add point to the top of bounds
            if (anchor.y < routingPoint.y) {
                newPoint = {
                    x: anchor.x,
                    y: bounds.y - this.SPACING
                };
            } else {
                // Add point to the bottom of bounds
                newPoint = {
                    x: anchor.x,
                    y: bounds.y + bounds.height + this.SPACING
                };
            }
        }
        return newPoint;
    }

    checkOverlapBetweenTargetAndPoint(target: SModelElement, elementAndRoutingPoint: ElementAndRoutingPoints) {
        const targetAnchor: Point = elementAndRoutingPoint.target;
        const routingPoint: Point = elementAndRoutingPoint.routingPoints[elementAndRoutingPoint.routingPoints.length - 1];
        target.root.index.all()
            .filter(element => isSelectable(element))
            .forEach(element => {
                if (isMoveable(element) && isResizeable(element)) {
                    const isOverlapping = this.isLineOverlappingElement(targetAnchor, routingPoint, element.bounds);
                    const edge = target.root.index.getById(elementAndRoutingPoint.elementId);
                    let edgeSourceId;
                    let edgeTargetId;
                    let newPoint: Point;
                    if (edge instanceof SEdge && isRoutable(edge)) {
                        edgeSourceId = edge.sourceId;
                        edgeTargetId = edge.targetId;
                    }
                    if (isOverlapping && element.id !== edgeSourceId && element.id !== edgeTargetId) {
                        const sideAndDistance = this.getClosestSideAndDistance(targetAnchor, routingPoint, element.bounds);
                        // Create additional point
                        newPoint = this.generateNewRoutingPoint(sideAndDistance, targetAnchor, routingPoint, element.bounds);
                        // Shift the old and new point
                        const shiftedPoints: Point[] = this.getShiftedRoutingPoints(newPoint, routingPoint, sideAndDistance);
                        elementAndRoutingPoint.routingPoints[elementAndRoutingPoint.routingPoints.length - 1] = shiftedPoints[1];
                        elementAndRoutingPoint.routingPoints.push(shiftedPoints[0]);
                    }
                }
            });
    }

    checkOverlapBetweenSourceAndPoint(target: SModelElement, elementAndRoutingPoint: ElementAndRoutingPoints) {
        const source: Point = elementAndRoutingPoint.source;
        const routingPoint: Point = elementAndRoutingPoint.routingPoints[0];
        const elements = target.root.index.all()
            .filter(element => isSelectable(element));
        // Traditional loop insetad of foreach in order to allow breaking
        for (const element of elements) {
            if (isMoveable(element) && isResizeable(element)) {
                const isOverlapping = this.isLineOverlappingElement(source, routingPoint, element.bounds);
                const edge = target.root.index.getById(elementAndRoutingPoint.elementId);
                let edgeSourceId;
                let edgeTargetId;
                let newPoint: Point;
                if (edge instanceof SEdge && isRoutable(edge)) {
                    edgeSourceId = edge.sourceId;
                    edgeTargetId = edge.targetId;
                }
                if (isOverlapping && element.id !== edgeSourceId && element.id !== edgeTargetId) {
                    const sideAndDistance = this.getClosestSideAndDistance(source, routingPoint, element.bounds);
                    // Create additional point
                    newPoint = this.generateNewRoutingPoint(sideAndDistance, source, routingPoint, element.bounds);
                    // Shift the old and new point
                    const shiftedPoints: Point[] = this.getShiftedRoutingPoints(newPoint, routingPoint, sideAndDistance);
                    elementAndRoutingPoint.routingPoints[0] = shiftedPoints[1];
                    elementAndRoutingPoint.routingPoints.unshift(shiftedPoints[0]);
                    break;
                }
            }
        }
    }

    // Straigth line between elements, no additional routing points
    checkOverlapBetweenAnchors(target: SModelElement, elementAndRoutingPoint: ElementAndRoutingPoints) {
        const sourceAnchor: Point = elementAndRoutingPoint.source;
        const targetAnchor: Point = elementAndRoutingPoint.target;
        const elements = target.root.index.all()
            .filter(element => isSelectable(element));
        // Traditional loop insetad of foreach in order to allow breaking
        for (const element of elements) {
            if (isMoveable(element) && isResizeable(element)) {
                const isOverlapping = this.isLineOverlappingElement(sourceAnchor, targetAnchor, element.bounds);
                if (isOverlapping) {
                    const sideAndDistance: SideAndDistance = this.getClosestSideAndDistance(sourceAnchor, targetAnchor, element.bounds);
                    // Create additional points
                    if (sideAndDistance.side === Side.BOTTOM || sideAndDistance.side === Side.TOP) {
                        const firstPoint: Point = {
                            x: element.bounds.x - this.SPACING,
                            y: sourceAnchor.y
                        };
                        const secondPoint: Point = {
                            x: element.bounds.x + element.bounds.width + this.SPACING,
                            y: sourceAnchor.y
                        };
                        elementAndRoutingPoint.routingPoints.push(firstPoint);
                        elementAndRoutingPoint.routingPoints.push(secondPoint);
                    } else {
                        const firstPoint: Point = {
                            x: sourceAnchor.x,
                            y: element.bounds.y - this.SPACING
                        };
                        const secondPoint: Point = {
                            x: sourceAnchor.y,
                            y: element.bounds.y + this.SPACING
                        };
                        elementAndRoutingPoint.routingPoints.push(firstPoint);
                        elementAndRoutingPoint.routingPoints.push(secondPoint);
                    }
                }
            }
        }
    }

    getDistanceBetweenParallelLines(p1: Point, p2: Point, secondLine: PointToPointLine): Number {
        const numerator: number = Math.abs((secondLine.a * p1.x) + (secondLine.b * p1.y) - secondLine.c);
        const denominator: number = Math.sqrt(Math.pow(secondLine.a, 2) + Math.pow(secondLine.b, 2));
        return numerator / denominator;
    }

    getShiftedRoutingPoints(firstPoint: Point, secondPoint: Point, sideAndDistance: SideAndDistance): Point[] {
        const distance: Number = sideAndDistance.distance;
        const shiftedRoutingPoints: Point[] = [];
        const side: Side = sideAndDistance.side;
        let shiftedX1: number = firstPoint.x;
        let shiftedY1: number = firstPoint.y;
        let shiftedX2: number = secondPoint.x;
        let shiftedY2: number = secondPoint.y;
        if (side === Side.LEFT) {
            shiftedX1 = firstPoint.x - distance.valueOf() - this.SPACING;
            shiftedX2 = secondPoint.x - distance.valueOf() - this.SPACING;
        } else if (side === Side.RIGHT) {
            shiftedX1 = firstPoint.x + distance.valueOf() + this.SPACING;
            shiftedX2 = secondPoint.x + distance.valueOf() + this.SPACING;
        } else if (side === Side.TOP) {
            shiftedY1 = firstPoint.y - distance.valueOf() - this.SPACING;
            shiftedY2 = secondPoint.y - distance.valueOf() - this.SPACING;
        } else if (side === Side.BOTTOM) {
            shiftedY1 = firstPoint.y + distance.valueOf() + this.SPACING;
            shiftedY2 = secondPoint.y + distance.valueOf() + this.SPACING;
        }
        const shiftedFirstPoint: Point = {
            x: shiftedX1,
            y: shiftedY1
        };
        const shiftedSecondPoint: Point = {
            x: shiftedX2,
            y: shiftedY2
        };

        shiftedRoutingPoints.push(shiftedFirstPoint);
        shiftedRoutingPoints.push(shiftedSecondPoint);
        return shiftedRoutingPoints;
    }

    getClosestSideAndDistance(firstPoint: Point, secondPoint: Point, bounds: Bounds): SideAndDistance {
        const parallelLines: PointToPointLine[] = this.getParallelLines(firstPoint, secondPoint, bounds);
        const distanceFirstLine = this.getDistanceBetweenParallelLines(firstPoint, secondPoint, parallelLines[0]);
        const distanceSecondLine = this.getDistanceBetweenParallelLines(firstPoint, secondPoint, parallelLines[1]);
        let distanceMinLine: Number;
        let closestSide;

        // Horizontal line so we have to consider top/bottom
        if (parallelLines[0].a === 0) {
            if (distanceFirstLine <= distanceSecondLine) {
                distanceMinLine = distanceFirstLine;
                closestSide = Side.TOP;
            } else {
                distanceMinLine = distanceSecondLine;
                closestSide = Side.BOTTOM;
            }
        } else {
            // Vertical line so we have to consider left/right
            if (distanceFirstLine <= distanceSecondLine) {
                distanceMinLine = distanceFirstLine;
                closestSide = Side.LEFT;
            } else {
                distanceMinLine = distanceSecondLine;
                closestSide = Side.RIGHT;
            }
        }
        return { side: closestSide, distance: distanceMinLine };
    }

    getParallelLines(a1: Point, a2: Point, bounds: Bounds): PointToPointLine[] {
        const topLeft: Point = {
            x: bounds.x,
            y: bounds.y
        };
        const topRight: Point = {
            x: bounds.x + bounds.width,
            y: bounds.y
        };
        const bottomLeft: Point = {
            x: bounds.x,
            y: bounds.y + bounds.height
        };
        const bottomRight: Point = {
            x: bounds.x + bounds.width,
            y: bounds.y + bounds.height
        };

        const parallelLines: PointToPointLine[] = [];
        if (this.areLinesParallel(a1, a2, topLeft, topRight)) {
            parallelLines.push(new PointToPointLine(topLeft, topRight));
        }
        if (this.areLinesParallel(a1, a2, bottomLeft, bottomRight)) {
            parallelLines.push(new PointToPointLine(bottomLeft, bottomRight));
        }
        if (this.areLinesParallel(a1, a2, topLeft, bottomLeft)) {
            parallelLines.push(new PointToPointLine(topLeft, bottomLeft));
        }
        if (this.areLinesParallel(a1, a2, topRight, bottomRight)) {
            parallelLines.push(new PointToPointLine(topRight, bottomRight));
        }
        return parallelLines;
    }

    areLinesParallel(a1: Point, a2: Point, b1: Point, b2: Point): boolean {
        const firstLine: PointToPointLine = new PointToPointLine(a1, a2);
        const secondLine: PointToPointLine = new PointToPointLine(b1, b2);
        const parallelEquation = (firstLine.a * secondLine.b) - (firstLine.b * secondLine.a);
        return almostEquals(parallelEquation, 0);
    }

    isLineOverlappingElement(p1: Point, p2: Point, bounds: Bounds) {
        let minX = p1.x;
        let maxX = p2.x;
        const left = bounds.x;
        const top = bounds.y;
        const width = bounds.width;
        const height = bounds.height;

        if (p1.x > p2.x) {
            minX = p2.x;
            maxX = p1.x;
        }

        if (maxX > left + width)
            maxX = left + width;

        if (minX < left)
            minX = left;

        if (minX > maxX)
            return false;

        let minY = p1.y;
        let maxY = p2.y;

        const dx = p2.x - p1.x;

        if (Math.abs(dx) > 0.0000001) {
            const a = (p2.y - p1.y) / dx;
            const b = p1.y - a * p1.x;
            minY = a * minX + b;
            maxY = a * maxX + b;
        }

        if (minY > maxY) {
            const tmp = maxY;
            maxY = minY;
            minY = tmp;
        }

        if (maxY > top + height)
            maxY = top + height;

        if (minY < top)
            minY = top;

        if (minY > maxY)
            return false;
        return true;
    }

    getConnectedRoutingPoints(element: SModelElement): ElementAndRoutingPoints[] {
        const elementsAndRoutingPoints: ElementAndRoutingPoints[] = [];

        if (isMoveable(element) && isResizeable(element) && isSelectable(element) && isConnectable(element)) {

            element.incomingEdges.forEach(edge => {
                const router = this.edgeRouterRegistry.get(edge.routerKind);
                router.createRoutingHandles(edge);
                const routedPoints: RoutedPoint[] = router.route(edge);
                const source: Point = routedPoints[0];
                const target: Point = routedPoints[routedPoints.length - 1];
                elementsAndRoutingPoints.push.apply(elementsAndRoutingPoints, createElementAndRoutingPoints(edge, source, target));

            });
            element.outgoingEdges.forEach(edge => {
                const router = this.edgeRouterRegistry.get(edge.routerKind);
                router.createRoutingHandles(edge);
                const routedPoints: RoutedPoint[] = router.route(edge);
                const source: Point = routedPoints[0];
                const target: Point = routedPoints[routedPoints.length - 1];
                // edge.routingPoints = routedPoints;
                elementsAndRoutingPoints.push.apply(elementsAndRoutingPoints, createElementAndRoutingPoints(edge, source, target));

            });
        }
        return elementsAndRoutingPoints;
    }

}

function isNonRoutableBoundsAware(element: SModelElement): element is SModelElement & BoundsAware & Selectable {
    return isBoundsAware(element) && !isRoutable(element);
}

function createElementAndRoutingPoints(element: SEdge & BoundsAware, source: Point, target: Point): ElementAndRoutingPoints[] {
    return [{ elementId: element.id, source, target, routingPoints: element.routingPoints }];
}

