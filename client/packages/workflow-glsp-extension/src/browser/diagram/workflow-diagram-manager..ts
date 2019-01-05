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
import { EditorManager } from "@theia/editor/lib/browser";
import { GLSPClientContribution, GLSPDiagramManager, GLSPPaletteContribution, GLSPTheiaSprottyConnector } from "glsp-theia-extension/lib/browser";
import { inject, injectable } from "inversify";
import { DiagramWidgetRegistry, TheiaFileSaver } from "theia-glsp/lib";
import { WorkflowLanguage } from "../../common/workflow-language";
import { WorkflowGLSPClientContribution } from "../language/workflow-glsp-client-contribution";
import { ThemeManager } from "./thememanager";

@injectable()
export class WorkflowDiagramManager extends GLSPDiagramManager {
    readonly diagramType = WorkflowLanguage.DiagramType;
    readonly iconClass = "fa fa-project-diagram";
    readonly label = WorkflowLanguage.Label + " Editor";

    private _diagramConnector: GLSPTheiaSprottyConnector;

    constructor(
        @inject(WorkflowGLSPClientContribution)
        readonly languageClientContribution: GLSPClientContribution,
        @inject(TheiaFileSaver)
        readonly theiaFileSaver: TheiaFileSaver,
        @inject(EditorManager)
        readonly editorManager: EditorManager,
        @inject(DiagramWidgetRegistry)
        readonly diagramWidgetRegistry: DiagramWidgetRegistry,
        @inject(ThemeManager)
        readonly themeManager: ThemeManager,
        @inject(GLSPPaletteContribution) readonly paletteContribution: GLSPPaletteContribution) {
        super();

    }

    get diagramConnector() {
        if (!this._diagramConnector) {
            this._diagramConnector = new GLSPTheiaSprottyConnector(
                this.languageClientContribution,
                this.theiaFileSaver,
                this.editorManager,
                this.diagramWidgetRegistry,
                this.paletteContribution)
            this.themeManager.initialize();

        }
        return this._diagramConnector
    }
}