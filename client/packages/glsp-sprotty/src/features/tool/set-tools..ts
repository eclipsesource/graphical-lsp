import { injectable } from "inversify";
import { Command, Action, CommandResult, CommandExecutionContext } from "sprotty/lib";
import { Tool } from "../../utils/tool";

export class RequestToolsAction implements Action {
    static readonly KIND = 'requestTools'
    readonly kind = RequestToolsAction.KIND

    constructor() { }

}

export class SetToolsAction implements Action {
    readonly kind = SetToolsCommand.KIND
    constructor(public readonly tools: Tool[]) { }
}

@injectable()
export class SetToolsCommand extends Command {
    static readonly KIND = 'setTools'
    execute(context: CommandExecutionContext): CommandResult {
        throw new Error('Not yet implemented')

    }

    undo(context: CommandExecutionContext): CommandResult {
        throw new Error('Not yet implemented')
    }

    redo(context: CommandExecutionContext): CommandResult {
        throw new Error('Not yet implemented')
    }
}