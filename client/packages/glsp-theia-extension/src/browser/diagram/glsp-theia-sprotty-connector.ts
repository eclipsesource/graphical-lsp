/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import URI from "@theia/core/lib/common/uri";
import { EditorManager } from "@theia/editor/lib/browser";
import { Workspace } from "@theia/languages/lib/browser";
import { ActionMessage, ExportSvgAction, ServerStatusAction } from "glsp-sprotty/lib";
import { DiagramWidgetRegistry, OpenInTextEditorMessage, TheiaDiagramServer, TheiaFileSaver, TheiaSprottyConnector } from "theia-glsp/lib";
import { ActionMessageNotification } from "../../common/";
import { GLSPClientContribution } from "../language/glsp-client-contribution";
import { GLSPPaletteContribution } from "./glsp-palette-contribution";
import { GLSPTheiaDiagramServer } from "./glsp-theia-diagram-server";

export class GLSPTheiaSprottyConnector implements TheiaSprottyConnector {

    private servers: TheiaDiagramServer[] = []

    constructor(private glspClientContribution: GLSPClientContribution,
        private fileSaver: TheiaFileSaver,
        private editorManager: EditorManager,
        private diagramWidgetRegistry: DiagramWidgetRegistry,
        private paletteContribution: GLSPPaletteContribution,
        readonly workspace?: Workspace) {

        this.glspClientContribution.languageClient.then(
            lc => {
                lc.onNotification(ActionMessageNotification.type, this.onMessageReceived.bind(this))
            }
        ).catch(
            err => console.error(err)
        )
    }

    connect(diagramServer: TheiaDiagramServer): void {
        this.paletteContribution.register(diagramServer as GLSPTheiaDiagramServer);
        this.servers.push(diagramServer)
        diagramServer.connect(this)
    }

    disconnect(diagramServer: TheiaDiagramServer): void {
        const index = this.servers.indexOf(diagramServer)
        if (index >= 0)
            this.servers.splice(index, 0)
        diagramServer.disconnect()
        this.glspClientContribution.languageClient.then(lc => lc.stop())
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
        this.glspClientContribution.languageClient.then(lc => lc.sendNotification(ActionMessageNotification.type, message))
    }

    onMessageReceived(message: ActionMessage): void {
        this.servers.forEach(element => {
            element.messageReceived(message)
        })
    }

}