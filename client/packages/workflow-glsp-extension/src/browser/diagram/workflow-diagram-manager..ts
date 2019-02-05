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
import { EditorManager } from "@theia/editor/lib/browser";
import { GLSPClientContribution, GLSPDiagramManager, GLSPTheiaSprottyConnector } from "glsp-theia-extension/lib/browser";
import { inject, injectable } from "inversify";
import { DiagramWidgetRegistry, TheiaFileSaver } from "theia-glsp/lib";
import { WorkflowLanguage } from "../../common/workflow-language";
import { WorkflowGLSPClientContribution } from "../language/workflow-glsp-client-contribution";

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
        readonly diagramWidgetRegistry: DiagramWidgetRegistry) {
        super();

    }

    get diagramConnector() {
        if (!this._diagramConnector) {
            this._diagramConnector = new GLSPTheiaSprottyConnector(
                this.languageClientContribution,
                this.theiaFileSaver,
                this.editorManager,
                this.diagramWidgetRegistry)
        }
        return this._diagramConnector
    }

    get fileExtensions() {
        return [WorkflowLanguage.FileExtension]
    }
}