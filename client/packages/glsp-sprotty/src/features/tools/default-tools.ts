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
