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
import { FrontendApplicationContribution, OpenHandler, WidgetFactory } from "@theia/core/lib/browser";
import { GLSPClientContribution } from "glsp-theia-extension/lib/browser";
import { ContainerModule, interfaces } from "inversify";
import { DiagramConfiguration, DiagramManager, DiagramManagerProvider } from "sprotty-theia/lib";
import { WorkflowDiagramConfiguration } from "./diagram/workflow-diagram-configuration";
import { WorkflowDiagramManager } from "./diagram/workflow-diagram-manager";
import { WorkflowGLSPDiagramClient } from "./diagram/workflow-glsp-diagram-client";
import { WorkflowGLSPClientContribution } from "./language/workflow-glsp-client-contribution";


export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    bind(WorkflowGLSPClientContribution).toSelf().inSingletonScope()
    bind(GLSPClientContribution).toService(WorkflowGLSPClientContribution);

    bind(WorkflowGLSPDiagramClient).toSelf().inSingletonScope()

    bind(DiagramConfiguration).to(WorkflowDiagramConfiguration).inSingletonScope()
    bind(WorkflowDiagramManager).toSelf().inSingletonScope()
    bind(FrontendApplicationContribution).toService(WorkflowDiagramManager)
    bind(OpenHandler).toService(WorkflowDiagramManager)
    bind(WidgetFactory).toService(WorkflowDiagramManager);
    bind(DiagramManagerProvider).toProvider<DiagramManager>((context) => {
        return () => {
            return new Promise<DiagramManager>((resolve) => {
                const diagramManager = context.container.get<WorkflowDiagramManager>(WorkflowDiagramManager);
                resolve(diagramManager);
            });
        };
    });
})

