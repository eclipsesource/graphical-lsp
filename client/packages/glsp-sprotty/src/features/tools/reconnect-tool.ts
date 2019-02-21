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
import {
    Action, AnchorComputerRegistry, Connectable, DeleteElementAction, findParentByFeature, isConnectable, MouseTool, //
    SModelElement, SRoutableElement, SRoutingHandle, Tool
} from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { CreateConnectionOperationAction } from "../operation/operation-actions";
import { isReconnectHandle, isRoutable, isSourceRoutingHandle, isTargetRoutingHandle } from "../reconnect/model";
import { SelectionTracker } from "../select/selection-tracker";
import { feedbackEdgeId } from "../tool-feedback/creation-tool-feedback";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import {
    FeedbackEdgeSourceMovingMouseListener, FeedbackEdgeTargetMovingMouseListener, HideEdgeReconnectHandlesFeedbackAction, HideEdgeReconnectToolFeedbackAction, //
    ShowEdgeReconnectHandlesFeedbackAction, ShowEdgeReconnectSelectSourceFeedbackAction, ShowEdgeReconnectSelectTargetFeedbackAction
} from "../tool-feedback/reconnect-tool-feedback";

@injectable()
export class EdgeReconnectTool implements Tool {
    static ID = "glsp.edge-reconnect-tool";
    readonly id = EdgeReconnectTool.ID;

    protected feedbackEdgeSourceMovingListener: FeedbackEdgeSourceMovingMouseListener;
    protected feedbackEdgeTargetMovingListener: FeedbackEdgeTargetMovingMouseListener;
    protected reconnectEdgeListener: ReconnectEdgeListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher,
        @inject(AnchorComputerRegistry) protected anchorRegistry: AnchorComputerRegistry) {
    }

    enable(): void {
        this.reconnectEdgeListener = new ReconnectEdgeListener(this);
        this.mouseTool.register(this.reconnectEdgeListener);

        // install feedback move mouse listener for client-side move updates
        this.feedbackEdgeSourceMovingListener = new FeedbackEdgeSourceMovingMouseListener(anchorRegistry);
        this.feedbackEdgeTargetMovingListener = new FeedbackEdgeTargetMovingMouseListener(anchorRegistry);
        this.mouseTool.register(this.feedbackEdgeSourceMovingListener);
        this.mouseTool.register(this.feedbackEdgeTargetMovingListener);
    }

    disable(): void {
        this.reconnectEdgeListener.reset();
        this.mouseTool.deregister(this.feedbackEdgeSourceMovingListener);
        this.mouseTool.deregister(this.feedbackEdgeTargetMovingListener);
        this.mouseTool.deregister(this.reconnectEdgeListener);
    }

    dispatchFeedback(actions: Action[]) {
        this.feedbackDispatcher.registerFeedback(this, actions);
    }
}

class ReconnectEdgeListener extends SelectionTracker {
    private isMouseDown: boolean;

    // active edge data
    private edgeId?: string;
    private edgeTypeId?: string;
    private edgeSourceId?: string;
    private edgeTargetId?: string;

    // active reconnect handle data
    private reconnectMode?: 'NEW_SOURCE' | 'NEW_TARGET';

    // new connectable (source or target) for edge
    private newConnectable?: SModelElement & Connectable;

    constructor(protected tool: EdgeReconnectTool) {
        super();
    }

    private isValidEdge(edge?: SRoutableElement): edge is SRoutableElement {
        return edge !== undefined && edge.id !== feedbackEdgeId(edge.root);
    }

    private setEdgeSelected(edge: SRoutableElement) {
        if (this.edgeId && this.edgeId !== edge.id) {
            // reset from a previously selected edge
            this.reset();
        }

        this.edgeId = edge.id;
        this.edgeSourceId = edge.sourceId;
        this.edgeTargetId = edge.targetId;
        this.edgeTypeId = edge.type;
        this.tool.dispatchFeedback([new ShowEdgeReconnectHandlesFeedbackAction(this.edgeId)]);
    }

    private isEdgeSelected(): boolean {
        return this.edgeId !== undefined && this.edgeTypeId !== undefined;
    }

    private setReconnectHandleSelected(edge: SRoutableElement, reconnectHandle: SRoutingHandle) {
        if (this.edgeTypeId && this.edgeSourceId && this.edgeTargetId) {
            if (isSourceRoutingHandle(edge, reconnectHandle)) {
                this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction(), new ShowEdgeReconnectSelectSourceFeedbackAction(this.edgeTypeId, this.edgeTargetId)]);
                this.reconnectMode = "NEW_SOURCE";
            } else if (isTargetRoutingHandle(edge, reconnectHandle)) {
                this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction(), new ShowEdgeReconnectSelectTargetFeedbackAction(this.edgeTypeId, this.edgeSourceId)]);
                this.reconnectMode = "NEW_TARGET";
            }
        }
    }

    private isReconnecting(): boolean {
        return this.reconnectMode !== undefined;
    }

    private isReconnectingNewSource(): boolean {
        return this.reconnectMode === "NEW_SOURCE";
    }

    private requiresReconnect(sourceId: string, targetId: string): boolean {
        return this.edgeSourceId !== sourceId || this.edgeTargetId !== targetId;
    }

    private setNewConnectable(connectable?: SModelElement & Connectable) {
        this.newConnectable = connectable;
    }

    private isReadyToReconnect() {
        return this.isEdgeSelected() && this.isReconnecting() && this.newConnectable !== undefined;
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        this.isMouseDown = true;
        if (event.button === 0) {
            const reconnectHandle = findParentByFeature(target, isReconnectHandle);
            const edge = findParentByFeature(target, isRoutable);
            if (this.isEdgeSelected() && edge && reconnectHandle) {
                // PHASE 2: Select reconnect handle on selected edge
                this.setReconnectHandleSelected(edge, reconnectHandle);
            } else if (this.isValidEdge(edge)) {
                // PHASE 1: Select edge
                this.setEdgeSelected(edge);
            } else if (this.isReconnecting()) {
                // PHASE 3: Select new connectable (target or source) for reconnecting the selected edge
                // if no connectable was selected, do nothing, allow clicking on other elements and empty area during this phase
                const connectable = findParentByFeature(target, isConnectable);
                if (connectable) {
                    this.setNewConnectable(connectable);
                }
            } else {
                this.reset();
            }
        }
        return result;
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        if (this.isMouseDown) {
            // reset any selected connectables when we are dragging, maybe the user is just panning
            this.setNewConnectable(undefined);
        }
        return [];
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        this.isMouseDown = false;
        if (!this.isReadyToReconnect()) {
            return [];
        }

        const result: Action[] = [];
        if (this.newConnectable) {
            const id = this.edgeId;
            const type = this.edgeTypeId;
            const sourceId = this.isReconnectingNewSource() ? this.newConnectable.id : this.edgeSourceId;
            const targetId = this.isReconnectingNewSource() ? this.edgeTargetId : this.newConnectable.id;
            if (id && type && sourceId && targetId && this.requiresReconnect(sourceId, targetId)) {
                result.push(new DeleteElementAction([id]));
                result.push(new CreateConnectionOperationAction(type, sourceId, targetId));
            }
        }
        this.reset();
        return result;
    }

    public reset() {
        this.resetData();
        this.resetFeedback();
    }

    private resetData() {
        this.isMouseDown = false;
        this.edgeId = undefined;
        this.edgeSourceId = undefined;
        this.edgeTargetId = undefined;
        this.edgeTypeId = undefined;
        this.reconnectMode = undefined;
        this.newConnectable = undefined;
    }

    private resetFeedback() {
        this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction()]);
        this.tool.dispatchFeedback([new HideEdgeReconnectToolFeedbackAction()]);
    }
}
