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
import { LocationDecorator, MoveCommand, TYPES } from "sprotty/lib";
import {
    FeedbackEdgeEnd, HideEdgeCreationToolFeedbackCommand, HideNodeCreationToolFeedbackCommand, //
    ShowEdgeCreationSelectSourceFeedbackCommand, ShowEdgeCreationSelectTargetFeedbackCommand, //
    ShowNodeCreationToolFeedbackCommand
} from "./creation-tool-feedback";
import { FeedbackEdgeEndView } from "./view";

const toolFeedbackModule = new ContainerModule(bind => {
    // create node and edge tool feedback
    bind(TYPES.ICommand).toConstructor(ShowNodeCreationToolFeedbackCommand);
    bind(TYPES.ICommand).toConstructor(HideNodeCreationToolFeedbackCommand);
    bind(TYPES.ICommand).toConstructor(ShowEdgeCreationSelectSourceFeedbackCommand);
    bind(TYPES.ICommand).toConstructor(ShowEdgeCreationSelectTargetFeedbackCommand);
    bind(TYPES.ICommand).toConstructor(HideEdgeCreationToolFeedbackCommand);
    bind(TYPES.ViewRegistration).toConstantValue({ type: FeedbackEdgeEnd.TYPE, constr: () => new FeedbackEdgeEndView() });

    // move tool feedback: we use sprotties MoveCommand as client-side visual feedback
    bind(TYPES.ICommand).toConstructor(MoveCommand);
    bind(TYPES.IVNodeDecorator).to(LocationDecorator);
    bind(TYPES.HiddenVNodeDecorator).to(LocationDecorator);
});

export default toolFeedbackModule;
