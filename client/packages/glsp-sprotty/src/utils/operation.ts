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
export namespace OperationKind {

    export const CREATE_NODE = "create-node";
    export const CREATE_CONNECTION = "create-connection";
    export const DELETE_ELEMENT = "delete-node";
    export const MOVE = "move";
    export const GENERIC = "generic";
}
export interface Operation {

    readonly elementTypeId: string;

    readonly label: string;

    readonly operationKind: string;

}