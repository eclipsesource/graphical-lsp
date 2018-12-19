/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
import { SModelElement, SModelExtension } from "sprotty/lib";

export const nameFeature = Symbol('nameableFeature');

export interface Nameable extends SModelExtension {
    name: string
}

export function isNameable(element: SModelElement): element is SModelElement & Nameable {
    return element.hasFeature(nameFeature);
}

export function name(element: SModelElement): string {
    if (isNameable(element)) {
        return element.name;
    } else {
        return 'unnamed';
    }
}
