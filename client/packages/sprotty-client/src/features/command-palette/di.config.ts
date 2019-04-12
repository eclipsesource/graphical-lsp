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
import "../../../css/command-palette.css";

import { ContainerModule } from "inversify";
import { TYPES } from "sprotty/lib";

import { GLSP_TYPES } from "../../types";
import {
    CommandPaletteActionProviderRegistry,
    ICommandPaletteActionProvider,
    NavigationCommandPaletteActionProvider,
    ServerCommandPaletteActionProvider
} from "./action-provider";
import { CommandPalette, CommandPaletteKeyListener } from "./command-palette";




const commandPaletteModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    bind(CommandPalette).toSelf().inSingletonScope();
    bind(GLSP_TYPES.IDiagramUIExtension).toService(CommandPalette);
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
    bind(ServerCommandPaletteActionProvider).toSelf().inSingletonScope();
    bind(GLSP_TYPES.ICommandPaletteActionProvider).to(ServerCommandPaletteActionProvider);
});

export default commandPaletteModule;
