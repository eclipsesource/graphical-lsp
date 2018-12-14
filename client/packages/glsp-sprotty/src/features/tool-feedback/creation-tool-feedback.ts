/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Philip Langer - initial API and implementation
 ******************************************************************************/

import { Action, CommandExecutionContext, SModelRoot } from "sprotty/lib";
import { applyCssClassesToRoot, FeedbackCommand, unapplyCssClassesToRoot } from "./model";

const NODE_CREATION_CSS_CLASS = 'node-creation-tool-mode';

export class ShowNodeCreationToolFeedbackAction implements Action {
    kind = ShowNodeCreationToolFeedbackCommand.KIND;
}

export class HideNodeCreationToolFeedbackAction implements Action {
    kind = HideNodeCreationToolFeedbackCommand.KIND;
}

export class ShowNodeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.nodecreationtool.feedback.show';
    execute(context: CommandExecutionContext): SModelRoot {
        return applyCssClassesToRoot(context, [NODE_CREATION_CSS_CLASS]);
    }
}

export class HideNodeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.nodecreationtool.feedback.hide';
    execute(context: CommandExecutionContext): SModelRoot {
        return unapplyCssClassesToRoot(context, [NODE_CREATION_CSS_CLASS]);
    }
}