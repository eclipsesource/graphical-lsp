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
import { BoundsAware } from "sprotty/lib";
import { Selectable } from "sprotty/lib";
import { SModelElement } from "sprotty/lib";

import { isBoundsAware } from "sprotty/lib";
import { isSelectable } from "sprotty/lib";

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
    return isSelectable(element) && element.selected;
}

export function isSelectedBoundsAware(element: SModelElement): element is SModelElement & BoundsAware & Selectable {
    return isBoundsAware(element) && isSelected(element);
}

export function isNotUndefined<T>(element: T | undefined): element is T {
    return element !== undefined;
}

export function addCssClasses(root: SModelElement, cssClasses: string[]) {
    if (root.cssClasses === undefined) {
        root.cssClasses = [];
    }
    for (const cssClass of cssClasses) {
        if (root.cssClasses.indexOf(cssClass) < 0) {
            root.cssClasses.push(cssClass);
        }
    }
}

export function removeCssClasses(root: SModelElement, cssClasses: string[]) {
    if (root.cssClasses === undefined || root.cssClasses.length === 0) {
        return;
    }
    for (const cssClass of cssClasses) {
        const index = root.cssClasses.indexOf(cssClass);
        if (index !== -1) {
            root.cssClasses.splice(root.cssClasses.indexOf(cssClass), 1);
        }
    }
}
