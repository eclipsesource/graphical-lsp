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
    /**
     * The kind of the action.
     */
    readonly kind = ExecuteNodeCreationToolAction.KIND

    /**
     * The id of the node creation tool to execute.
     */
    readonly toolId: string;

    /**
     * The location where the tool has been invoked by the user.
     */
    readonly location?: Point;

    /**
     * The id of the container element in which the tool has been invoked by the user.
     */
    readonly containerId?: string;
}

class ExecuteToolAction implements Action {

    static readonly KIND = 'executeTool'
    /**
     * The kind of the action.
     */
    readonly kind = ExecuteToolAction.KIND

    /**
     * The id of the generic tool to execute.
     */
    readonly toolId: string;

    /**
     * The location where the tool has been invoked by the user.
     */
    readonly location?: Point;

    /**
     * The id of the element on which the tool has been invoked by the user.
     */
    readonly elementId?: string;
}