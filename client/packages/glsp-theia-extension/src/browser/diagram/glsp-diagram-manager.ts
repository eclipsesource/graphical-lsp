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
import { EditorPreferences } from "@theia/editor/lib/browser";
import { inject, injectable, Container } from "inversify";
import { DiagramManagerImpl, DiagramWidgetFactory } from "theia-glsp/lib";
import { GLSPDiagramWidget } from "./glsp-diagram-widget";
import { OP_TYPES, OperationService } from "glsp-sprotty/lib";

@injectable()
export abstract class GLSPDiagramManager extends DiagramManagerImpl {

    @inject(EditorPreferences)
    protected readonly editorPreferences: EditorPreferences;

    @inject(OP_TYPES.OperationService) protected operationService: OperationService
    protected get diagramWidgetFactory(): DiagramWidgetFactory {
        return options => new GLSPDiagramWidget(options, this.editorPreferences);
    }


    protected createDiagramContainer(containerId: string): Container {
        const diContainer = super.createDiagramContainer(containerId);
        if (diContainer.isBound(OP_TYPES.OperationService)) {
            diContainer.rebind<OperationService>(OP_TYPES.OperationService).toConstantValue(this.operationService)
        }
        return diContainer

    }
}