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
import { BoundsAware, Hoverable, hoverFeedbackFeature, isBoundsAware, isMoveable, Locateable, SChildElement, SModelElement } from "sprotty/lib";
import { ResizeHandleLocation } from "../resize/model";


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