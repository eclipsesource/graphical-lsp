import { injectable, inject } from "inversify";
import { MenuContribution, MenuPath, MAIN_MENU_BAR, MenuModelRegistry, CommandContribution, CommandRegistry } from "@theia/core";
import { DiagramMenus, DiagramCommandHandler } from "theia-glsp/lib";
import { ApplicationShell, Widget } from "@theia/core/lib/browser";
import { ExecuteNodeCreationToolAction, SaveModelAction } from "glsp-sprotty/lib";



export namespace GLSPDiagramCommands {
    export const CREATE_NODE = 'glsp:diagram:createNode';
    export const SAVE_MODEL = 'glsp:diagram:saveModel'
}
@injectable()
export class GLSPDiagramMenuContribution implements MenuContribution {
    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction(DiagramMenus.DIAGRAM, {
            commandId: GLSPDiagramCommands.CREATE_NODE
        });
        menus.registerMenuAction(DiagramMenus.DIAGRAM, {
            commandId: GLSPDiagramCommands.SAVE_MODEL
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
        });
        registry.registerCommand({
            id: GLSPDiagramCommands.SAVE_MODEL,
            label: 'Save Model'
        })
        registry.registerHandler(
            GLSPDiagramCommands.CREATE_NODE,
            new DiagramCommandHandler(this.shell, widget => {
                let toolId = "wf-automated-task-tool"
                let point = { x: 50, y: 50 }
                let containerId = undefined
                let action = new ExecuteNodeCreationToolAction(toolId, point, containerId);
                widget.actionDispatcher.dispatch(action)
            }))
        registry.registerHandler(GLSPDiagramCommands.SAVE_MODEL, new DiagramCommandHandler(this.shell, widget => {
            widget.actionDispatcher.dispatch(new SaveModelAction())
        }))
    }




}