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
import { Widget, WidgetManager } from "@theia/core/lib/browser";
import { EditorManager } from "@theia/editor/lib/browser";
import { RequestOperationsAction, RequestTypeHintsAction } from "glsp-sprotty/lib";
import {
    GLSPDiagramManager,
    GLSPDiagramWidget,
    GLSPTheiaDiagramServer,
    GLSPTheiaSprottyConnector
} from "glsp-theia-extension/lib/browser";
import { inject, injectable } from "inversify";
import { DiagramServer, ModelSource, RequestModelAction, TYPES } from "sprotty";
import { DiagramWidgetOptions, TheiaFileSaver } from "sprotty-theia/lib";

import { EcoreLanguage } from "../common/ecore-language";
import { EcoreGLSPDiagramClient } from "./ecore-gslp-diagram-client";

@injectable()
export class EcoreDiagramManager extends GLSPDiagramManager {
    readonly diagramType = EcoreLanguage.DiagramType;
    readonly iconClass = "fa fa-project-diagram";
    readonly label = EcoreLanguage.Label + " Editor";
    readonly fileExtensions = EcoreLanguage.FileExtensions;
    private _diagramConnector: GLSPTheiaSprottyConnector;

    constructor(
        @inject(EcoreGLSPDiagramClient) diagramClient: EcoreGLSPDiagramClient,
        @inject(TheiaFileSaver) fileSaver: TheiaFileSaver,
        @inject(WidgetManager) widgetManager: WidgetManager,
        @inject(EditorManager) editorManager: EditorManager) {
        super();
        this._diagramConnector = new GLSPTheiaSprottyConnector({ diagramClient, fileSaver, editorManager, widgetManager, diagramManager: this });
    }

    async createWidget(options?: any): Promise<Widget> {
        if (DiagramWidgetOptions.is(options)) {
            const clientId = this.createClientId();
            const config = this.diagramConfigurationRegistry.get(options.diagramType);
            const diContainer = config.createContainer(clientId + '_sprotty');
            const diagramWidget = new EcoreGLSPDiagramWidget(options, clientId, diContainer, this.editorPreferences, this.diagramConnector);
            return diagramWidget;
        }
        throw Error('DiagramWidgetFactory needs DiagramWidgetOptions but got ' + JSON.stringify(options));
    }

    get diagramConnector() {
        return this._diagramConnector;
    }

}

class EcoreGLSPDiagramWidget extends GLSPDiagramWidget {
    protected initializeSprotty() {
        const modelSource = this.diContainer.get<ModelSource>(TYPES.ModelSource);
        if (modelSource instanceof DiagramServer)
            modelSource.clientId = this.id;
        if (modelSource instanceof GLSPTheiaDiagramServer && this.connector)
            this.connector.connect(modelSource);
        this.disposed.connect(() => {
            if (modelSource instanceof GLSPTheiaDiagramServer && this.connector)
                this.connector.disconnect(modelSource);
        });
        this.actionDispatcher.dispatch(new RequestModelAction({
            sourceUri: this.options.uri,
            diagramType: this.options.diagramType,
            needsClientLayout: 'true'
        }));

        this.actionDispatcher.dispatch(new RequestOperationsAction());
        this.actionDispatcher.dispatch(new RequestTypeHintsAction());
    }
}

