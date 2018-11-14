/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
import { CommandContribution, CommandRegistry, MAIN_MENU_BAR, MenuContribution, MenuModelRegistry, MenuPath } from "@theia/core";
import { ApplicationShell } from "@theia/core/lib/browser";
import { EdgeCreationTool, EnableToolsAction, MouseDeleteTool, NodeCreationTool } from "glsp-sprotty/lib";
import { inject, injectable } from "inversify";
import { DiagramCommandHandler } from "theia-glsp/lib";
import { GLSPTheiaDiagramServer } from "./glsp-theia-diagram-server";



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
    export const CREATE_DECISION_NODE = { id: "create:decision:node", label: "Decision Node" }
    export const CREATE_MERGE_NODE = { id: "create:merge:node", label: "Merge Node" }
}


@injectable()
export class GLSPPaletteContribution implements MenuContribution, CommandContribution {
    @inject(ApplicationShell) private shell: ApplicationShell
    private commandCounter: number = 0
    private diagramServer: GLSPTheiaDiagramServer;

    register(diagramServer: GLSPTheiaDiagramServer): any {
        this.diagramServer = diagramServer
    }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerSubmenu(DiagramMenus.PALETTE, "Palette")
        menus.registerSubmenu(DiagramMenus.CREATE_NODE, "Create Node")
        menus.registerSubmenu(DiagramMenus.CREATE_CONNECTION, "Create Connection")

        menus.registerMenuAction(DiagramMenus.PALETTE, { commandId: PaletteCommands.DELETE_ELEMENT.id, label: "Delete Element" })

        menus.registerMenuAction(DiagramMenus.CREATE_NODE, { commandId: PaletteCommands.CREATE_AUTOMATED_TASK.id, label: "Automated Task" })
        menus.registerMenuAction(DiagramMenus.CREATE_NODE, { commandId: PaletteCommands.CREATE_MANUAL_TASK.id, label: "Manual Task" })
        menus.registerMenuAction(DiagramMenus.CREATE_NODE, { commandId: PaletteCommands.CREATE_DECISION_NODE.id, label: "Decision Node" })
        menus.registerMenuAction(DiagramMenus.CREATE_NODE, { commandId: PaletteCommands.CREATE_MERGE_NODE.id, label: "Merge Node" })
        menus.registerMenuAction(DiagramMenus.CREATE_CONNECTION, { commandId: PaletteCommands.CREATE_WEIGHTED_EDGE.id, label: "Weighted Edge" })
        menus.registerMenuAction(DiagramMenus.CREATE_CONNECTION, { commandId: PaletteCommands.CREATE_EDGE.id, label: "Edge" })
    }

    registerCommands(commands: CommandRegistry): void {
        commands.registerCommand(PaletteCommands.CREATE_AUTOMATED_TASK)
        commands.registerCommand(PaletteCommands.CREATE_WEIGHTED_EDGE)
        commands.registerCommand(PaletteCommands.CREATE_MANUAL_TASK)
        commands.registerCommand(PaletteCommands.CREATE_DECISION_NODE)
        commands.registerCommand(PaletteCommands.CREATE_MERGE_NODE)
        commands.registerCommand(PaletteCommands.CREATE_EDGE)
        commands.registerCommand(PaletteCommands.DELETE_ELEMENT)

        commands.registerHandler(PaletteCommands.CREATE_AUTOMATED_TASK.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new EnableToolsAction([`${NodeCreationTool.ID}.wf-automated-task`]))
            )
        )

        commands.registerHandler(PaletteCommands.CREATE_MANUAL_TASK.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new EnableToolsAction([`${NodeCreationTool.ID}.wf-manual-task`]))
            )
        )

        commands.registerHandler(PaletteCommands.CREATE_DECISION_NODE.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new EnableToolsAction([`${NodeCreationTool.ID}.wf-decision-node`]))
            )
        )

        commands.registerHandler(PaletteCommands.CREATE_MERGE_NODE.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new EnableToolsAction([`${NodeCreationTool.ID}.wf-merge-node`]))
            )
        )

        commands.registerHandler(PaletteCommands.DELETE_ELEMENT.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new EnableToolsAction([MouseDeleteTool.ID]))
            )
        )

        commands.registerHandler(PaletteCommands.CREATE_WEIGHTED_EDGE.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new EnableToolsAction([`${EdgeCreationTool.ID}.wf-weighted-edge`]))
            )
        )

        commands.registerHandler(PaletteCommands.CREATE_EDGE.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new EnableToolsAction([`${EdgeCreationTool.ID}.wf-edge`]))
            )
        )
    }

}
