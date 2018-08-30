/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { RectangularNode, SEdge, LayoutContainer, SShapeElement, Bounds, boundsFeature, layoutContainerFeature, layoutableChildFeature, fadeFeature, Expandable, expandFeature, Point, SParentElement, } from "sprotty/lib";
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

export class ActivityNode extends RectangularNode {
    nodeType: string = ActivityNodeSchema.Type.UNDEFINED
    size = {
        width: 16,
        height: 16
    };

    getTranslatedAnchor(refPoint: Point, refContainer: SParentElement, edge: SEdge, offset?: number): Point {
        var rectangleAnchor = super.getTranslatedAnchor(refPoint, refContainer, edge, offset);
        return this.rotate_point(this.position.x + this.size.width / 2, this.position.y + this.size.height / 2, 0.785398, rectangleAnchor);
    }

    rotate_point(cx: number, cy: number, angle: number, p: Point): Point {
        const s = Math.sin(angle);
        const c = Math.cos(angle);

        // translate point back to origin:
        var x = p.x;
        var y = p.y;
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
export class Icon extends SShapeElement implements LayoutContainer {
    layout: string
    layoutOptions?: { [key: string]: string | number | boolean; };
    bounds: Bounds
    size = {
        width: 32,
        height: 32
    };

    hasFeature(feature: symbol): boolean {
        return feature === boundsFeature || feature === layoutContainerFeature || feature === layoutableChildFeature || feature === fadeFeature;
    }
}
