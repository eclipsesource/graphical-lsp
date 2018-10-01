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
import { Action, Point } from "sprotty/lib";

export class ExecuteNodeCreationToolAction implements Action {

    static readonly KIND = 'executeOperation_create-node'
    readonly kind = ExecuteNodeCreationToolAction.KIND


    constructor(public readonly elementId: string,
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