import { injectable, inject } from "inversify";
import { MenuModelRegistry, CommandRegistry, MAIN_MENU_BAR, MenuPath, MenuContribution, CommandContribution, Command } from "@theia/core";
import { Operation, OperationKind } from "glsp-sprotty/lib";
import { Disposable } from "vscode-jsonrpc";
import { GLSPTheiaDiagramServer } from "glsp-theia-extension/src/browser/diagram/glsp-theia-diagram-server";
import { TheiaSprottyConnector, ServiceRegistry } from "theia-glsp/lib";
import { Menu } from "@phosphor/widgets";
import { EditorManager } from "@theia/editor/lib/browser";
import { OperationService, OP_TYPES } from "glsp-sprotty/lib";


export namespace DiagramMenus {
    export const PALETTE: MenuPath = MAIN_MENU_BAR.concat("3_glsp_palette")
    export const CREATE_NODE: MenuPath = PALETTE.concat("1_create_node");
    export const CREATE_CONNECTION: MenuPath = PALETTE.concat("2_create_connection");

}

export namespace PaletteCommands {
    export const CREATE_AUTOMATED_TASK = { id: "create:automated:task", label: "Automated Task" }
    export const CREATE_MANUAL_TASK = { id: "create:manual:task", label: "Manual Task" }
    export const DELETE_ELEMENT = { id: "delete:automated:task", label: "Delete Element" }
    export const CREATE_EDGE = { id: "create:edge", label: "Edge" }
    export const CREATE_WEIGHTED_EDGE = { id: "create:weighted:edge", label: "Weighted Edge" }


}


@injectable()
export class GLSPPaletteContribution implements MenuContribution, CommandContribution {
    @inject(ServiceRegistry) private serviceRegistry: ServiceRegistry

    private commandCounter: number = 0
    private diagramServer: GLSPTheiaDiagramServer;
    private operationService: OperationService


    register(diagramServer: GLSPTheiaDiagramServer): any {
        this.diagramServer = diagramServer
        if (!this.operationService) {
            const service = this.serviceRegistry.getService(OP_TYPES.OperationService)
            if (service) this.operationService = service as OperationService
        }

    }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerSubmenu(DiagramMenus.PALETTE, "Palette")
        menus.registerSubmenu(DiagramMenus.CREATE_NODE, "Create Node")
        menus.registerSubmenu(DiagramMenus.CREATE_CONNECTION, "Create Connection")

        menus.registerMenuAction(DiagramMenus.PALETTE, { commandId: PaletteCommands.DELETE_ELEMENT.id, label: "Delete Element" })

        menus.registerMenuAction(DiagramMenus.CREATE_NODE, { commandId: PaletteCommands.CREATE_AUTOMATED_TASK.id, label: "Automated Task" })
        menus.registerMenuAction(DiagramMenus.CREATE_NODE, { commandId: PaletteCommands.CREATE_MANUAL_TASK.id, label: "Manual Task" })
        menus.registerMenuAction(DiagramMenus.CREATE_CONNECTION, { commandId: PaletteCommands.CREATE_WEIGHTED_EDGE.id, label: "Weighted Edge" })
        menus.registerMenuAction(DiagramMenus.CREATE_CONNECTION, { commandId: PaletteCommands.CREATE_EDGE.id, label: "Edge" })
    }

    registerCommands(commands: CommandRegistry): void {
        commands.registerCommand(PaletteCommands.CREATE_AUTOMATED_TASK)
        commands.registerCommand(PaletteCommands.CREATE_WEIGHTED_EDGE)
        commands.registerCommand(PaletteCommands.CREATE_MANUAL_TASK)
        commands.registerCommand(PaletteCommands.CREATE_EDGE)
        commands.registerCommand(PaletteCommands.DELETE_ELEMENT)

        let createManualTask = { elementTypeId: "wf-manual-task", operationKind: OperationKind.CREATE_NODE, label: PaletteCommands.CREATE_MANUAL_TASK.label }
        commands.registerHandler(PaletteCommands.CREATE_MANUAL_TASK.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createManualTask) })

        let createAutomatedTask = { elementTypeId: "wf-automated-task", operationKind: OperationKind.CREATE_NODE, label: PaletteCommands.CREATE_AUTOMATED_TASK.label }
        commands.registerHandler(PaletteCommands.CREATE_AUTOMATED_TASK.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createAutomatedTask) })
        let deleteElement = { operationKind: OperationKind.DELETE_ELEMENT, label: PaletteCommands.DELETE_ELEMENT.label }
        commands.registerHandler(PaletteCommands.DELETE_ELEMENT.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, deleteElement) })
        let createWeightedEdge = { elementTypeId: "wf-weighted-edge", operationKind: OperationKind.CREATE_CONNECTION, label: PaletteCommands.CREATE_WEIGHTED_EDGE.label }
        commands.registerHandler(PaletteCommands.CREATE_WEIGHTED_EDGE.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createWeightedEdge) })
        let createEdge = { elementTypeId: "wf-edge", operationKind: OperationKind.CREATE_CONNECTION, label: PaletteCommands.CREATE_EDGE.label }
        commands.registerHandler(PaletteCommands.CREATE_EDGE.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createEdge) })
    }


}

