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
import { Action, MouseListener, SModelElement, findParentByFeature } from "sprotty/lib";
import { isCommandExecutor } from "./model";

export class ExecuteServerCommandAction implements Action {
    static readonly KIND = "executeServerCommand"
    kind = ExecuteServerCommandAction.KIND;
    constructor(public readonly commandId: String, public readonly options?: { [key: string]: string }) { }
}

export class ExecuteCommandMouseListener extends MouseListener {
    doubleClick(target: SModelElement, event: WheelEvent): (Action | Promise<Action>)[] {
        const result: Action[] = [];
        let commandExecutorTarget = findParentByFeature(target, isCommandExecutor)
        if (commandExecutorTarget) {
            result.push(new ExecuteServerCommandAction(commandExecutorTarget.commandId, { invokerId: commandExecutorTarget.id }));
        }

        return result;
    }
}