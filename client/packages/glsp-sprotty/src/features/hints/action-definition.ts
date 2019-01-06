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
import { Action } from "sprotty/lib";

export class RequestTypeHintsAction implements Action {
    static readonly KIND = "requestTypeHints"
    kind = RequestTypeHintsAction.KIND
}

export class SetTypeHintsAction implements Action {
    static readonly KIND = "setTypeHints"
    kind = SetTypeHintsAction.KIND
    constructor(public readonly nodeHints: NodeTypeHint[], public readonly edgeHints: EdgeTypeHint[]) { }
}

export function isSetTypeHintsAction(action: Action): action is SetTypeHintsAction {
    return action !== undefined && (action.kind === SetTypeHintsAction.KIND)
        && (<SetTypeHintsAction>action).nodeHints !== undefined && (<SetTypeHintsAction>action).edgeHints !== undefined
}

export interface TypeHint {
    /** 
    The id of the element.
    */
    readonly elementTypeId: string;

    /**
    * Specifies whether the element can be relocated.
    */
    readonly repositionable: boolean;

    /**
     * Specifices wheter the element can be deleted
     */
    readonly deletable: boolean;

}

export interface NodeTypeHint extends TypeHint {
    /**
     * Specifies whether the element can be resized.
     */
    readonly resizable: boolean;

    /**
     * The types of elements that can be contained by this element (if any)
     */
    readonly containableElementTypeIds?: string[];
}

export interface EdgeTypeHint extends TypeHint {

    /**
     * Specifies whether the routing of this element can be changed.
     */
    readonly routable: boolean;

    /**
     * Allowed source element types for this edge type
     */
    readonly sourceElementTypeIds: string[];

    /**
    * Allowed targe element types for this edge type
    */
    readonly targetElementTypeIds: string[];

}