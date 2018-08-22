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
import { Action, Point } from "sprotty/lib";

export class ExecuteNodeCreationToolAction implements Action {

    static readonly KIND = 'executeNodeCreationTool'
    readonly kind = ExecuteNodeCreationToolAction.KIND


    constructor(public readonly toolId: string,
        public readonly location?: Point,
        public readonly containerId?: string) { }
}

export class ExecuteToolAction implements Action {

    static readonly KIND = 'executeTool'
    readonly kind = ExecuteToolAction.KIND


    constructor(public readonly toolId: string,
        public readonly location?: Point,
        public readonly elementId?: string) { }
}