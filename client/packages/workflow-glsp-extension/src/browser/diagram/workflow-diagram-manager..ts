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
import { EditorManager } from "@theia/editor/lib/browser";
import { GLSPDiagramManager, GLSPPaletteContribution, GLSPTheiaSprottyConnector, GraphicalLanguageClientContribution } from "glsp-theia-extension/lib/browser";
import { inject, injectable } from "inversify";
import { DiagramWidgetRegistry, TheiaFileSaver } from "theia-glsp/lib";
import { WorkflowLanguage } from "../../common/workflow-language";
import { WorkflowGLClientContribution } from "../language/workflow-gl-client-contribution";
import { ThemeManager } from "./thememanager";



@injectable()
export class WorkflowDiagramManager extends GLSPDiagramManager {
    readonly diagramType = WorkflowLanguage.DiagramType;
    readonly iconClass = "fa fa-project-diagram";
    readonly label = WorkflowLanguage.Label + " Editor";

    private _diagramConnector: GLSPTheiaSprottyConnector;

    constructor(
        @inject(WorkflowGLClientContribution)
        readonly languageClientContribution: GraphicalLanguageClientContribution,
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