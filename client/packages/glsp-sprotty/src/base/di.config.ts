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
import { ContainerModule } from "inversify";
import { configureCommand, Tool, TYPES } from "sprotty/lib";
import "../../css/glsp-sprotty.css";
import { GLSP_TYPES } from "../types";
import { GLSPCommandStack, IReadonlyModelAccess } from "./command-stack";
import { DiagramUIExtensionActionHandlerInitializer, DiagramUIExtensionRegistry } from "./diagram-ui-extension/diagram-ui-extension-registry";
import { ModelUpdateObserverRegistry, ObserverableUpdateModelCommand } from "./model/model-update-observer-registry";
import { createToolFactory, ToolManagerActionHandler } from "./tool-manager/tool-manager-action-handler";

const defaultGLSPModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    // GLSP Commandstack  initialization ------------------------------------
    if (isBound(TYPES.ICommandStack)) {
        unbind(TYPES.ICommandStack);
    }
    bind(GLSPCommandStack).toSelf().inSingletonScope();
    bind(TYPES.ICommandStack).toService(GLSPCommandStack);
    bind(GLSP_TYPES.IReadonlyModelAccessProvider).toProvider<IReadonlyModelAccess>((context) => {
        return () => {
            return new Promise<IReadonlyModelAccess>((resolve) => {
                resolve(context.container.get<IReadonlyModelAccess>(TYPES.ICommandStack));
            });
        };
    });

    // DiagramUIExtension registry initialization ------------------------------------
    bind(GLSP_TYPES.DiagramUIExtensionRegistry).to(DiagramUIExtensionRegistry).inSingletonScope();
    bind(TYPES.IActionHandlerInitializer).to(DiagramUIExtensionActionHandlerInitializer)

    // Tool manager initialization ------------------------------------

    bind(TYPES.IActionHandlerInitializer).to(ToolManagerActionHandler);
    bind(GLSP_TYPES.IToolFactory).toFactory<Tool>((createToolFactory()));

    // Model update initialization ------------------------------------
    bind(GLSP_TYPES.ModelUpdateObserverRegistry).to(ModelUpdateObserverRegistry).inSingletonScope();
    configureCommand({ bind, isBound }, ObserverableUpdateModelCommand);
})

export default defaultGLSPModule;
