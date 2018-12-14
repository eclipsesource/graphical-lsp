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
import {
    HideEdgeCreationToolFeedbackCommand, HideNodeCreationToolFeedbackCommand, //
    ShowEdgeCreationSelectSourceFeedbackCommand, ShowEdgeCreationSelectTargetFeedbackCommand, //
    ShowNodeCreationToolFeedbackCommand
} from "./creation-tool-feedback";

const toolFeedbackModule = new ContainerModule(bind => {
    bind(TYPES.ICommand).toConstructor(ShowNodeCreationToolFeedbackCommand);
    bind(TYPES.ICommand).toConstructor(HideNodeCreationToolFeedbackCommand);
    bind(TYPES.ICommand).toConstructor(ShowEdgeCreationSelectSourceFeedbackCommand);
    bind(TYPES.ICommand).toConstructor(ShowEdgeCreationSelectTargetFeedbackCommand);
    bind(TYPES.ICommand).toConstructor(HideEdgeCreationToolFeedbackCommand);
});

export default toolFeedbackModule;
