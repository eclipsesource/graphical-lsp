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
import { WidgetManager } from "@theia/core/lib/browser";
import { EditorManager } from "@theia/editor/lib/browser";
import { GLSPDiagramManager, GLSPTheiaSprottyConnector } from "glsp-theia-extension/lib/browser";
import { inject, injectable } from "inversify";
import { TheiaFileSaver } from "sprotty-theia/lib";
import { WorkflowLanguage } from "../../common/workflow-language";
import { WorkflowGLSPDiagramClient } from "./workflow-glsp-diagram-client";

@injectable()
export class WorkflowDiagramManager extends GLSPDiagramManager {
    readonly diagramType = WorkflowLanguage.DiagramType;
    readonly iconClass = "fa fa-project-diagram";
    readonly label = WorkflowLanguage.Label + " Editor";

    private _diagramConnector: GLSPTheiaSprottyConnector;

    constructor(
        @inject(WorkflowGLSPDiagramClient) diagramClient: WorkflowGLSPDiagramClient,
        @inject(TheiaFileSaver) fileSaver: TheiaFileSaver,
        @inject(WidgetManager) widgetManager: WidgetManager,
        @inject(EditorManager) editorManager: EditorManager) {
        super();
        this._diagramConnector = new GLSPTheiaSprottyConnector({ diagramClient, fileSaver, editorManager, widgetManager, diagramManager: this })
    }

    get fileExtensions() {
        return [WorkflowLanguage.FileExtension]
    }
    get diagramConnector() {
        return this._diagramConnector;
    }
}