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
import { SModelExtension, SModelElement, SModelRoot } from "sprotty/lib";

export const saveFeature = Symbol('saveFeature');

export interface Saveable extends SModelExtension {
    dirty: boolean;
}

export function isSaveable(element: SModelElement): element is SModelRoot & Saveable {
    return element.hasFeature(saveFeature);
}