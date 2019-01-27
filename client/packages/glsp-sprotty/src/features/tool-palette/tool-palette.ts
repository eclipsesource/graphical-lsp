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
import { inject, injectable } from "inversify";
import {
    Action, ActionHandlerRegistry, IActionDispatcherProvider, IActionHandler, IActionHandlerInitializer, ICommand, ILogger, //
    TYPES, ViewerOptions
} from "sprotty/lib";
import "../../../css/tool-palette.css";
import { BaseDiagramUIExtension } from "../../base/diagram-ui-extension/diagram-ui-extension";
import { ShowDiagramUIExtensionAction } from "../../base/diagram-ui-extension/diagram-ui-extension-registry";
import { EnableDefaultToolsAction, EnableToolsAction } from "../../base/tool-manager/tool";
import { isSetOperationsAction, Operation, OperationKind, SetOperationsAction } from "../operation/set-operations";
import { deriveToolId } from "../tools/creation-tool";
import { MouseDeleteTool } from "../tools/delete-tool";

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
        const nodeGroup = createToolGroup("Nodes", "palette_node_group")
        const edgeGroup = createToolGroup("Edges", "palette_edge_group")

        this.operations.forEach(op => {
            const button = this.createToolButton(op)
            if (op.operationKind === OperationKind.CREATE_NODE) {
                nodeGroup.appendChild(button)
            } else if (op.operationKind === OperationKind.CREATE_CONNECTION) {
                edgeGroup.appendChild(button)
            }
        })

        bodyDiv.appendChild(nodeGroup)
        bodyDiv.appendChild(edgeGroup)
        this.containerElement.appendChild(bodyDiv)
    }
    protected createHeader(): void {
        const header = document.createElement("div")
        header.classList.add("palette-header")
        header.appendChild(createIcon(["fa", "fa-palette"], "Palette"));

        // Header Tools Compartment
        const headerTools = document.createElement("div")
        headerTools.classList.add("header-tools")

        // Create button for DefaultTools
        this.defaultToolsButton = createIcon(["fas", "fa-mouse-pointer", "fa-xs", "clicked"])
        this.defaultToolsButton.id = "btn_default_tools"
        this.defaultToolsButton.onclick = (ev) => {
            this.executeAction(new EnableDefaultToolsAction())
            this.changeActiveButton()
        }
        headerTools.appendChild(this.defaultToolsButton)
        this.lastActivebutton = this.defaultToolsButton

        // Create button for MouseDeleteTool
        const deleteToolButton = createIcon(["fas", "fa-eraser", "fa-xs"])
        deleteToolButton.onclick = (ev) => {
            this.executeAction(new EnableToolsAction([MouseDeleteTool.ID]))
            this.changeActiveButton(deleteToolButton)
        }
        headerTools.appendChild(deleteToolButton)

        header.appendChild(headerTools)
        this.containerElement.appendChild(header)
    }

    protected createToolButton(operation: Operation): HTMLElement {
        const button = document.createElement("div")
        button.classList.add("tool-button")
        button.innerHTML = operation.label
        button.onclick = (ev) => {
            this.executeAction(new EnableToolsAction([deriveToolId(operation.operationKind, operation.elementTypeId)]))
            this.changeActiveButton(button)
            this.restoreFocus()
        }
        return button;
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

function createIcon(cssClasses: string[], label?: string) {
    const icon = document.createElement("i")
    icon.classList.add(...cssClasses)
    if (label) {
        icon.innerText = " " + label
    }
    return icon
}

function createToolGroup(label: string, groupId: string): HTMLElement {
    const group = document.createElement("div")
    group.classList.add("tool-group")
    group.id = groupId
    const header = document.createElement("div")
    header.classList.add("group-header")
    header.appendChild(createIcon(["fas", "fa-hammer"], label))
    group.appendChild(header)
    return group;
}

@injectable()
export class ToolPaletteActionInitializer implements IActionHandler, IActionHandlerInitializer {
    @inject(ToolPalette) protected readonly toolPalette: ToolPalette

    initialize(registry: ActionHandlerRegistry): void {
        registry.register(SetOperationsAction.KIND, this)
        registry.register(EnableDefaultToolsAction.KIND, this)
    }

    handle(action: Action): ICommand | Action | void {
        if (isSetOperationsAction(action)) {
            this.toolPalette.setOperations(action.operations)
            return new ShowDiagramUIExtensionAction(ToolPalette.ID, [])
        } else if (action instanceof EnableDefaultToolsAction) {
            this.toolPalette.changeActiveButton();
        }
    }
}