import { injectable, inject } from "inversify";
import { MenuModelRegistry, CommandRegistry, MAIN_MENU_BAR, MenuPath, MenuContribution, CommandContribution, Command } from "@theia/core";
import { Operation, OperationKind } from "glsp-sprotty/lib";
import { Disposable } from "vscode-jsonrpc";
import { GLSPTheiaDiagramServer } from "glsp-theia-extension/src/browser/diagram/glsp-theia-diagram-server";
import { TheiaSprottyConnector, TheiaDiagramServer } from "theia-glsp/lib";
import { Menu } from "@phosphor/widgets";
import { EditorManager } from "@theia/editor/lib/browser";


export namespace DiagramMenus {
    export const PALETTE: MenuPath = MAIN_MENU_BAR.concat("4_glsp_palette")
    export const CREATE_NODE: MenuPath = PALETTE.concat("1_create_node");
    export const CREATE_CONNECTION: MenuPath = PALETTE.concat("2_create_connection");
    export const DELETE_ELEMENT: MenuPath = PALETTE.concat("3_delete_element");

}

export namespace PaletteCommands {
    export const CREATE_AUTOMATED_TASK = { id: "create:automated:task", label: "Automated Task" }
    export const DELETE_AUTOMATED_TASK = { id: "delete:automated:task", label: "Automated Task" }
    export const CREATE_WEIGHTED_EDGE = { id: "create:weighted:edge", label: "Weighted Edge" }
    export const DELETE_WEIGHTED_EDGE = { id: "delete:weighted:edge", label: "Weighted Edge" }

}
@injectable()
export class OperationService {

    private currentCommands: { [id: string]: Operation; } = {};
    private previouslySelectedOperations: { [id: string]: Operation; } = {};

    public setCurrentOperation(id: string, operation: Operation) {

        this.previouslySelectedOperations[id] = this.currentCommands[id]
        this.currentCommands[id] = operation

    }

    public getCurrentOperation(id: string): Operation | undefined {
        return this.currentCommands[id]
    }


    public getPreviouslySelectedOperation(id: string): Operation | undefined {
        return this.previouslySelectedOperations[id]
    }
}


@injectable()
export class GLSPPaletteContribution implements MenuContribution, CommandContribution {

    @inject(OperationService) private readonly operationService: OperationService
    private commandCounter: number = 0
    private diagramServer: GLSPTheiaDiagramServer;



    register(diagramServer: GLSPTheiaDiagramServer): any {
        this.diagramServer = diagramServer

    }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerSubmenu(DiagramMenus.PALETTE, "Palette")
        menus.registerSubmenu(DiagramMenus.CREATE_NODE, "Create node")
        menus.registerSubmenu(DiagramMenus.CREATE_CONNECTION, "Create element")
        menus.registerSubmenu(DiagramMenus.DELETE_ELEMENT, "Delete")

        menus.registerMenuAction(DiagramMenus.CREATE_NODE, { commandId: PaletteCommands.CREATE_AUTOMATED_TASK.id, label: "Create automated task" })
        menus.registerMenuAction(DiagramMenus.DELETE_ELEMENT, { commandId: PaletteCommands.DELETE_AUTOMATED_TASK.id, label: "Delete automated task" })
        menus.registerMenuAction(DiagramMenus.CREATE_CONNECTION, { commandId: PaletteCommands.CREATE_WEIGHTED_EDGE.id, label: "Create weighted edge" })
        menus.registerMenuAction(DiagramMenus.DELETE_ELEMENT, { commandId: PaletteCommands.DELETE_WEIGHTED_EDGE.id, label: "Delete weighted edge" })
    }

    registerCommands(commands: CommandRegistry): void {
        commands.registerCommand(PaletteCommands.CREATE_AUTOMATED_TASK)
        commands.registerCommand(PaletteCommands.CREATE_WEIGHTED_EDGE)
        commands.registerCommand(PaletteCommands.DELETE_AUTOMATED_TASK)
        commands.registerCommand(PaletteCommands.DELETE_WEIGHTED_EDGE)
        let createTask = { elementTypeId: "node:task", operationKind: OperationKind.CREATE_NODE, label: PaletteCommands.CREATE_AUTOMATED_TASK.label }
        commands.registerHandler(PaletteCommands.CREATE_AUTOMATED_TASK.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createTask) })
        let deleteTask = { elementTypeId: "node:task", operationKind: OperationKind.DELETE_ELEMENT, label: PaletteCommands.DELETE_AUTOMATED_TASK.label }
        commands.registerHandler(PaletteCommands.DELETE_AUTOMATED_TASK.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, deleteTask) })
        let createConnection = { elementTypeId: "weighted:edge", operationKind: OperationKind.CREATE_CONNECTION, label: PaletteCommands.CREATE_WEIGHTED_EDGE.label }
        commands.registerHandler(PaletteCommands.CREATE_WEIGHTED_EDGE.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createConnection) })
        let deleteConnection = { elementTypeId: "weighted:edge", operationKind: OperationKind.DELETE_ELEMENT, label: PaletteCommands.DELETE_WEIGHTED_EDGE.label }
        commands.registerHandler(PaletteCommands.DELETE_WEIGHTED_EDGE.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, deleteConnection) })
    }




    // public createTools(operations: Operation[]): void {
    //     operations.forEach(op => {
    //         let menu = this.findCorrespondingSubMenu(op.operationKind)
    //         this.commandCounter++
    //         let command = { id: 'paletteCommand' + this.commandCounter, label: op.label }

    //         this.commandRegistry.registerCommand(command, { execute: () => { }, isVisible: () => true, isEnabled: () => true })
    //         if (menu) {
    //             this.menus.registerMenuAction(menu.concat(this.commandCounter + '_entry'), { commandId: command.id, label: command.label })
    //             this.commandCounter++;
    //             this.menus.registerMenuAction(menu.concat(this.commandCounter + '_entry'), { commandId: command.id, label: command.label })
    //             this.commandCounter++;
    //             this.menus.registerMenuAction(menu.concat(this.commandCounter + '_entry'), { commandId: command.id, label: command.label })
    //         }
    //     });

    // }

    private findCorrespondingSubMenu(operationKind: string): MenuPath | undefined {
        switch (operationKind) {
            case OperationKind.CREATE_CONNECTION:
                return DiagramMenus.CREATE_CONNECTION
            case OperationKind.CREATE_NODE:
                return DiagramMenus.CREATE_NODE
            case OperationKind.DELETE_ELEMENT:
                return DiagramMenus.DELETE_ELEMENT
            default:
                return undefined

        }
    }



}

