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
    Action,
    almostEquals,
    Bounds,
    BoundsAware,
    EdgeRouterRegistry,
    ElementAndBounds,
    findParentByFeature,
    isBoundsAware,
    isConnectable,
    isMoveable,
    isSelectable,
    isViewport,
    KeyTool,
    ManhattanEdgeRouter,
    MouseListener,
    Point,
    PointToPointLine,
    RoutedPoint,
    SEdge,
    Selectable,
    SetBoundsAction,
    Side,
    SModelElement,
    SModelRoot,
    SParentElement,
    Tool
} from "sprotty/lib";

import { GLSPViewerOptions } from "../../base/views/viewer-options";
import { GLSP_TYPES } from "../../types";
import { forEachElement, isSelected } from "../../utils/smodel-util";
import { SaveModelEdgesAction } from "../change-bounds/edges";
import { isBoundsAwareMoveable, isResizeable, ResizeHandleLocation, SResizeHandle } from "../change-bounds/model";
import { IMouseTool } from "../mouse-tool/mouse-tool";
import { ChangeBoundsOperationAction } from "../operation/operation-actions";
import { isRoutable } from "../reconnect/model";
import { SelectionListener, SelectionService } from "../select/selection-service";
import {
    FeedbackMoveMouseListener,
    HideChangeBoundsToolResizeFeedbackAction,
    ShowChangeBoundsToolResizeFeedbackAction
} from "../tool-feedback/change-bounds-tool-feedback";
import { SwitchRoutingModeAction } from "../tool-feedback/edge-edit-tool-feedback";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";

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

    constructor(@inject(GLSP_TYPES.SelectionService) protected selectionService: SelectionService,
        @inject(GLSP_TYPES.MouseTool) protected mouseTool: IMouseTool,
        @inject(KeyTool) protected keyTool: KeyTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher,
        @inject(GLSP_TYPES.ViewerOptions) protected opts: GLSPViewerOptions,
        @inject(EdgeRouterRegistry) protected edgeRouterRegistry: EdgeRouterRegistry,
        @inject(ManhattanEdgeRouter) protected manhattanRouter: ManhattanEdgeRouter) { }


    enable() {
        // install feedback move mouse listener for client-side move updates
        this.feedbackMoveMouseListener = new FeedbackMoveMouseListener(this.opts);
        this.mouseTool.register(this.feedbackMoveMouseListener);

        // instlal change bounds listener for client-side resize updates and server-side updates
        this.changeBoundsListener = new ChangeBoundsListener(this, this.edgeRouterRegistry, this.manhattanRouter);
        this.mouseTool.register(this.changeBoundsListener);
        this.selectionService.register(this.changeBoundsListener);
        this.feedbackDispatcher.registerFeedback(this, [new ShowChangeBoundsToolResizeFeedbackAction]);
    }

    disable() {
        this.mouseTool.deregister(this.changeBoundsListener);
        this.selectionService.deregister(this.changeBoundsListener);
        this.mouseTool.deregister(this.feedbackMoveMouseListener);
        this.feedbackDispatcher.deregisterFeedback(this, [new HideChangeBoundsToolResizeFeedbackAction]);
    }

    dispatchFeedback(actions: Action[]) {
        this.feedbackDispatcher.registerFeedback(this, actions);
    }
}

class ChangeBoundsListener extends MouseListener implements SelectionListener {
    // members for calculating the correct position change
    private lastDragPosition: Point | undefined = undefined;
    private positionDelta: Point = { x: 0, y: 0 };

    // members for resize mode
    private activeResizeElementId: string | undefined = undefined;
    private activeResizeHandle: SResizeHandle | undefined = undefined;

    // Spacing unit used for rerouting in case of collision
    private SPACING: number = 5;

    constructor(protected tool: ChangeBoundsTool, protected edgeRouterRegistry: EdgeRouterRegistry, protected manhattanRouter: ManhattanEdgeRouter) {
        super();
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        super.mouseDown(target, event);
        const actions: Action[] = [];
        if (event.button === 0) {
            // check if we have a resize handle (only single-selection)
            if (this.activeResizeElementId && target instanceof SResizeHandle) {
                this.activeResizeHandle = target;
            } else {
                this.setActiveResizeElement(target);
            }
            if (this.activeResizeElementId) {
                this.initPosition(event);
            } else {
                this.reset();
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
            const newRoutingPoints: ElementAndRoutingPoints[] = [];

            forEachElement(target, isNonRoutableBoundsAware, element => {



                createElementAndBounds(element).forEach(bounds => {
                    // Push new bounds only for selected elements
                    if (isSelected(element)) {
                        newBounds.push(bounds);
                    }
                });

                newRoutingPoints.push.apply(newRoutingPoints, this.getConnectedRoutingPoints(element));
                // Going through the routing points
                newRoutingPoints.forEach(elementAndRoutingPoint => {
                    this.tool.dispatchFeedback([new SwitchRoutingModeAction([elementAndRoutingPoint.elementId])]);
                    // In case of a straight edge, no routing points
                    if (elementAndRoutingPoint.routingPoints.length === 0) {
                        this.checkOverlapBetweenAnchors(target, elementAndRoutingPoint);
                    }
                    // Check Overlap between the source and first routing point
                    this.checkOverlapBetweenSourceAndPoint(target, elementAndRoutingPoint);
                    // Check Overlap between the target and the last routing point
                    this.checkOverlapBetweenTargetAndPoint(target, elementAndRoutingPoint);
                    const nrRoutingPoints = elementAndRoutingPoint.routingPoints.length;
                    const overlaps: String[] = [];
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
                                        overlaps.push(element2.id);
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
            if (newBounds.length > 0) {
                actions.push(new ChangeBoundsOperationAction(newBounds));
            }
            // Push new routing points
            if (newRoutingPoints.length > 0) {
                actions.push(new SaveModelEdgesAction(newRoutingPoints));
            }


        }
        this.resetPosition();
        return actions;
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
        target.root.index.all()
            .filter(element => isSelectable(element))
            .forEach(element => {
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

                    }
                }
            });
    }

    // Straigth line between elements, no additional routing points
    checkOverlapBetweenAnchors(target: SModelElement, elementAndRoutingPoint: ElementAndRoutingPoints) {
        const sourceAnchor: Point = elementAndRoutingPoint.source;
        const targetAnchor: Point = elementAndRoutingPoint.target;
        target.root.index.all()
            .filter(element => isSelectable(element))
            .forEach(element => {
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
            });
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

    selectionChanged(root: SModelRoot, selectedElements: string[]): void {
        if (this.activeResizeElementId) {
            if (selectedElements.indexOf(this.activeResizeElementId) > -1) {
                // our active element is still selected, nothing to do
                return;
            }

            // try to find some other selected element and mark that active
            for (const elementId of selectedElements.reverse()) {
                const element = root.index.getById(elementId);
                if (element && this.setActiveResizeElement(element)) {
                    return;
                }
            }
            this.reset();
        }
    }

    private setActiveResizeElement(target: SModelElement): boolean {
        // check if we have a selected, moveable element (multi-selection allowed)
        const moveableElement = findParentByFeature(target, isBoundsAwareMoveable);
        if (isSelected(moveableElement)) {
            // only allow one element to have the element resize handles
            this.activeResizeElementId = moveableElement.id;
            this.tool.dispatchFeedback([new ShowChangeBoundsToolResizeFeedbackAction(this.activeResizeElementId)]);
            return true;
        }
        return false;
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

    private reset() {
        this.tool.dispatchFeedback([new HideChangeBoundsToolResizeFeedbackAction()]);
        this.resetPosition();
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
    if (isValidBoundChange(element, element.bounds, element.bounds)) {
        return [new ChangeBoundsOperationAction([toElementAndBounds(element)])];
    }
    return [];
}

function createElementAndBounds(element: SModelElement & BoundsAware): ElementAndBounds[] {
    if (isValidBoundChange(element, element.bounds, element.bounds)) {
        return [toElementAndBounds(element)];
    }
    return [];
}

function createSetBoundsAction(element: SModelElement & BoundsAware, x: number, y: number, width: number, height: number): Action[] {
    const newPosition = { x, y };
    const newSize = { width, height };
    if (isValidBoundChange(element, newPosition, newSize)) {
        return [new SetBoundsAction([{ elementId: element.id, newPosition, newSize }])];

    }
    return [];
}


function isValidBoundChange(element: SModelElement & BoundsAware, newPosition: Point, newSize: Dimension): boolean {
    return newSize.width >= minWidth(element) && newSize.height >= minHeight(element);
}

function minWidth(element: SModelElement & BoundsAware): number {
    // currently there are no element-specific constraints
    return 1;
}

function minHeight(element: SModelElement & BoundsAware): number {
    // currently there are no element-specific constraints
    return 1;
}

function isNonRoutableBoundsAware(element: SModelElement): element is SModelElement & BoundsAware & Selectable {
    return isBoundsAware(element) /*&& isSelected(element) */ && !isRoutable(element);
}

function createElementAndRoutingPoints(element: SEdge & BoundsAware, source: Point, target: Point): ElementAndRoutingPoints[] {
    return [{ elementId: element.id, source, target, routingPoints: element.routingPoints }];
}



export interface ElementAndRoutingPoints {
    elementId: string
    source: Point
    target: Point
    routingPoints: Point[]
}

export interface SideAndDistance {
    side: Side
    distance: Number
}
