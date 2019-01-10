/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Tobias Ortmayr- initial API and implementation
 ******************************************************************************/
import { interfaces } from "inversify";
import { GLSP_TYPES } from "../../types";
import { ToolManager } from "../tool-manager/tool-manager";
import { ChangeBoundsTool } from "./change-bounds-tool";
import { DelKeyDeleteTool, MouseDeleteTool } from "./delete-tool";

export function registerDefaultTools(container: interfaces.Container) {
    const toolManager: ToolManager = container.get(GLSP_TYPES.ToolManager);
    toolManager.registerStandardTools(
        container.resolve(ChangeBoundsTool),
        container.resolve(DelKeyDeleteTool));
    toolManager.registerTools(container.resolve(MouseDeleteTool));
    toolManager.enableStandardTools();
}
