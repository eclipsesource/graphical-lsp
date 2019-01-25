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
import { injectable } from "inversify";
import { Action, Command, CommandExecutionContext, CommandResult, SModelElement, SModelRoot, SParentElement } from "sprotty/lib";
import { isNotUndefined } from "../../utils/smodel-util";
import { isResizeable, ResizeHandleLocation, SResizeHandle } from "./model";

export class SwitchResizeModeAction implements Action {
    kind = SwitchResizeModeCommand.KIND;

    constructor(
        public readonly elementsToActivate: string[] = [],
        public readonly elementsToDeactivate: string[] = []) {
    }
}

@injectable()
export class SwitchResizeModeCommand extends Command {
    static KIND: string = "switchResizeMode";

    protected elementsToActivate: SModelElement[] = [];
    protected elementsToDeactivate: SModelElement[] = [];

    constructor(public action: SwitchResizeModeAction) {
        super();
    }

    execute(context: CommandExecutionContext): SModelRoot {
        const index = context.root.index;
        this.action.elementsToActivate.map(id => index.getById(id)).filter(isNotUndefined).forEach(e => this.elementsToActivate.push(e));
        this.action.elementsToDeactivate.map(id => index.getById(id)).filter(isNotUndefined).forEach(e => this.elementsToDeactivate.push(e));
        return this.doExecute(context);
    }

    protected doExecute(context: CommandExecutionContext): SModelRoot {
        this.elementsToDeactivate.filter(isResizeable).forEach(removeResizeHandles);
        this.elementsToActivate.filter(isResizeable).forEach(addResizeHandles);
        return context.root;
    }

    undo(context: CommandExecutionContext): CommandResult {
        throw new Error("Method not implemented.");
    }

    redo(context: CommandExecutionContext): CommandResult {
        return this.doExecute(context);
    }
}

export function addResizeHandles(element: SParentElement) {
    removeResizeHandles(element);
    element.add(new SResizeHandle(ResizeHandleLocation.TopLeft));
    element.add(new SResizeHandle(ResizeHandleLocation.TopRight));
    element.add(new SResizeHandle(ResizeHandleLocation.BottomLeft));
    element.add(new SResizeHandle(ResizeHandleLocation.BottomRight));
}

export function removeResizeHandles(element: SParentElement) {
    element.removeAll(child => child instanceof SResizeHandle);
}