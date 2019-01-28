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
import { inject, injectable, interfaces } from "inversify";
import { Action, ActionHandlerRegistry, IActionHandler, IActionHandlerInitializer, ICommand, KeyListener, SModelElement } from "sprotty/lib";
import { matchesKeystroke } from "sprotty/lib/utils/keyboard";
import { isSetOperationsAction, OperationKind, SetOperationsAction } from "../../features/operation/set-operations";
import { EdgeCreationTool, NodeCreationTool } from "../../features/tools/creation-tool";
import { MouseDeleteTool } from "../../features/tools/delete-tool";
import { GLSP_TYPES } from "../../types";
import { EnableDefaultToolsAction, EnableToolsAction, Tool } from "./tool";

/**
 * A tool manager coordinates the state of tools in the context of an editor.
 *
 * One instance of a tool manager is intended per editor, coordinating the state of all tools within
 * this editor. A tool can be active or not. A tool manager ensures that activating a set of tools
 * will disable all other tools, allowing them to invoke behavior when they become enabled or disabled.
 */
export interface IToolManager {

    /** All tools managed by this tool manager. */
    readonly managedTools: Tool[];

    /** The tools that are enabled by default, whenever no other tool is enabled. */
    readonly defaultTools: Tool[];

    /** The currently active tools, which are either specifically enabled tools, or the default tools. */
    readonly activeTools: Tool[];

    /**
     * Enables the tools with the specified `toolIds`.
     * Therefore, this manager first disables currently active tools and then enable the
     * tools indicated in `toolIds`, making them the currently active tools. If this manager
     * doesn't manage one or more tools specified in `toolIds`, it'll do nothing. If not a
     * single tool that shall be enabled was found in the managed tools, it'll fall back to
     * the default tools.
     *
     * @param tools The tools to be enabled.
     */
    enable(toolIds: string[]): void;

    /**
     * Enables all default tools.
     */
    enableDefaultTools(): void;

    /** Disables all currently active tools. After this call, no tool will be active anymore. */
    disableActiveTools(): void;

    registerDefaultTools(...tools: Tool[]): void;

    registerTools(...tools: Tool[]): void;
}

@injectable()
export class ToolManager implements IToolManager {

    readonly tools: Tool[] = [];
    readonly defaultTools: Tool[] = [];
    readonly actives: Tool[] = [];

    get managedTools(): Tool[] {
        return this.defaultTools.concat(this.tools);
    }

    get activeTools(): Tool[] {
        return this.actives;
    }

    disableActiveTools() {
        this.actives.forEach(tool => tool.disable());
        this.actives.splice(0, this.actives.length);
    }

    enableDefaultTools() {
        this.enable(this.defaultTools.map(tool => tool.id));
    }

    enable(toolIds: string[]) {
        this.disableActiveTools();
        const tools = toolIds.map(id => this.tool(id));
        tools.forEach(tool => {
            if (tool !== undefined) {
                tool.enable();
                this.actives.push(tool);
            }
        });
    }

    tool(toolId: string): Tool | undefined {
        return this.managedTools.find(tool => tool.id === toolId);
    }

    registerDefaultTools(...tools: Tool[]) {
        for (const tool of tools) {
            this.defaultTools.push(tool);
        }
    }

    registerTools(...tools: Tool[]) {
        for (const tool of tools) {
            this.tools.push(tool);
        }
    }
}

@injectable()
export class ToolManagerActionHandlerInitializer implements IActionHandlerInitializer, IActionHandler {
    @inject(GLSP_TYPES.IToolManager)
    readonly toolManager: IToolManager;

    initialize(registry: ActionHandlerRegistry): void {
        registry.register(EnableDefaultToolsAction.KIND, this);
        registry.register(EnableToolsAction.KIND, this);
    }

    handle(action: Action): void | ICommand | Action {
        if (action instanceof EnableDefaultToolsAction) {
            this.toolManager.enableDefaultTools();
        } else if (action instanceof EnableToolsAction) {
            this.toolManager.enable(action.toolIds);
        }
    }
}

@injectable()
export class DefaultToolsEnablingKeyListener extends KeyListener {
    keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
        if (matchesKeystroke(event, 'Escape')) {
            return [new EnableDefaultToolsAction()];
        }
        return [];
    }
}

// Code below remains after https://github.com/eclipse/sprotty/pull/33 is merged

@injectable()
export class GLSPToolManagerActionHandlerInitializer extends ToolManagerActionHandlerInitializer {
    @inject(GLSP_TYPES.IToolFactory) readonly toolFactory: (operationKind: string) => Tool
    initialize(registry: ActionHandlerRegistry): void {
        super.initialize(registry)
        registry.register(SetOperationsAction.KIND, this);
    }

    handle(action: Action): void | ICommand | Action {
        if (isSetOperationsAction(action)) {
            this.configure(action)
        } else {
            return super.handle(action)
        }
    }
    configure(action: SetOperationsAction): any {
        const configuredTools = action.operations.map(op => {
            const tool = this.toolFactory(op.operationKind)
            if (isTypeAware(tool) && op.elementTypeId) {
                tool.elementTypeId = op.elementTypeId;
            }

            return tool;
        }).filter(tool => tool.id !== UNDEFINED_TOOL_ID)

        this.toolManager.registerTools(...configuredTools)
    }
}

export function isTypeAware(tool: Tool): tool is Tool & TypeAware {
    return (<any>tool)["elementTypeId"] !== undefined && typeof (<any>tool)["elementTypeId"] === "string"
}

export interface TypeAware {
    elementTypeId: string;
}
const UNDEFINED_TOOL_ID = "undefined-tool"
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
                        disable() { },
                        enable() { }
                    }
            }
        };
    }
}