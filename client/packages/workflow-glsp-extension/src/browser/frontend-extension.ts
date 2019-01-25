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
import { CommandContribution, MenuContribution } from "@theia/core";
import { FrontendApplicationContribution, OpenHandler } from "@theia/core/lib/browser";
import { GLSPClientContribution, GLSPPaletteContribution } from "glsp-theia-extension/lib/browser";
import { ContainerModule, interfaces } from "inversify";
import { DiagramConfiguration, DiagramManager, DiagramManagerProvider } from "theia-glsp/lib";
import { WorkflowLanguage } from "../common/workflow-language";
import { WorkflowDiagramConfiguration } from "./diagram/di.config";
import { ThemeManager } from "./diagram/thememanager";
import { WorkflowDiagramManager } from "./diagram/workflow-diagram-manager.";
import { WorkflowGLSPClientContribution } from "./language/workflow-glsp-client-contribution";


export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    bind(WorkflowGLSPClientContribution).toSelf().inSingletonScope()
    bind(GLSPClientContribution).toService(WorkflowGLSPClientContribution);

    bind(DiagramConfiguration).to(WorkflowDiagramConfiguration).inSingletonScope()
    bind(DiagramManagerProvider).toProvider<DiagramManager>(context => {
        return () => {
            return new Promise<DiagramManager>((resolve) =>
                resolve(context.container.get(WorkflowDiagramManager))
            )
        }
    }).whenTargetNamed(WorkflowLanguage.DiagramType)

    bind(WorkflowDiagramManager).toSelf().inSingletonScope()
    bind(FrontendApplicationContribution).toService(WorkflowDiagramManager)
    bind(OpenHandler).toService(WorkflowDiagramManager)
    bind(ThemeManager).toSelf().inSingletonScope()
    bind(GLSPPaletteContribution).toSelf().inSingletonScope()
    bind(MenuContribution).toService(GLSPPaletteContribution)
    bind(CommandContribution).toService(GLSPPaletteContribution)
})

