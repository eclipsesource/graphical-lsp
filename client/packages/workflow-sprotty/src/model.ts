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

import {
    Bounds, boundsFeature, CommandExecutor, DiamondNode, executeCommandFeature, Expandable, expandFeature, //
    fadeFeature, layoutableChildFeature, LayoutContainer, layoutContainerFeature, RectangularNode, SEdge, SShapeElement
} from "glsp-sprotty/lib";
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
