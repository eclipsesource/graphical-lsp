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

import { ContainerModule } from "inversify";
import { LocationDecorator, MoveCommand, TYPES } from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import {
    FeedbackEdgeEnd, HideEdgeCreationToolFeedbackCommand, HideNodeCreationToolFeedbackCommand, //
    ShowEdgeCreationSelectSourceFeedbackCommand, ShowEdgeCreationSelectTargetFeedbackCommand, //
    ShowNodeCreationToolFeedbackCommand
} from "./creation-tool-feedback";
import { FeedbackActionDispatcher } from "./feedback-action-dispatcher";
import { FeedbackEdgeEndView } from "./view";

const toolFeedbackModule = new ContainerModule(bind => {
    bind(GLSP_TYPES.IFeedbackActionDispatcher).to(FeedbackActionDispatcher).inSingletonScope();

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
