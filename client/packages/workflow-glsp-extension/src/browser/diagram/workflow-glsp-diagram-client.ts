/*******************************************************************************
 * Copyright (c) 2019 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { EditorManager } from "@theia/editor/lib/browser";
import { GLSPDiagramClient } from "glsp-theia-extension/lib/browser";
import { inject, injectable } from "inversify";
import { WorkflowGLSPClientContribution } from "../language/workflow-glsp-client-contribution";

@injectable()
export class WorkflowGLSPDiagramClient extends GLSPDiagramClient {
    constructor(
        @inject(WorkflowGLSPClientContribution) glspCLientContribution: WorkflowGLSPClientContribution,
        @inject(EditorManager) editorManager: EditorManager) {
        super(glspCLientContribution, editorManager)
    }
}