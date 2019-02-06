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
import { SelectionService } from "@theia/core";
import { registerDefaultTools, TYPES } from "glsp-sprotty/lib";
import { GLSPTheiaDiagramServer } from 'glsp-theia-extension/lib/browser';
import { Container, inject, injectable } from "inversify";
import "sprotty-theia/css/theia-sprotty.css";
import { DiagramConfiguration, TheiaDiagramServer, TheiaSprottySelectionForwarder } from "sprotty-theia/lib";
import { createWorkflowDiagramContainer } from "workflow-sprotty/lib";
import { WorkflowLanguage } from "../../common/workflow-language";

@injectable()
export class WorkflowDiagramConfiguration implements DiagramConfiguration {
    @inject(SelectionService) protected selectionService: SelectionService
    diagramType: string = WorkflowLanguage.DiagramType

    createContainer(widgetId: string): Container {
        const container = createWorkflowDiagramContainer(widgetId);
        container.bind(TYPES.ModelSource).to(GLSPTheiaDiagramServer)
        container.bind(TheiaDiagramServer).toService(GLSPTheiaDiagramServer);
        // container.rebind(KeyTool).to(TheiaKeyTool).inSingletonScope()
        container.bind(TYPES.IActionHandlerInitializer).to(TheiaSprottySelectionForwarder)
        container.bind(SelectionService).toConstantValue(this.selectionService)
        registerDefaultTools(container);
        return container;
    }

}