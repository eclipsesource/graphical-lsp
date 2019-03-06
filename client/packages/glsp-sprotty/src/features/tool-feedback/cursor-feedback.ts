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
import { Action } from "sprotty/lib";
import { CommandExecutionContext } from "sprotty/lib";
import { FeedbackCommand } from "./model";
import { SModelRoot } from "sprotty/lib";
import { TYPES } from "sprotty/lib";

import { applyCssClassesToRoot } from "./model";
import { inject } from "inversify";
import { injectable } from "inversify";
import { unapplyCssClassesToRoot } from "./model";

export enum CursorCSS {
    NODE_CREATION = 'node-creation-mode',
    EDGE_CREATION_SOURCE = 'edge-creation-select-source-mode',
    EDGE_CREATION_TARGET = 'edge-creation-select-target-mode',
    EDGE_RECONNECT = 'edge-reconnect-select-target-mode',
    OPERATION_NOT_ALLOWED = 'edge-modification-not-allowed-mode',
    ELEMENT_DELETION = "element-deletion-mode"
}

export class ApplyCursorCSSFeedbackAction implements Action {
    kind = ApplyCursorCSSFeedbackActionCommand.KIND
    constructor(readonly cssClass?: CursorCSS) { }
}

@injectable()
export class ApplyCursorCSSFeedbackActionCommand extends FeedbackCommand {
    static readonly KIND = 'applyCursorCssFeedback';

    constructor(@inject(TYPES.Action) readonly action: ApplyCursorCSSFeedbackAction) {
        super()
    }
    execute(context: CommandExecutionContext): SModelRoot {
        let result = unapplyCssClassesToRoot(context, Object.values(CursorCSS))
        if (this.action.cssClass) {
            result = applyCssClassesToRoot(context, [this.action.cssClass])
        }
        return result;
    }
}