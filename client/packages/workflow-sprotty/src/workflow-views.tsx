/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import {
    angleOfPoint, isResizeable, IView, Point, PolylineEdgeView, RectangularNodeView, RenderingContext, ResizeHandleLocation, SEdge, setAttr, SResizeHandle, SShapeElement, //
    toDegrees
} from "glsp-sprotty/lib";
import * as snabbdom from "snabbdom-jsx";
import { VNode } from "snabbdom/vnode";
import { Icon, TaskNode, WeightedEdge } from "./model";

const JSX = { createElement: snabbdom.svg }

export class TaskNodeView extends RectangularNodeView {
    render(node: TaskNode, context: RenderingContext): VNode {
        const rcr = this.getRoundedCornerRadius(node)
        const graph = <g>
            <rect class-sprotty-node={true} class-task={true}
                class-automated={node.taskType === 'automated'}
                class-manual={node.taskType === 'manual'}
                class-mouseover={node.hoverFeedback} class-selected={node.selected}
                x={0} y={0} rx={rcr} ry={rcr}
                width={Math.max(0, node.bounds.width)} height={Math.max(0, node.bounds.height)}></rect>
            {context.renderChildren(node)}
        </g>;
        return graph


    }

    protected getRoundedCornerRadius(node: SShapeElement): number {
        return 5;
    }
}

export class WorkflowEdgeView extends PolylineEdgeView {
    protected renderAdditionals(edge: SEdge, segments: Point[], context: RenderingContext): VNode[] {
        const p1 = segments[segments.length - 2];
        const p2 = segments[segments.length - 1];
        return [
            <path class-sprotty-edge={true} class-arrow={true} d="M 2,0 L 10,-4 L 10,4 Z"
                transform={`rotate(${toDegrees(angleOfPoint({ x: p1.x - p2.x, y: p1.y - p2.y }))} ${p2.x} ${p2.y}) translate(${p2.x} ${p2.y})`} />
        ];
    }
}

export class WeightedEdgeView extends WorkflowEdgeView {
    render(edge: Readonly<WeightedEdge>, context: RenderingContext): VNode {
        const route = edge.route();
        if (route.length === 0)
            return this.renderDanglingEdge("Cannot compute route", edge, context);

        return <g class-sprotty-edge={true}
            class-weighted={true}
            class-low={edge.probability === 'low'}
            class-medium={edge.probability === 'medium'}
            class-high={edge.probability === 'high'}
            class-mouseover={edge.hoverFeedback}>
            {this.renderLine(edge, route, context)}
            {this.renderAdditionals(edge, route, context)}
            {context.renderChildren(edge, { route })}
        </g>;
    }
}

export class IconView implements IView {
    render(element: Icon, context: RenderingContext): VNode {
        const radius = this.getRadius();
        return <g>
            <circle class-sprotty-icon={true} r={radius} cx={radius} cy={radius}></circle>
            {context.renderChildren(element)}
        </g>;
    }

    getRadius() {
        return 16;
    }
}

export class SResizeHandleView implements IView {
    render(handle: SResizeHandle, context: RenderingContext): VNode {
        const position = this.getPosition(handle);
        if (position !== undefined) {
            const node = <circle class-sprotty-resize-handle={true} class-mouseover={handle.hoverFeedback}
                cx={position.x} cy={position.y} />;   // Radius must be specified via CSS
            setAttr(node, 'data-kind', handle.location);
            return node;
        }
        // Fallback: Create an empty group
        return <g />;
    }

    protected getPosition(handle: SResizeHandle): Point | undefined {
        const parent = handle.parent;
        if (isResizeable(parent)) {
            if (handle.location === ResizeHandleLocation.TopLeft) {
                return { x: 0, y: 0 };
            } else if (handle.location === ResizeHandleLocation.TopRight) {
                return { x: parent.bounds.width, y: 0 };
            } else if (handle.location === ResizeHandleLocation.BottomLeft) {
                return { x: 0, y: parent.bounds.height };
            } else if (handle.location === ResizeHandleLocation.BottomRight) {
                return { x: parent.bounds.width, y: parent.bounds.height };
            }
        }
        return undefined;
    }
}