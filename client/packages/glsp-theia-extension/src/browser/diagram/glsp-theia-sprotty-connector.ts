/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { TheiaSprottyConnector, TheiaDiagramServer, OpenInTextEditorMessage, TheiaFileSaver, DiagramWidgetRegistry } from "theia-sprotty/lib";
import { ExportSvgAction, ServerStatusAction, ActionMessage, Tool, SetToolsAction, Action, ExecuteNodeCreationToolAction, ExecuteToolAction, ToolType, SetToolsCommand } from "glsp-sprotty/lib";
import { GraphicalLanguageClientContribution } from "../language/graphical-langauge-client-contribution";
import { EditorManager, EDITOR_CONTEXT_MENU } from "@theia/editor/lib/browser";
import URI from "@theia/core/lib/common/uri";
import { ActionMessageNotification } from "../../common/";
import { MenuModelRegistry, Command, CommandRegistry, SelectionService } from "@theia/core";
import { UriCommandHandler, UriAwareCommandHandler } from "@theia/core/lib/common/uri-command-handler";

export class GLSPTheiaSprottyConnector implements TheiaSprottyConnector {

    private servers: TheiaDiagramServer[] = []

    constructor(private graphicalLanguageClientContribution: GraphicalLanguageClientContribution,
        private fileSaver: TheiaFileSaver,
        private editorManager: EditorManager,
        private diagramWidgetRegistry: DiagramWidgetRegistry,
        private menuModelRegistry: MenuModelRegistry,
        private commandRegistry: CommandRegistry,
        private selectionService: SelectionService) {

        this.graphicalLanguageClientContribution.languageClient.then(
            lc => {
                lc.onNotification(ActionMessageNotification.type, this.onMessageReceived.bind(this))
            }
        ).catch(
            err => console.error(err)
        )
    }

    connect(diagramServer: TheiaDiagramServer): void {
        this.servers.push(diagramServer)
        diagramServer.connect(this)
    }
    disconnect(diagramServer: TheiaDiagramServer): void {
        const index = this.servers.indexOf(diagramServer)
        if (index >= 0)
            this.servers.splice(index, 0)
        diagramServer.disconnect()
        this.graphicalLanguageClientContribution.languageClient.then(lc => lc.stop())
    }
    save(uri: string, action: ExportSvgAction): void {
        this.fileSaver.save(uri, action)
    }

    openInTextEditor(message: OpenInTextEditorMessage): void {
        const uri = new URI(message.location.uri)
        if (!message.forceOpen) {
            this.editorManager.all.forEach(editorWidget => {
                const currentTextEditor = editorWidget.editor
                if (editorWidget.isVisible && uri.toString() === currentTextEditor.uri.toString()) {
                    currentTextEditor.cursor = message.location.range.start
                    currentTextEditor.revealRange(message.location.range)
                    currentTextEditor.selection = message.location.range
                }
            })
        } else {
            this.editorManager.open(uri).then(
                editorWidget => {
                    const editor = editorWidget.editor
                    editor.cursor = message.location.range.start
                    editor.revealRange(message.location.range)
                    editor.selection = message.location.range
                })
        }
    }
    showStatus(widgetId: string, status: ServerStatusAction): void {
        const widget = this.diagramWidgetRegistry.getWidgetById(widgetId)
        if (widget)
            widget.setStatus(status)
    }
    sendMessage(message: ActionMessage): void {
        this.graphicalLanguageClientContribution.languageClient.then(lc => lc.sendNotification(ActionMessageNotification.type, message))
    }
    onMessageReceived(message: ActionMessage): void {
        this.servers.forEach(element => {
            element.messageReceived(message)
        })
        if (message.action.kind === SetToolsCommand.KIND) {
            this.handleSetToolsAction(message)
        }

    }



    handleSetToolsAction(message: ActionMessage) {
        (message.action as SetToolsAction).tools.forEach(tool => {
            this.createToolCommand(tool, message.clientId)
        });
    }

    private createToolCommand(tool: Tool, clientId: string) {
        const command: Command = {
            id: tool.id,
            label: tool.name
        }

        this.commandRegistry.registerCommand(command, {
            execute: async () => {
                let message = { clientId: clientId, action: this.createdAction(tool) }
                this.sendMessage(message)
            }

        })

        this.menuModelRegistry.registerMenuAction([...EDITOR_CONTEXT_MENU, '0_glsp'], {
            commandId: command.id,
            label: command.label
        })
    }

    protected createdAction(tool: Tool): Action {
        let point = { x: 5, y: 5 }
        if (tool.toolType === ToolType.CREATION) {
            return new ExecuteNodeCreationToolAction(tool.id, point, undefined)
        } else if (tool.toolType === ToolType.CONNECTION) {

        }
        return new ExecuteToolAction(tool.id, point, undefined)
    }



}