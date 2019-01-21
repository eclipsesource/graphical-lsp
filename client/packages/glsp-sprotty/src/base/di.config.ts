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
import { ContainerModule } from "inversify";
import { TYPES } from "sprotty/lib";
import { GLSP_TYPES } from "../types";
import { GLSPCommandStack, IReadonlyModelAccess } from "./command-stack";
import { DiagramUIExtensionRegistry } from "./diagram-ui-extension/diagram-ui-extension-registry";

const defaultGLSPModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    if (isBound(TYPES.ICommandStack)) {
        unbind(TYPES.ICommandStack);
    }
    bind(GLSPCommandStack).toSelf().inSingletonScope();
    bind(TYPES.ICommandStack).toService(GLSPCommandStack);
    bind(GLSP_TYPES.IModelUpdateNotifier).toService(GLSPCommandStack);
    bind(GLSP_TYPES.IReadonlyModelAccessProvider).toProvider<IReadonlyModelAccess>((context) => {
        return () => {
            return new Promise<IReadonlyModelAccess>((resolve) => {
                resolve(context.container.get<IReadonlyModelAccess>(TYPES.ICommandStack));
            });
        };
    });

    bind(DiagramUIExtensionRegistry).toSelf().inSingletonScope();
    bind(TYPES.IActionHandlerInitializer).toService(DiagramUIExtensionRegistry)
})

export default defaultGLSPModule;
