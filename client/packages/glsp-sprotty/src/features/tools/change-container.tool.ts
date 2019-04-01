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
import { Bounds } from "sprotty/lib";
import { ChangeContainerOperationAction } from "../operation/operation-actions";
import { CursorCSS } from "../tool-feedback/cursor-feedback";
import { DragAwareMouseListener } from "./drag-aware-mouse-listener";
import { GLSP_TYPES } from "../../types";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import { MouseTool } from "sprotty/lib";
import { NodeEditConfig } from "../../base/edit-config/edit-config";
import { Point } from "sprotty/lib";
import { SChildElement } from "sprotty/lib";
import { SModelElement } from "sprotty/lib";
import { SNode } from "sprotty/lib";
import { Tool } from "sprotty/lib";

import { findParentByFeature } from "sprotty/lib";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isBoundsAware } from "sprotty/lib";
import { isConfigurableNode } from "../../base/edit-config/edit-config";
import { reparentAllowed } from "../../base/edit-config/edit-config";


@injectable()
export class ChangeContainerTool implements Tool {
    static ID = "glsp.change-container-tool";
    readonly id = ChangeContainerTool.ID;
    protected changeContainerListener: ChangeContainerListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher) { }

    enable() {
        this.changeContainerListener = new ChangeContainerListener(this);
        this.mouseTool.register(this.changeContainerListener);
    }

    disable() {
        this.mouseTool.deregister(this.changeContainerListener);
        this.feedbackDispatcher.deregisterFeedback(this, [new ApplyCursorCSSFeedbackAction()])
    }

    dispatchFeedback(actions: Action[]) {
        this.feedbackDispatcher.registerFeedback(this, actions);
    }
}

class ChangeContainerListener extends DragAwareMouseListener {
    private container?: SNode & NodeEditConfig;
    private child?: SChildElement;
    private parentBounds?: Bounds;

    constructor(protected tool: ChangeContainerTool) { super() }
    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        this.isMouseDown = true;
        const currentChild = findParentByFeature(target, isConfigurableNode);
        if (currentChild && currentChild.reparentable && isBoundsAware(currentChild.parent)) {
            this.child = currentChild;
            this.parentBounds = currentChild.parent.bounds;
        }
        return [];
    }

    draggingMouseUp(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        if (this.child && this.container && this.container !== this.child.parent) {
            if (reparentAllowed) {
                const location: Point = { x: event.x, y: event.y };
                result.push(new ChangeContainerOperationAction(this.child.id, this.container.id, location))

            }
        }
        this.reset();
        return result;
    }
    mouseOver(target: SModelElement, event: MouseEvent): Action[] {
        if (this.child && !this.inBounds(event)) {
            const currentContainer = findParentByFeature(target, isConfigurableNode);
            if (!this.container || currentContainer !== this.container) {
                this.container = currentContainer;
                const feedback = this.reparentAllowed()
                    ? new ApplyCursorCSSFeedbackAction(CursorCSS.CHANGE_CONTAINER) :
                    new ApplyCursorCSSFeedbackAction(CursorCSS.OPERATION_NOT_ALLOWED);
                this.tool.dispatchFeedback([feedback]);
            }
        }
        return [];
    }
    private inBounds(event: MouseEvent): boolean {
        if (this.parentBounds) {
            return this.parentBounds.x <= event.x && event.x <= this.parentBounds.x + this.parentBounds.width &&
                this.parentBounds.y <= event.y && event.y <= this.parentBounds.y + this.parentBounds.height;
        }
        return true;

    }
    private reset() {
        this.child = undefined;
        this.container = undefined;
        this.tool.dispatchFeedback([new ApplyCursorCSSFeedbackAction()])
    }

    private reparentAllowed(): boolean {
        if (this.child) {
            return this.container ? this.container.isContainableElement(this.child) : false;
        }
        return false;
    }
}

