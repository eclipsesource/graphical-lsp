/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
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
import { Tool } from "../../utils/tool";

export class RequestToolsAction implements Action {
    static readonly KIND = 'requestOperations'
    readonly kind = RequestToolsAction.KIND

    constructor() { }

}

export class SetToolsAction implements Action {
    readonly kind = SetToolsCommand.KIND
    constructor(public readonly tools: Tool[]) { }
}

@injectable()
//Basically a no-op command for now. Because the tools palette has been implemented 
//with Theia mechanisms in the first draft. Should be improved and changed in a later revision
export class SetToolsCommand extends Command {
    static readonly KIND = 'setTools'

    currentTools: Tool[]

    constructor(public action: SetToolsAction) {
        super()
    }
    execute(context: CommandExecutionContext): CommandResult {
        this.currentTools = this.action.tools
        return context.root

    }

    undo(context: CommandExecutionContext): CommandResult {
        return context.root
    }

    redo(context: CommandExecutionContext): CommandResult {
        return context.root
    }

    getTools(): Tool[] {
        return this.currentTools
    }

}