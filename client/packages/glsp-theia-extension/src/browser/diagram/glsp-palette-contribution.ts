import { injectable, inject } from "inversify";
import { MenuModelRegistry, CommandRegistry, MAIN_MENU_BAR, MenuPath, MenuContribution } from "@theia/core";
import { Operation, OperationKind } from "glsp-sprotty/lib";
import { Disposable } from "vscode-jsonrpc";
import { GLSPTheiaDiagramServer } from "glsp-theia-extension/src/browser/diagram/glsp-theia-diagram-server";
import { TheiaSprottyConnector, TheiaDiagramServer } from "theia-glsp/lib";


export namespace DiagramMenus {
    export const PALETTE: MenuPath = MAIN_MENU_BAR.concat("4_glsp_palette")
    export const CREATE_NODE: MenuPath = PALETTE.concat("1_create_node");
    export const CREATE_CONNECTION: MenuPath = PALETTE.concat("2_create_connection");
    export const DELETE_ELEMENT: MenuPath = PALETTE.concat("3_delete_element");

}
@injectable()
export class GLSPPaletteContribution {

    @inject(CommandRegistry) private readonly commandRegistry: CommandRegistry;
    @inject(MenuModelRegistry) private readonly menus: MenuModelRegistry

    private removeMenu?: Disposable
    private disposeOnRemove: Disposable[]
    private commandCounter: number = 0
    private diagramServer: TheiaDiagramServer;

    register(diagramServer: TheiaDiagramServer): any {
        this.diagramServer = diagramServer
        if (!this.removeMenu) {
            this.init()
        }
    }
    protected init(): void {
        this.disposeOnRemove = []
        this.disposeOnRemove.concat(this.removeMenu = this.menus.registerSubmenu(DiagramMenus.PALETTE, "Palette"))

        this.disposeOnRemove.concat(this.menus.registerSubmenu(DiagramMenus.CREATE_NODE, "Create node"))
        this.disposeOnRemove.concat(this.menus.registerSubmenu(DiagramMenus.CREATE_CONNECTION, "Create element"))
        this.disposeOnRemove.concat(this.menus.registerSubmenu(DiagramMenus.DELETE_ELEMENT, "Delete"))
    }

    public removePalette(): void {
        this.disposeOnRemove.forEach(d => d.dispose())
    }




    public createTools(operations: Operation[]): void {
        operations.forEach(op => {
            let menu = this.findCorrespondingSubMenu(op.operationKind)
            this.commandCounter++
            let command = { id: 'paletteCommand' + this.commandCounter, label: op.label }

            this.disposeOnRemove.concat(this.commandRegistry.registerCommand(command, { execute: () => { } }))
            if (menu) {
                this.menus.registerMenuAction(menu.concat(this.commandCounter + '_entry'), { commandId: command.id, label: command.label })
            }
        });

    }

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
