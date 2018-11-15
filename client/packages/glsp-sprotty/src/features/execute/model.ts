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
import { SModelElement, SModelExtension } from "sprotty/lib";

export const executeCommandFeature = Symbol('executeFeature');

export interface CommandExecutor extends SModelExtension {
    commandId: string
}

export function isCommandExecutor(element: SModelElement): element is SModelElement & CommandExecutor {
    return element.hasFeature(executeCommandFeature)
}
