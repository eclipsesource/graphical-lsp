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
import { ActionMessage } from "glsp-sprotty/lib";
import { DiagramManager } from "sprotty-theia/lib";
import { DiagramWidget } from "sprotty-theia/lib";
import { EditorManager } from "@theia/editor/lib/browser";
import { ExportSvgAction } from "glsp-sprotty/lib";
import { GLSPClient } from "../language/glsp-client-services";
import { GLSPDiagramClient } from "./glsp-diagram-client";
import { ServerStatusAction } from "glsp-sprotty/lib";
import { TheiaDiagramServer } from "sprotty-theia/lib";
import { TheiaFileSaver } from "sprotty-theia/lib";
import { TheiaSprottyConnector } from "sprotty-theia/lib";
import { WidgetManager } from "@theia/core/lib/browser";

export interface GLSPTheiaSprottyConnectorServices {
    readonly diagramClient: GLSPDiagramClient,
    readonly fileSaver: TheiaFileSaver,
    readonly editorManager: EditorManager,
    readonly widgetManager: WidgetManager,
    readonly diagramManager: DiagramManager
}

export class GLSPTheiaSprottyConnector implements TheiaSprottyConnector, GLSPTheiaSprottyConnectorServices {
    private servers: Map<String, TheiaDiagramServer> = new Map;

    readonly diagramClient: GLSPDiagramClient;
    readonly fileSaver: TheiaFileSaver;
    readonly editorManager: EditorManager;
    readonly widgetManager: WidgetManager;
    readonly diagramManager: DiagramManager;

    constructor(services: GLSPTheiaSprottyConnectorServices) {
        Object.assign(this, services);
        this.diagramClient.connect(this);
    }

    connect(diagramServer: TheiaDiagramServer) {
        this.servers.set(diagramServer.clientId, diagramServer);
        diagramServer.connect(this);
    }

    disconnect(diagramServer: TheiaDiagramServer) {
        this.servers.delete(diagramServer.clientId);
        diagramServer.disconnect();
        this.diagramClient.didClose(diagramServer.clientId);
    }

    save(uri: string, action: ExportSvgAction): void {
        this.fileSaver.save(uri, action);
    }

    showStatus(widgetId: string, status: ServerStatusAction): void {
        const widget = this.widgetManager.getWidgets(this.diagramManager.id).find(w => w.id === widgetId);
        if (widget instanceof DiagramWidget)
            widget.setStatus(status);
    }

    sendMessage(message: ActionMessage) {
        this.diagramClient.sendThroughLsp(message);
    }


    getGLSPClient(): Promise<GLSPClient> {
        return this.diagramClient.glspClient;
    }

    onMessageReceived(message: ActionMessage): void {
        const diagramServer = this.servers.get(message.clientId);
        if (diagramServer) {
            diagramServer.messageReceived(message);
        }
    }
}
