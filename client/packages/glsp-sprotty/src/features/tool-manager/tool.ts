/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
import { interfaces } from "inversify";
import { Action } from "sprotty/lib";
import { OperationKind } from "../operation/set-operations";
import { EdgeCreationTool, NodeCreationTool } from "../tools/creation-tool";
import { MouseDeleteTool } from "../tools/delete-tool";

/**
 * Action to enable the tools of the specified `toolIds`.
 */
export class EnableToolsAction implements Action {
    static KIND = "enable-tools";
    readonly kind = EnableToolsAction.KIND;
    constructor(public readonly toolIds: string[]) { }
}

/**
 * Action to disable the currently active tools and enable the standard tools instead.
 */
export class EnableStandardToolsAction implements Action {
    static KIND = "enable-standard-tools";
    readonly kind = EnableStandardToolsAction.KIND;
}


/** A tool that can be managed by a `ToolManager`. */
export interface Tool {
    readonly id: string;
    elementTypeId?: string;
    /* Notifies the tool to become active. */
    enable(): void;
    /* Notifies the tool to become inactive. */
    disable(): void;

}

export const UNDEFINED_TOOL_ID = "undefinedTool"


export function createToolFactory(): interfaces.FactoryCreator<Tool> {
    return (context: interfaces.Context) => {
        return (operationKind: string) => {
            switch (operationKind) {
                case OperationKind.CREATE_NODE:
                    return context.container.resolve(NodeCreationTool);
                case OperationKind.CREATE_CONNECTION:
                    return context.container.resolve(EdgeCreationTool);
                case OperationKind.DELETE_ELEMENT:
                    return context.container.resolve(MouseDeleteTool)
                default:
                    return {
                        id: UNDEFINED_TOOL_ID,
                        elementTypeId: UNDEFINED_TOOL_ID,
                        disable() { },
                        enable() { }
                    }
            }
        };
    }
}