/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
// tslint:disable-next-line:max-line-length
import { Bounds, boundsFeature, CommandExecutor, DiamondNode, executeCommandFeature, Expandable, expandFeature, fadeFeature, layoutableChildFeature, LayoutContainer, layoutContainerFeature, Point, RectangularNode, SEdge, SParentElement, SShapeElement, toRadians } from "glsp-sprotty/lib";
import { ActivityNodeSchema } from "./model-schema";

export class TaskNode extends RectangularNode implements Expandable {
    expanded: boolean
    name: string = ""
    duration?: number;
    taskType?: string;
    reference?: string;


    hasFeature(feature: symbol) {
        return feature === expandFeature || super.hasFeature(feature);
    }

}

export class WeightedEdge extends SEdge {
    probability?: string
}

export class RotatableRectangularNode extends RectangularNode {

    rotationInDegrees: number = 0

    getTranslatedAnchor(refPoint: Point, refContainer: SParentElement, edge: SEdge, offset?: number): Point {
        const cx = this.position.x + this.size.width / 2
        const cy = this.position.y + this.size.height / 2
        const translatedRefPoint = this.rotatePoint(cx, cy, -this.rotationInDegrees, refPoint)
        const originalAnchor = super.getTranslatedAnchor(translatedRefPoint, refContainer, edge, offset);
        return this.rotatePoint(cx, cy, this.rotationInDegrees, originalAnchor);
    }

    rotatePoint(cx: number, cy: number, angle: number, p: Point): Point {
        const rad = toRadians(angle)
        const s = Math.sin(rad);
        const c = Math.cos(rad);

        // translate point back to origin:
        let x = p.x;
        let y = p.y;
        x -= cx;
        y -= cy;

        // rotate point
        const xnew = x * c - y * s;
        const ynew = x * s + y * c;

        // translate point back:
        return {
            x: xnew + cx,
            y: ynew + cy
        };
    }
}

export class ActivityNode extends DiamondNode {
    nodeType: string = ActivityNodeSchema.Type.UNDEFINED
    size = {
        width: 32,
        height: 32
    };
    strokeWidth = 1
}


export class Icon extends SShapeElement implements LayoutContainer, CommandExecutor {
    commandId: string
    layout: string
    layoutOptions?: { [key: string]: string | number | boolean; };
    bounds: Bounds
    size = {
        width: 32,
        height: 32
    };

    hasFeature(feature: symbol): boolean {
        return feature === executeCommandFeature
            || feature === boundsFeature || feature === layoutContainerFeature
            || feature === layoutableChildFeature || feature === fadeFeature;
    }
}
