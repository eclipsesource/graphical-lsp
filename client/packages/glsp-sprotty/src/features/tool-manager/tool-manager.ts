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
import { inject, injectable } from "inversify";
import { Action, ActionHandlerRegistry, IActionHandler, IActionHandlerInitializer, ICommand, KeyListener, SModelElement } from "sprotty/lib";
import { matchesKeystroke } from "sprotty/lib/utils/keyboard";
import { GLSP_TYPES } from "../../types";
import { isSetOperationsAction, SetOperationsAction } from "../operation/set-operations";
import { EnableStandardToolsAction, EnableToolsAction, Tool, UNDEFINED_TOOL_ID } from "./tool";

/**
 * A tool manager coordinates the state of tools in the context of an editor.
 *
 * One instance of a tool manager is intended per editor, coordinating the state of all tools within
 * this editor. A tool can be active or not. A tool manager ensures that activating a set of tools
 * will disable all other tools, allowing them to invoke behavior when they become enabled or disabled.
 */
export interface ToolManager {

    /** All tools managed by this tool manager. */
    readonly managedTools: Tool[];

    /** The tools that are enabled by standard, whenever no other tool is enabled. */
    readonly standardTools: Tool[];

    /** The currently active tools, which are either specifically enabled tools, or the standard tools. */
    readonly activeTools: Tool[];

    /**
     * Enables the tools with the specified `toolIds`.
     * Therefore, this manager first disables currently active tools and then enable the
     * tools indicated in `toolIds`, making them the currently active tools. If this manager
     * doesn't manage one or more tools specified in `toolIds`, it'll do nothing. If not a
     * single tool that shall be enabled was found in the managed tools, it'll fall back to
     * the standard tools.
     *
     * @param tools The tools to be enabled.
     */
    enable(toolIds: string[]): void;

    /**
     * Enables all standard tools.
     */
    enableStandardTools(): void;

    /** Disables all currently active tools. After this call, no tool will be active anymore. */
    disableActiveTools(): void;

    registerStandardTools(...tools: Tool[]): void;

    registerTools(...tools: Tool[]): void;

    /**
     * Configures the toolmanager using a SetOperations action. For each available operation the corresponding
     * tool will be registered
     * @param action The action containing the available operations
     */
    configure(action: SetOperationsAction): void;
}

@injectable()
export class DefaultToolManager implements ToolManager {
    @inject(GLSP_TYPES.ToolFactory) readonly toolFactory: (operationKind: string) => Tool

    readonly standardTools: Tool[] = []
    readonly tools: Tool[] = []
    private actives: Tool[] = [];

    get managedTools(): Tool[] {
        return this.standardTools.concat(this.tools);
    }

    get activeTools(): Tool[] {
        return this.actives;
    }

    disableActiveTools() {
        this.actives.forEach(tool => tool.disable());
        this.actives = [];
    }

    enableStandardTools() {
        this.enable(this.standardTools.map(tool => tool.id));
    }

    enable(toolIds: string[]) {
        this.disableActiveTools();
        const tools = toolIds.map(id => this.tool(id))
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

    registerStandardTools(...tools: Tool[]) {
        for (const tool of tools) {
            this.standardTools.push(tool);
        }
    }

    registerTools(...tools: Tool[]) {
        for (const tool of tools) {
            this.tools.push(tool);
        }
    }

    configure(action: SetOperationsAction) {
        const configuredTools = action.operations.map(op => {
            const tool = this.toolFactory(op.operationKind)
            tool.elementTypeId = op.elementTypeId;
            return tool;
        }).filter(tool => tool.id !== UNDEFINED_TOOL_ID)

        this.registerTools(...configuredTools)

    }
}

@injectable()
export class ToolManagerActionHandlerInitializer implements IActionHandlerInitializer, IActionHandler {
    @inject(GLSP_TYPES.ToolManager)
    readonly toolManager: ToolManager;

    initialize(registry: ActionHandlerRegistry): void {
        registry.register(EnableStandardToolsAction.KIND, this);
        registry.register(EnableToolsAction.KIND, this);
        registry.register(SetOperationsAction.KIND, this);
    }

    handle(action: Action): void | ICommand | Action {
        if (action instanceof EnableStandardToolsAction) {
            this.toolManager.enableStandardTools();
        } else if (action instanceof EnableToolsAction) {
            this.toolManager.enable(action.toolIds);
        } else if (isSetOperationsAction(action)) {
            this.toolManager.configure(action);
        }
    }
}

@injectable()
export class StandardToolsEnablingKeyListener extends KeyListener {
    keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
        if (matchesKeystroke(event, 'Escape')) {
            return [new EnableStandardToolsAction()];
        }
        return [];
    }
}
