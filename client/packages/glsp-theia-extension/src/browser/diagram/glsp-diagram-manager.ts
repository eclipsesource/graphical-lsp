/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { EditorPreferences } from "@theia/editor/lib/browser";
import { inject, injectable } from "inversify";
import { DiagramManagerImpl, DiagramWidgetFactory } from "theia-glsp/lib";
import { GLSPDiagramWidget } from "./glsp-diagram-widget";

@injectable()
export abstract class GLSPDiagramManager extends DiagramManagerImpl {

    @inject(EditorPreferences)
    protected readonly editorPreferences: EditorPreferences;

    protected get diagramWidgetFactory(): DiagramWidgetFactory {
        return options => new GLSPDiagramWidget(options, this.editorPreferences);
    }

}