import { injectable, inject } from "inversify";
import { MenuContribution, MenuPath, MAIN_MENU_BAR, MenuModelRegistry, CommandContribution, CommandRegistry } from "@theia/core";
import { DiagramMenus, DiagramCommandHandler } from "theia-glsp/lib";
import { ApplicationShell } from "@theia/core/lib/browser";
import { ExecuteNodeCreationToolAction } from "glsp-sprotty/lib";



export namespace GLSPDiagramCommands {
    export const CREATE_NODE = 'glsp:diagra:createNode'
}
@injectable()
export class GLSPDiagramMenuContribution implements MenuContribution {
    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction(DiagramMenus.DIAGRAM, {
            commandId: GLSPDiagramCommands.CREATE_NODE
        })
    }
}

@injectable()
export class GLSPDiagramCommandContribution implements CommandContribution {
    constructor(@inject(ApplicationShell) protected readonly shell: ApplicationShell) {
    }


    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand({
            id: GLSPDiagramCommands.CREATE_NODE,
            label: 'Create Node'
        }),
            registry.registerHandler(
                GLSPDiagramCommands.CREATE_NODE,
                new DiagramCommandHandler(this.shell, widget => {
                    let toolId = "wf-automated-task-tool"
                    let point = { x: 50, y: 50 }
                    let containerId = undefined
                    let action = new ExecuteNodeCreationToolAction(toolId, point, containerId);
                    widget.actionDispatcher.dispatch(action)
                }))
    }




}