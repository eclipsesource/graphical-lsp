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
/** @jsx svg */
import { injectable } from 'inversify';
import { svg } from 'snabbdom-jsx';
import { VNode } from "snabbdom/vnode";
import { IView, Point, PolylineEdgeView, RectangularNodeView, RenderingContext, SEdge, toDegrees } from "sprotty/lib";
import { ClassNode, EdgeWithMultiplicty, Icon } from './model';


@injectable()
export class ClassNodeView extends RectangularNodeView {
    render(node: ClassNode, context: RenderingContext): VNode {
        return <g class-node={true}>
            <rect class-sprotty-node={true} class-selected={node.selected} class-mouseover={node.hoverFeedback}
                x={0} y={0} rx={10} ry={10}
                width={Math.max(0, node.bounds.width)} height={Math.max(0, node.bounds.height)} />
            {context.renderChildren(node)}
        </g>;
    }
}

@injectable()
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

@injectable()
export class ArrowEdgeView extends PolylineEdgeView {
    protected renderAdditionals(edge: SEdge, segments: Point[], context: RenderingContext): VNode[] {
        const p1 = segments[segments.length - 2];
        const p2 = segments[segments.length - 1];
        return [
            <path class-sprotty-edge={true} d="M 10,-4 L 0,0 L 10,4"
                transform={`rotate(${angle(p2, p1)} ${p2.x} ${p2.y}) translate(${p2.x} ${p2.y})`} />,
        ]
    }

    static readonly TARGET_CORRECTION = Math.sqrt(1 * 1 + 2.5 * 2.5)

    protected getTargetAnchorCorrection(edge: SEdge): number {
        return ArrowEdgeView.TARGET_CORRECTION
    }

}

@injectable()
export class InheritanceEdgeView extends ArrowEdgeView {
    protected renderAdditionals(edge: SEdge, segments: Point[], context: RenderingContext): VNode[] {
        const p1 = segments[segments.length - 2];
        const p2 = segments[segments.length - 1];
        return [
            <path class-sprotty-edge={true} d="M 10,-4 L 0,0 L 10,4 Z" class-inheritance={true}
                transform={`rotate(${angle(p2, p1)} ${p2.x} ${p2.y}) translate(${p2.x} ${p2.y})`} />,
        ]
    }

    static readonly TARGET_CORRECTION = Math.sqrt(1 * 1 + 2.5 * 2.5)

    protected getTargetAnchorCorrection(edge: SEdge): number {
        return ArrowEdgeView.TARGET_CORRECTION
    }
}

@injectable()
abstract class DiamondEdgeView extends PolylineEdgeView {
    protected renderAdditionals(edge: EdgeWithMultiplicty, segments: Point[], context: RenderingContext): VNode[] {
        const p1 = segments[0]
        const p2 = segments[1]
        const r = 6;
        const rhombStr = "M 0,0 l" + r + "," + (r / 2) + " l" + r + ",-" + (r / 2) + " l-" + r + ",-" + (r / 2) + " l-" + r + "," + (r / 2) + " Z";
        const firstEdgeAngle = angle(p1, p2);
        const pn = segments[segments.length - 1];
        const pn_1 = segments[segments.length - 2];
        const lastEdgeAngle = angle(pn_1, pn);
        return [
            <path class-sprotty-edge={true} class-diamond={true} class-composition={this.isComposition()} class-aggregation={this.isAggregation()} d={rhombStr}
                transform={`rotate(${firstEdgeAngle} ${p1.x} ${p1.y}) translate(${p1.x} ${p1.y})`} />,
            <text class-sprotty-label={true} transform={`rotate(${firstEdgeAngle} ${p1.x} ${p1.y}) translate(${p1.x + 15} ${p1.y - 5})`}>{edge.multiplicitySource}</text>,
            <text class-sprotty-label={true} transform={`rotate(${lastEdgeAngle} ${pn.x} ${pn.y}) translate(${pn.x - 15} ${pn.y - 5})`}>{edge.multiplicityTarget}</text>
        ]
    }

    static readonly SOURCE_CORRECTION = Math.sqrt(1 * 1 + 2 * 2)

    protected getSourceAnchorCorrection(edge: SEdge): number {
        return CompositionEdgeView.SOURCE_CORRECTION
    }
    protected isComposition(): boolean {
        return false;
    }
    protected isAggregation(): boolean {
        return false;
    }
}

@injectable()
export class CompositionEdgeView extends DiamondEdgeView {
    protected isComposition(): boolean {
        return true;
    }
}

@injectable()
export class AggregationEdgeView extends DiamondEdgeView {
    protected isAggregation(): boolean {
        return true;
    }
}

export function angle(x0: Point, x1: Point): number {
    return toDegrees(Math.atan2(x1.y - x0.y, x1.x - x0.x))
}