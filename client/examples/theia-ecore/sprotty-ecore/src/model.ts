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

import { Expandable } from "sprotty/lib";
import { Hoverable } from "sprotty/lib";
import { RectangularNode } from "sprotty/lib";
import { SEdge } from "sprotty/lib";
import { SGraph } from "sprotty/lib";
import { SLabel } from "sprotty/lib";
import { SModelRoot } from "sprotty/lib";
import { SModelRootSchema } from "sprotty/lib";
import { SShapeElement } from "sprotty/lib";

import { boundsFeature } from "sprotty/lib";
import { expandFeature } from "sprotty/lib";
import { fadeFeature } from "sprotty/lib";
import { hoverFeedbackFeature } from "sprotty/lib";
import { layoutableChildFeature } from "sprotty/lib";
import { layoutContainerFeature } from "sprotty/lib";



export class EcoreGraph extends SGraph {
    needsInitialLayout: boolean
}

export function isEcoreGraph(root: SModelRoot | SModelRootSchema): root is EcoreGraph {
    return (<any>root)["needsInitialLayout"] !== undefined && typeof ((<any>root)["needsInitialLayout"]) === 'boolean'
}

export class ClassNode extends RectangularNode implements Expandable {
    expanded: boolean = false;

    hasFeature(feature: symbol) {
        return feature === expandFeature || super.hasFeature(feature);
    }
}

export class Icon extends SShapeElement {
    size = {
        width: 32,
        height: 32
    };

    hasFeature(feature: symbol): boolean {
        return feature === boundsFeature || feature === layoutContainerFeature || feature === layoutableChildFeature || feature === fadeFeature;
    }
}

export const multiplicitySourceFeature = Symbol('multiplicitySourceFeature');
export const multiplicityTargetFeature = Symbol('multiplicityTargetFeature');

export class EdgeWithMultiplicty extends SEdge {
    multiplicitySource: string
    multiplicityTarget: string

    hasFeature(feature: symbol) {
        return feature === multiplicitySourceFeature || feature === multiplicityTargetFeature || super.hasFeature(feature);
    }
}

export class PropertyLabel extends SLabel implements Hoverable {
    hoverFeedback: boolean

    hasFeature(feature: symbol) {
        return feature === hoverFeedbackFeature || super.hasFeature(feature);
    }
}