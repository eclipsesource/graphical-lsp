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
import "../../css/glsp-sprotty.css";

import { ContainerModule } from "inversify";
import { configureCommand, Tool, TYPES } from "sprotty/lib";

import { GLSP_TYPES } from "../types";
import { FeedbackAwareUpdateModelCommand, SetModelActionHandler } from "./model/update-model-command";
import { createToolFactory, ToolManagerActionHandler } from "./tool-manager/tool-manager-action-handler";
import { defaultGLSPViewerOptions, GLSPViewerOptions } from "./views/viewer-options";

const defaultGLSPModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    // Tool manager initialization ------------------------------------
    bind(TYPES.IActionHandlerInitializer).to(ToolManagerActionHandler);
    bind(GLSP_TYPES.IToolFactory).toFactory<Tool>((createToolFactory()));

    // Model update initialization ------------------------------------
    configureCommand({ bind, isBound }, FeedbackAwareUpdateModelCommand);
    bind(TYPES.IActionHandlerInitializer).to(SetModelActionHandler);


    bind<GLSPViewerOptions>(GLSP_TYPES.ViewerOptions).toConstantValue(defaultGLSPViewerOptions());
    if (isBound(TYPES.ViewerOptions)) {
        rebind(TYPES.ViewerOptions).toService(GLSP_TYPES.ViewerOptions);
    } else {
        bind(TYPES.ViewerOptions).toService(GLSP_TYPES.ViewerOptions);
    }
});

export default defaultGLSPModule;
