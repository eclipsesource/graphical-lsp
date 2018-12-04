/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { injectable } from "inversify";
import { Action, Command, CommandExecutionContext, CommandResult } from "sprotty/lib";

export namespace OperationKind {
    export const CREATE_NODE = "createNode";
    export const CREATE_CONNECTION = "createConnection";
    export const DELETE_ELEMENT = "delete";
    export const CHANGE_BOUNDS = "changeBoundsOperation";
    export const CHANGE_CONTAINER = "changeContainer"
    export const GENERIC = "generic";
}

export interface Operation {
    readonly elementTypeId?: string;
    readonly label: string;
    readonly operationKind: string;
    isActive?: boolean;
}

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
// Basically a no-op command for now. Because the tools palette has been implemented
// with Theia mechanisms in the first draft. Should be improved and changed in a later revision
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
