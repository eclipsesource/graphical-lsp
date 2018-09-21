/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *     Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { injectable } from "inversify";
import { Command, Action, CommandResult, CommandExecutionContext } from "sprotty/lib";
import { Operation } from "../../utils/operation";

export class RequestOperationsAction implements Action {
    static readonly KIND = 'requestOperations'
    readonly kind = RequestOperationsAction.KIND

    constructor() { }

}

export class SetOperationsAction implements Action {
    readonly kind = SetOperationsCommand.KIND
    constructor(public readonly operations: Operation[]) { }
}

@injectable()
//Basically a no-op command for now. Because the tools palette has been implemented 
//with Theia mechanisms in the first draft. Should be improved and changed in a later revision
export class SetOperationsCommand extends Command {
    static readonly KIND = 'setOperations'

    availableOperations: Operation[]

    constructor(public action: SetOperationsAction) {
        super()
    }
    execute(context: CommandExecutionContext): CommandResult {
        this.availableOperations = this.action.operations
        return context.root

    }

    undo(context: CommandExecutionContext): CommandResult {
        return context.root
    }

    redo(context: CommandExecutionContext): CommandResult {
        return context.root
    }

    getTools(): Operation[] {
        return this.availableOperations
    }

}