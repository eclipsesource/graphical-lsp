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

const EDGE_CREATION_SOURCE_CSS_CLASS = 'edge-creation-select-source-mode';
const EDGE_CREATION_TARGET_CSS_CLASS = 'edge-creation-select-target-mode';

export class ShowEdgeCreationSelectSourceFeedbackAction implements Action {
    kind = ShowEdgeCreationSelectSourceFeedbackCommand.KIND;
}

export class ShowEdgeCreationSelectTargetFeedbackAction implements Action {
    kind = ShowEdgeCreationSelectTargetFeedbackCommand.KIND;
}

export class HideEdgeCreationToolFeedbackAction implements Action {
    kind = HideEdgeCreationToolFeedbackCommand.KIND;
}

export class ShowEdgeCreationSelectSourceFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.selectsource.feedback.show';
    execute(context: CommandExecutionContext): SModelRoot {
        unapplyCssClassesToRoot(context, [EDGE_CREATION_TARGET_CSS_CLASS]);
        return applyCssClassesToRoot(context, [EDGE_CREATION_SOURCE_CSS_CLASS]);
    }
}

export class ShowEdgeCreationSelectTargetFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.selecttarget.feedback.show';
    execute(context: CommandExecutionContext): SModelRoot {
        unapplyCssClassesToRoot(context, [EDGE_CREATION_SOURCE_CSS_CLASS]);
        return applyCssClassesToRoot(context, [EDGE_CREATION_TARGET_CSS_CLASS]);
    }
}

export class HideEdgeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.feedback.hide';
    execute(context: CommandExecutionContext): SModelRoot {
        return unapplyCssClassesToRoot(context, [EDGE_CREATION_SOURCE_CSS_CLASS, EDGE_CREATION_TARGET_CSS_CLASS]);
    }
}