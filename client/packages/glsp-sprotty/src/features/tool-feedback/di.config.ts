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

import { ApplyCursorCSSFeedbackActionCommand } from "./cursor-feedback";
import { ContainerModule } from "inversify";
import { DrawEdgeFeedbackCommand } from "./creation-tool-feedback";
import { DrawFeebackEdgeSourceCommand } from "./reconnect-tool-feedback";
import { FeedbackActionDispatcher } from "./feedback-action-dispatcher";
import { FeedbackEdgeEnd } from "./creation-tool-feedback";
import { FeedbackEdgeEndView } from "./view";
import { GLSP_TYPES } from "../../types";
import { HideChangeBoundsToolResizeFeedbackCommand } from "./change-bounds-tool-feedback";
import { HideEdgeReconnectHandlesFeedbackCommand } from "./reconnect-tool-feedback";
import { LocationDecorator } from "sprotty/lib";
import { MoveCommand } from "sprotty/lib";
import { RemoveEdgeFeedbackCommand } from "./creation-tool-feedback";
import { ShowChangeBoundsToolResizeFeedbackCommand } from "./change-bounds-tool-feedback";
import { ShowEdgeReconnectHandlesFeedbackCommand } from "./reconnect-tool-feedback";
import { SResizeHandle } from "../change-bounds/model";
import { SResizeHandleView } from "./view";
import { TYPES } from "sprotty/lib";

import { configureCommand } from "sprotty/lib";
import { configureView } from "sprotty/lib";

const toolFeedbackModule = new ContainerModule((bind, _unbind, isBound) => {
    bind(GLSP_TYPES.IFeedbackActionDispatcher).to(FeedbackActionDispatcher).inSingletonScope();
    bind(GLSP_TYPES.IModelUpdateObserver).toService(GLSP_TYPES.IFeedbackActionDispatcher)

    // create node and edge tool feedback
    configureCommand({ bind, isBound }, ApplyCursorCSSFeedbackActionCommand);
    configureCommand({ bind, isBound }, DrawEdgeFeedbackCommand);
    configureCommand({ bind, isBound }, RemoveEdgeFeedbackCommand);

    configureView({ bind, isBound }, FeedbackEdgeEnd.TYPE, FeedbackEdgeEndView)
    // move tool feedback: we use sprotties MoveCommand as client-side visual feedback
    configureCommand({ bind, isBound }, MoveCommand);

    // resize tool feedback
    configureCommand({ bind, isBound }, ShowChangeBoundsToolResizeFeedbackCommand);
    configureCommand({ bind, isBound }, HideChangeBoundsToolResizeFeedbackCommand);
    configureView({ bind, isBound }, SResizeHandle.TYPE, SResizeHandleView);

    // reconnect edge tool feedback
    configureCommand({ bind, isBound }, ShowEdgeReconnectHandlesFeedbackCommand);
    configureCommand({ bind, isBound }, HideEdgeReconnectHandlesFeedbackCommand);
    configureCommand({ bind, isBound }, DrawFeebackEdgeSourceCommand);

    bind(TYPES.IVNodeDecorator).to(LocationDecorator);
    bind(TYPES.HiddenVNodeDecorator).to(LocationDecorator);
});

export default toolFeedbackModule;
