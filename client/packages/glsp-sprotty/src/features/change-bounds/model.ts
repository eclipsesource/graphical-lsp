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
import {
    BoundsAware, Hoverable, hoverFeedbackFeature, isBoundsAware, isMoveable, isSelectable, Locateable, SChildElement, Selectable, SModelElement, //
    SNode, SParentElement
} from "sprotty/lib";
import { isConfigurableNode, NodeEditConfig } from "../../base/edit-config/edit-config";

export enum ResizeHandleLocation {
    TopLeft = "top-left",
    TopRight = "top-right",
    BottomLeft = "bottom-left",
    BottomRight = "bottom-right"
}

export function isResizeable(element: SModelElement): element is SNode & SParentElement & BoundsAware & Selectable & NodeEditConfig {
    return isConfigurableNode(element) && element.resizable && isBoundsAware(element) && isSelectable(element) && element instanceof SParentElement;
}

export function isBoundsAwareMoveable(element: SModelElement): element is SModelElement & Locateable & BoundsAware {
    return isMoveable(element) && isBoundsAware(element);
}

export class SResizeHandle extends SChildElement implements Hoverable {
    type: string = 'resize-handle';
    hoverFeedback: boolean = false;
    location?: ResizeHandleLocation;

    constructor(location?: ResizeHandleLocation) {
        super();
        this.location = location;
    }

    hasFeature(feature: symbol): boolean {
        return feature === hoverFeedbackFeature;
    }
}