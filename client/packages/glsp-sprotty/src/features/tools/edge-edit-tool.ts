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
import { inject, injectable, optional } from "inversify";
import {
    Action, AnchorComputerRegistry, Connectable, EdgeRouterRegistry, findParentByFeature, isConnectable, MouseTool, //
    SModelElement, SRoutableElement, SRoutingHandle, Tool
} from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { ReconnectConnectionOperationAction, RerouteConnectionOperationAction } from "../reconnect/action-definitions";
import { isReconnectHandle, isRoutable, isRoutingHandle, isSourceRoutingHandle, isTargetRoutingHandle, SReconnectHandle } from "../reconnect/model";
import { SelectionTracker } from "../select/selection-tracker";
import { FeedbackMoveMouseListener } from "../tool-feedback/change-bounds-tool-feedback";
import { feedbackEdgeId } from "../tool-feedback/creation-tool-feedback";
import {
    FeedbackEdgeRouteMovingMouseListener, FeedbackEdgeSourceMovingMouseListener, FeedbackEdgeTargetMovingMouseListener, HideEdgeReconnectHandlesFeedbackAction, //
    HideEdgeReconnectToolFeedbackAction, ShowEdgeReconnectHandlesFeedbackAction, ShowEdgeReconnectSelectSourceFeedbackAction, ShowEdgeReconnectSelectTargetFeedbackAction
} from "../tool-feedback/edge-edit-tool-feedback";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";

@injectable()
export class EdgeEditTool implements Tool {
    static ID = "glsp.edge-edit-tool";
    readonly id = EdgeEditTool.ID;

    protected feedbackEdgeSourceMovingListener: FeedbackEdgeSourceMovingMouseListener;
    protected feedbackEdgeTargetMovingListener: FeedbackEdgeTargetMovingMouseListener;
    protected feedbackMovingListener: FeedbackMoveMouseListener;
    protected reconnectEdgeListener: ReconnectEdgeListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher,
        @inject(AnchorComputerRegistry) protected anchorRegistry: AnchorComputerRegistry,
        @inject(EdgeRouterRegistry) @optional() protected edgeRouterRegistry?: EdgeRouterRegistry) {
    }

    enable(): void {
        this.reconnectEdgeListener = new ReconnectEdgeListener(this);
        this.mouseTool.register(this.reconnectEdgeListener);

        // install feedback move mouse listener for client-side move updates
        this.feedbackEdgeSourceMovingListener = new FeedbackEdgeSourceMovingMouseListener(this.anchorRegistry);
        this.feedbackEdgeTargetMovingListener = new FeedbackEdgeTargetMovingMouseListener(this.anchorRegistry);
        this.feedbackMovingListener = new FeedbackEdgeRouteMovingMouseListener(this.edgeRouterRegistry);
        this.mouseTool.register(this.feedbackEdgeSourceMovingListener);
        this.mouseTool.register(this.feedbackEdgeTargetMovingListener);
        this.mouseTool.register(this.feedbackMovingListener);
    }

    disable(): void {
        this.reconnectEdgeListener.reset();
        this.mouseTool.deregister(this.feedbackEdgeSourceMovingListener);
        this.mouseTool.deregister(this.feedbackEdgeTargetMovingListener);
        this.mouseTool.deregister(this.feedbackMovingListener);
        this.mouseTool.deregister(this.reconnectEdgeListener);
    }

    dispatchFeedback(actions: Action[]) {
        this.feedbackDispatcher.registerFeedback(this, actions);
    }
}

class ReconnectEdgeListener extends SelectionTracker {
    private isMouseDown: boolean;

    // active selection data
    private edge?: SRoutableElement;
    private routingHandle?: SRoutingHandle;

    // new connectable (source or target) for edge
    private newConnectable?: SModelElement & Connectable;

    // active reconnect handle data
    private reconnectMode?: 'NEW_SOURCE' | 'NEW_TARGET';

    constructor(protected tool: EdgeEditTool) {
        super();
    }

    private isValidEdge(edge?: SRoutableElement): edge is SRoutableElement {
        return edge !== undefined && edge.id !== feedbackEdgeId(edge.root);
    }

    private setEdgeSelected(edge: SRoutableElement) {
        if (this.edge && this.edge.id !== edge.id) {
            // reset from a previously selected edge
            this.reset();
        }

        this.edge = edge;
        this.tool.dispatchFeedback([new ShowEdgeReconnectHandlesFeedbackAction(this.edge.id)]);
    }

    private isEdgeSelected(): boolean {
        return this.edge !== undefined;
    }

    private setReconnectHandleSelected(edge: SRoutableElement, reconnectHandle: SReconnectHandle) {
        if (this.edge && this.edge.target && this.edge.source) {
            if (isSourceRoutingHandle(edge, reconnectHandle)) {
                this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction(), new ShowEdgeReconnectSelectSourceFeedbackAction(this.edge.type, this.edge.target.id)]);
                this.reconnectMode = "NEW_SOURCE";
            } else if (isTargetRoutingHandle(edge, reconnectHandle)) {
                this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction(), new ShowEdgeReconnectSelectTargetFeedbackAction(this.edge.type, this.edge.source.id)]);
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

    private setRoutingHandleSelected(edge: SRoutableElement, routingHandle: SRoutingHandle) {
        if (this.edge && this.edge.target && this.edge.source) {
            this.routingHandle = routingHandle;
        }
    }

    private requiresReconnect(sourceId: string, targetId: string): boolean {
        return this.edge !== undefined && (this.edge.sourceId !== sourceId || this.edge.targetId !== targetId);
    }

    private setNewConnectable(connectable?: SModelElement & Connectable) {
        this.newConnectable = connectable;
    }

    private isReadyToReconnect() {
        return this.isEdgeSelected() && this.isReconnecting() && this.newConnectable !== undefined;
    }

    private isReadyToReroute() {
        return this.routingHandle !== undefined;
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        this.isMouseDown = true;
        if (event.button === 0) {
            const reconnectHandle = findParentByFeature(target, isReconnectHandle);
            const routingHandle = findParentByFeature(target, isRoutingHandle);
            const edge = findParentByFeature(target, isRoutable);
            if (this.isEdgeSelected() && edge && reconnectHandle) {
                // PHASE 2 Reconnect: Select reconnect handle on selected edge
                this.setReconnectHandleSelected(edge, reconnectHandle);
            } else if (this.isEdgeSelected() && edge && routingHandle) {
                // PHASE 2 Reroute: Select routing handle on selected edge
                this.setRoutingHandleSelected(edge, routingHandle);
            } else if (this.isValidEdge(edge)) {
                // PHASE 1: Select edge
                this.setEdgeSelected(edge);
            } else if (this.isReconnecting()) {
                // PHASE 3 Reconnect: Select new connectable (target or source) for reconnecting the selected edge
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
        if (!this.isReadyToReconnect() && !this.isReadyToReroute()) {
            return [];
        }

        const result: Action[] = [];
        if (this.edge && this.newConnectable) {
            const sourceId = this.isReconnectingNewSource() ? this.newConnectable.id : this.edge.sourceId;
            const targetId = this.isReconnectingNewSource() ? this.edge.targetId : this.newConnectable.id;
            if (this.requiresReconnect(sourceId, targetId)) {
                result.push(new ReconnectConnectionOperationAction(this.edge.id, sourceId, targetId));
            }
        } else if (this.edge && this.routingHandle) {
            result.push(new RerouteConnectionOperationAction(this.edge.id, this.edge.routingPoints));
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
        this.edge = undefined;
        this.reconnectMode = undefined;
        this.newConnectable = undefined;
        this.routingHandle = undefined;
    }

    private resetFeedback() {
        this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction()]);
        this.tool.dispatchFeedback([new HideEdgeReconnectToolFeedbackAction()]);
    }
}
