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
import { KeyTool, TYPES } from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { ExtendedKeyTool } from "./key-tool";
import { DefaultToolManager, StandardToolsEnablingKeyListener, ToolManagerActionHandlerInitializer } from "./tool-manager";

const toolManagerModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    bind(GLSP_TYPES.ToolManager).to(DefaultToolManager).inSingletonScope();
    bind(TYPES.KeyListener).to(StandardToolsEnablingKeyListener);
    bind(TYPES.IActionHandlerInitializer).to(ToolManagerActionHandlerInitializer);
    bind(ExtendedKeyTool).toSelf().inSingletonScope();
    rebind(KeyTool).to(ExtendedKeyTool).inSingletonScope();
});

export default toolManagerModule;
