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
import { FrontendApplicationContribution, OpenHandler, WidgetFactory } from "@theia/core/lib/browser";
import { GLSPClientContribution } from "glsp-theia-extension/lib/browser";
import { ContainerModule, interfaces } from "inversify";
import { DiagramConfiguration, DiagramManager, DiagramManagerProvider } from "sprotty-theia/lib";

import { EcoreDiagramConfiguration } from "./ecore-diagram-configuration";
import { EcoreDiagramManager } from "./ecore-diagram-manager.";
import { EcoreGLClientContribution } from "./ecore-glclient-contribution";
import { EcoreGLSPDiagramClient } from "./ecore-gslp-diagram-client";


export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    bind(EcoreGLClientContribution).toSelf().inSingletonScope();
    bind(GLSPClientContribution).toService(EcoreGLClientContribution);

    bind(EcoreGLSPDiagramClient).toSelf().inSingletonScope();

    bind(DiagramConfiguration).to(EcoreDiagramConfiguration).inSingletonScope();
    bind(EcoreDiagramManager).toSelf().inSingletonScope();
    bind(FrontendApplicationContribution).toService(EcoreDiagramManager);
    bind(OpenHandler).toService(EcoreDiagramManager);
    bind(WidgetFactory).toService(EcoreDiagramManager);
    bind(DiagramManagerProvider).toProvider<DiagramManager>((context) => {
        return () => {
            return new Promise<DiagramManager>((resolve) => {
                const diagramManager = context.container.get<EcoreDiagramManager>(EcoreDiagramManager);
                resolve(diagramManager);
            });
        };
    });
});

