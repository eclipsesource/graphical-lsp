/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
import { Action } from "sprotty/lib";

export namespace OperationKind {
    export const CREATE_NODE = "createNode";
    export const CREATE_CONNECTION = "createConnection";
    export const RECONNECT_CONNECTION = "reconnectConnection";
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

export function deriveOperationId(operationKind: string, elementTypeId?: string) {
    const elementTypeSuffix = elementTypeId ? '_' + elementTypeId : "";
    return `${operationKind}${elementTypeSuffix}`
}