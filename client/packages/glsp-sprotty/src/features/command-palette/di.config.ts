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
import { CommandPaletteActionProviderRegistry, ICommandPaletteActionProvider, NavigationCommandPaletteActionProvider } from "./action-provider";
import { CommandPalette, CommandPaletteActionHandlerInitializer, CommandPaletteKeyListener } from "./command-palette";

const commandPaletteModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    bind(CommandPalette).toSelf().inSingletonScope();
    bind(TYPES.IActionHandlerInitializer).to(CommandPaletteActionHandlerInitializer);
    bind(TYPES.KeyListener).to(CommandPaletteKeyListener);
    bind(CommandPaletteActionProviderRegistry).toSelf().inSingletonScope();
    bind(GLSP_TYPES.ICommandPaletteActionProviderRegistry).toProvider<ICommandPaletteActionProvider>((context) => {
        return () => {
            return new Promise<ICommandPaletteActionProvider>((resolve) => {
                resolve(context.container.get<ICommandPaletteActionProvider>(CommandPaletteActionProviderRegistry));
            });
        };
    });
    bind(GLSP_TYPES.ICommandPaletteActionProvider).to(NavigationCommandPaletteActionProvider);
});

export default commandPaletteModule;
