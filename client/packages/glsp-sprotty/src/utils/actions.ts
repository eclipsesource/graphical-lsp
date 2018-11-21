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

import { Action, ElementAndBounds } from "sprotty/lib";

export class ChangeBoundsAction implements Action {
    public static readonly KIND = "executeOperation_change-bounds";
    public kind = ChangeBoundsAction.KIND;

    constructor(public newBounds: ElementAndBounds[]) { }
}