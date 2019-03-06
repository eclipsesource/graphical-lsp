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
import { ApplyCursorCSSFeedbackAction } from "../tool-feedback/cursor-feedback";
import { CursorCSS } from "../tool-feedback/cursor-feedback";
import { DeleteElementOperationAction } from "../operation/operation-actions";
import { DragAwareMouseListener } from "./drag-aware-mouse-listener";
import { EnableDefaultToolsAction } from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import { KeyListener } from "sprotty/lib";
import { KeyTool } from "sprotty/lib";
import { MouseTool } from "sprotty/lib";
import { SModelElement } from "sprotty/lib";
import { SModelRoot } from "sprotty/lib";
import { Tool } from "sprotty/lib";

import { findParent } from "sprotty/lib";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isCtrlOrCmd } from "sprotty/lib";
import { isDeletionAllowed } from "../../utils/smodel-util";
import { isSelectable } from "sprotty/lib";
import { matchesKeystroke } from "sprotty/lib/utils/keyboard";



/**
 * Deletes selected elements when hitting the `Del` key.
 */
@injectable()
export class DelKeyDeleteTool implements Tool {
    static ID = "glsp.delete-keyboard";
    readonly id = DelKeyDeleteTool.ID;

    protected deleteKeyListener: DeleteKeyListener = new DeleteKeyListener();

    constructor(@inject(KeyTool) protected readonly keytool: KeyTool) { }

    enable() {
        this.keytool.register(this.deleteKeyListener);
    }

    disable() {
        this.keytool.deregister(this.deleteKeyListener);
    }
}

@injectable()
export class DeleteKeyListener extends KeyListener {
    keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
        if (matchesKeystroke(event, 'Delete')) {
            const deleteElementIds = Array.from(element.root.index.all().filter(e => isSelectable(e) && e.selected)
                .filter(e => e.id !== e.root.id && isDeletionAllowed(e)).map(e => e.id))
            return [new DeleteElementOperationAction(deleteElementIds)]
        }
        return [];
    }
}

/**
 * Deletes selected elements when clicking on them.
 */
@injectable()
export class MouseDeleteTool implements Tool {

    static ID = "glsp.delete-mouse";
    readonly id = MouseDeleteTool.ID;

    protected deleteToolMouseListener: DeleteToolMouseListener = new DeleteToolMouseListener();

    constructor(@inject(MouseTool) protected readonly mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher) { }

    enable() {
        this.mouseTool.register(this.deleteToolMouseListener);
        this.feedbackDispatcher.registerFeedback(this, [new ApplyCursorCSSFeedbackAction(CursorCSS.ELEMENT_DELETION)])
    }

    disable() {
        this.mouseTool.deregister(this.deleteToolMouseListener);
        this.feedbackDispatcher.registerFeedback(this, [new ApplyCursorCSSFeedbackAction()]);
    }
}

@injectable()
export class DeleteToolMouseListener extends DragAwareMouseListener {
    private toDelete?: SModelElement

    nonDraggingMouseUp(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        if (this.toDelete) {
            result.push(new DeleteElementOperationAction([this.toDelete.id]));
        }
        if (!isCtrlOrCmd(event)) {
            result.push(new EnableDefaultToolsAction());
        }
        return result;
    }

    mouseOver(target: SModelElement, event: MouseEvent): Action[] {
        const currentTarget = findParent(target, e => isDeletionAllowed(e));
        if (this.toDelete !== currentTarget) {
            this.toDelete = currentTarget;
            if (this.toDelete || target instanceof SModelRoot) {
                return [new ApplyCursorCSSFeedbackAction(CursorCSS.ELEMENT_DELETION)]
            }
            return [new ApplyCursorCSSFeedbackAction(CursorCSS.OPERATION_NOT_ALLOWED)]
        }
        return [];
    }
}
