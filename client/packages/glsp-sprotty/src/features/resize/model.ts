/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Martin Fleck - initial API and implementation
 ******************************************************************************/
import { BoundsAware, Hoverable, hoverFeedbackFeature, isBoundsAware, SChildElement, SModelElement, SParentElement } from "sprotty/lib";

export const resizeFeature = Symbol('resizeFeature');

export enum ResizeHandleLocation {
    TopLeft = "top-left",
    TopRight = "top-right",
    BottomLeft = "bottom-left",
    BottomRight = "bottom-right"
}

export function isResizeable(element: SModelElement): element is SParentElement & BoundsAware {
    return element.hasFeature(resizeFeature) && isBoundsAware(element) && element instanceof SParentElement;
}

export class SResizeHandle extends SChildElement implements Hoverable {
    type: string = 'resize-handle';
    hoverFeedback: boolean = false;
    location: ResizeHandleLocation;

    constructor(location?: ResizeHandleLocation) {
        super();
        if (location) {
            this.location = location;
        }
    }

    hasFeature(feature: symbol): boolean {
        return feature === hoverFeedbackFeature;
    }
}