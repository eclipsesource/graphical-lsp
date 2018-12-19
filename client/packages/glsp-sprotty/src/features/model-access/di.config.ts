/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/

import { ContainerModule } from "inversify";
import { TYPES } from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { IModelAccess, ModelProvidingCommandStack } from "./model-access";

const modelAccessModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    rebind(TYPES.ICommandStack).to(ModelProvidingCommandStack).inSingletonScope();
    bind(GLSP_TYPES.IModelAccessProvider).toProvider<IModelAccess>((context) => {
        return () => {
            return new Promise<IModelAccess>((resolve) => {
                resolve(context.container.get<IModelAccess>(TYPES.ICommandStack));
            });
        };
    });
});

export default modelAccessModule;
