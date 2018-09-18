/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
import { CommandContribution, CommandRegistry, MAIN_MENU_BAR, MenuContribution, MenuModelRegistry, MenuPath } from "@theia/core";
import { OperationKind, OperationService, OP_TYPES } from "glsp-sprotty/lib";
import { GLSPTheiaDiagramServer } from "./glsp-theia-diagram-server";
import { inject, injectable } from "inversify";



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
    @inject(OP_TYPES.OperationService) private operationService: OperationService
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

        let createManualTask = { elementTypeId: "wf-manual-task", operationKind: OperationKind.CREATE_NODE, label: PaletteCommands.CREATE_MANUAL_TASK.label }
        commands.registerHandler(PaletteCommands.CREATE_MANUAL_TASK.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createManualTask) })
        let createAutomatedTask = { elementTypeId: "wf-automated-task", operationKind: OperationKind.CREATE_NODE, label: PaletteCommands.CREATE_AUTOMATED_TASK.label }
        commands.registerHandler(PaletteCommands.CREATE_AUTOMATED_TASK.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createAutomatedTask) })
        let createDecisionNode = { elementTypeId: "wf-decision-node", operationKind: OperationKind.CREATE_NODE, label: PaletteCommands.CREATE_DECISION_NODE.label }
        commands.registerHandler(PaletteCommands.CREATE_DECISION_NODE.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createDecisionNode) })
        let createMergeNode = { elementTypeId: "wf-merge-node", operationKind: OperationKind.CREATE_NODE, label: PaletteCommands.CREATE_MERGE_NODE.label }
        commands.registerHandler(PaletteCommands.CREATE_MERGE_NODE.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createMergeNode) })

        let deleteElement = { operationKind: OperationKind.DELETE_ELEMENT, label: PaletteCommands.DELETE_ELEMENT.label }
        commands.registerHandler(PaletteCommands.DELETE_ELEMENT.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, deleteElement) })
        let createWeightedEdge = { elementTypeId: "wf-weighted-edge", operationKind: OperationKind.CREATE_CONNECTION, label: PaletteCommands.CREATE_WEIGHTED_EDGE.label }
        commands.registerHandler(PaletteCommands.CREATE_WEIGHTED_EDGE.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createWeightedEdge) })
        let createEdge = { elementTypeId: "wf-edge", operationKind: OperationKind.CREATE_CONNECTION, label: PaletteCommands.CREATE_EDGE.label }
        commands.registerHandler(PaletteCommands.CREATE_EDGE.id, { execute: () => this.operationService.setCurrentOperation(this.diagramServer.clientId, createEdge) })
    }


}

