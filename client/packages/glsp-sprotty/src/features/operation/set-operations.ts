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
import { Action } from "sprotty/lib";

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
    static readonly KIND = 'setOperations'
    readonly kind = SetOperationsAction.KIND
    constructor(public readonly operations: Operation[]) { }
}

export function isSetOperationsAction(action: Action): action is SetOperationsAction {
    return action !== undefined && (action.kind === SetOperationsAction.KIND)
        && (<SetOperationsAction>action).operations !== undefined
}