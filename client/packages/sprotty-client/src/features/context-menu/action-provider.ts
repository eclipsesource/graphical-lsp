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
import { inject, injectable } from "inversify";
import { Action, LabeledAction, Point, SModelElement } from "sprotty/lib";

import { GLSP_TYPES } from "../../types";
import { isSelected } from "../../utils/smodel-util";
import { isSetContextActionsAction, RequestContextActions } from "../context-actions/action-definitions";
import { RequestResponseSupport } from "../request-response/support";
import { IContextMenuItemProvider } from "./menu-providers";

export namespace ServerContextMenu {
    export const KEY = "context-menu";
}

@injectable()
export class ServerContextMenuItemProvider implements IContextMenuItemProvider {

    constructor(@inject(GLSP_TYPES.RequestResponseSupport) protected requestResponseSupport: RequestResponseSupport) { }

    getItems(root: Readonly<SModelElement>, lastMousePosition?: Point): Promise<LabeledAction[]> {
        const selectedElementIds = Array.from(root.index.all().filter(isSelected).map(e => e.id));
        const requestAction = new RequestContextActions(selectedElementIds, lastMousePosition, [ServerContextMenu.KEY]);
        const responseHandler = this.getPaletteActionsFromResponse;
        return this.requestResponseSupport.dispatchRequest(requestAction, responseHandler);
    }

    getPaletteActionsFromResponse(action: Action): LabeledAction[] {
        if (isSetContextActionsAction(action)) {
            return action.actions;
        }
        return [];
    }
}
