/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Martin Fleck - initial API and implementation
 ******************************************************************************/

import { BoundsAware, isBoundsAware, isSelectable, Selectable, SModelElement } from "sprotty/lib";

export function getIndex(element: SModelElement) {
    return element.root.index;
}

export function forEachElement<T>(element: SModelElement, predicate: (element: SModelElement) => element is SModelElement & T, runnable: (element: SModelElement & T) => void) {
    getIndex(element).all()
        .filter(predicate)
        .forEach(runnable);
}

export function hasSelectedElements(element: SModelElement) {
    return getSelectedElementCount(element) > 0;
}

export function getSelectedElementCount(element: SModelElement): number {
    let selected = 0;
    getIndex(element).all()
        .filter(isSelected)
        .forEach(e => selected = selected + 1);
    return selected;
}

export function isSelected(element: SModelElement): element is SModelElement & Selectable {
    return isSelectable(element) && element.selected
}

export function isSelectedBoundsAware(element: SModelElement): element is SModelElement & BoundsAware & Selectable {
    return isBoundsAware(element) && isSelected(element);
}