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
import { BaseDiagramUIExtension } from "../../base/diagram-ui-extension/diagram-ui-extension";
import { EnableDefaultToolsAction } from "sprotty/lib";
import { EnableToolsAction } from "sprotty/lib";
import { IActionDispatcherProvider } from "sprotty/lib";
import { ICommand } from "sprotty/lib";
import { ILogger } from "sprotty/lib";
import { MouseDeleteTool } from "../tools/delete-tool";
import { Operation } from "../operation/set-operations";
import { SelfInitializingActionHandler } from "../../base/diagram-ui-extension/diagram-ui-extension-registry";
import { SetOperationsAction } from "../operation/set-operations";
import { ShowDiagramUIExtensionAction } from "../../base/diagram-ui-extension/diagram-ui-extension-registry";
import { TYPES } from "sprotty/lib";
import { ViewerOptions } from "sprotty/lib";

import { deriveToolId } from "../tools/creation-tool";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isSetOperationsAction } from "../operation/set-operations";
import { parentGroup } from "../operation/set-operations";

const CLICKED_CSS_CLASS = "clicked";
@injectable()
export class ToolPalette extends BaseDiagramUIExtension {
    static readonly ID = "glsp_tool_palette"

    readonly id = ToolPalette.ID
    readonly containerClass = "tool-palette"
    protected operations: Operation[];
    protected lastActivebutton?: HTMLElement;
    protected defaultToolsButton: HTMLElement;

    constructor(
        @inject(TYPES.ViewerOptions) protected options: ViewerOptions,
        @inject(TYPES.IActionDispatcherProvider) protected actionDispatcherProvider: IActionDispatcherProvider,
        @inject(TYPES.ILogger) protected logger: ILogger) {
        super(options, actionDispatcherProvider, logger)
    }

    initialize() {
        if (!this.operations) {
            return false;
        }
        return super.initialize()
    }

    protected createUIElements(): void {
        this.createHeader()
        this.createBody()
    }

    protected createBody(): void {
        const bodyDiv = document.createElement("div")
        bodyDiv.classList.add("palette-body")
        // Greate operation groups
        const groups: Map<string, HTMLElement> = new Map();
        this.operations.map(parentGroup).forEach(group => {
            if (!groups.has(group.id)) {
                groups.set(group.id, createToolGroup(group.label, group.id));
            }
        });

        // Fill groups
        this.operations.forEach(op => {
            const button = this.createToolButton(op);
            const group = parentGroup(op);
            const htmlGroup = groups.get(group.id);
            if (htmlGroup) {
                htmlGroup.appendChild(button);
            }
        })

        // Add groups to container
        Array.from(groups.values()).forEach(group => bodyDiv.appendChild(group));
        this.containerElement.appendChild(bodyDiv)
    }
    protected createHeader(): void {
        const headerCompartment = document.createElement("div")
        headerCompartment.classList.add("palette-header")

        // Title header
        const header = document.createElement("div")
        header.classList.add("header-icon")
        header.appendChild(createIcon(["fa", "fa-palette"]));
        header.insertAdjacentText("beforeend", "Palette")
        headerCompartment.append(header)
        // Header Tools Compartment
        const headerTools = document.createElement("div")
        headerTools.classList.add("header-tools")

        // Create button for DefaultTools
        this.defaultToolsButton = createIcon(["fas", "fa-mouse-pointer", "fa-xs", "clicked"])
        this.defaultToolsButton.id = "btn_default_tools"
        this.defaultToolsButton.onclick = this.onClickToolButton(this.defaultToolsButton)
        headerTools.appendChild(this.defaultToolsButton)
        this.lastActivebutton = this.defaultToolsButton

        // Create button for MouseDeleteTool
        const deleteToolButton = createIcon(["fas", "fa-eraser", "fa-xs"])
        deleteToolButton.onclick = this.onClickToolButton(deleteToolButton, MouseDeleteTool.ID)
        headerTools.appendChild(deleteToolButton)

        headerCompartment.appendChild(headerTools)
        this.containerElement.appendChild(headerCompartment)
    }

    protected createToolButton(operation: Operation): HTMLElement {
        const button = document.createElement("div")
        button.classList.add("tool-button")
        button.innerHTML = operation.label
        button.onclick = this.onClickToolButton(button, deriveToolId(operation.operationKind, operation.elementTypeId))
        return button;
    }

    protected onClickToolButton(button: HTMLElement, toolId?: string) {
        return (ev: MouseEvent) => {
            const action = toolId ? new EnableToolsAction([toolId]) : new EnableDefaultToolsAction()
            this.executeAction(action)
            this.changeActiveButton(button)
            this.restoreFocus()
        }
    }

    setOperations(operations: Operation[]) {
        this.operations = operations
    }

    changeActiveButton(button?: HTMLElement) {
        if (this.lastActivebutton) {
            this.lastActivebutton.classList.remove(CLICKED_CSS_CLASS)
        }
        if (button) {
            button.classList.add(CLICKED_CSS_CLASS)
            this.lastActivebutton = button
        } else {
            this.defaultToolsButton.classList.add(CLICKED_CSS_CLASS)
            this.lastActivebutton = this.defaultToolsButton
        }
    }
}

function createIcon(cssClasses: string[]) {
    const icon = document.createElement("i")
    icon.classList.add(...cssClasses)
    return icon
}

function createToolGroup(label: string, groupId: string): HTMLElement {
    const group = document.createElement("div")
    group.classList.add("tool-group")
    group.id = groupId
    const header = document.createElement("div")
    header.classList.add("group-header")
    header.appendChild(createIcon(["fas", "fa-hammer"]))
    header.insertAdjacentText('beforeend', label)
    header.ondblclick = (ev) => {
        Array.from(group.children).forEach(item => {
            item.classList.contains("collapsed") ? item.classList.remove("collapsed") :
                item.classList.add("collapsed");
        });
        window.getSelection().removeAllRanges();
    };

    group.appendChild(header)
    return group;
}

@injectable()
export class ToolPaletteActionHandler extends SelfInitializingActionHandler {
    @inject(ToolPalette) protected readonly toolPalette: ToolPalette

    readonly handledActionKinds = [SetOperationsAction.KIND, EnableDefaultToolsAction.KIND]

    handle(action: Action): ICommand | Action | void {
        if (isSetOperationsAction(action)) {
            this.toolPalette.setOperations(action.operations)
            return new ShowDiagramUIExtensionAction(ToolPalette.ID, [])
        } else if (action instanceof EnableDefaultToolsAction) {
            this.toolPalette.changeActiveButton();
        }
    }
}