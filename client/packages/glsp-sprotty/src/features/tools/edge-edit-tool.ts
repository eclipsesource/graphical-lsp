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
import { AnchorComputerRegistry } from "sprotty/lib";
import { ApplyCursorCSSFeedbackAction } from "../tool-feedback/cursor-feedback";
import { Connectable } from "sprotty/lib";
import { CursorCSS } from "../tool-feedback/cursor-feedback";
import { DrawFeedbackEdgeAction } from "../tool-feedback/creation-tool-feedback";
import { DrawFeedbackEdgeSourceAction } from "../tool-feedback/edge-edit-tool-feedback";
import { EdgeRouterRegistry } from "sprotty/lib";
import { FeedbackEdgeRouteMovingMouseListener } from "../tool-feedback/edge-edit-tool-feedback";
import { FeedbackEdgeSourceMovingMouseListener } from "../tool-feedback/edge-edit-tool-feedback";
import { FeedbackEdgeTargetMovingMouseListener } from "../tool-feedback/edge-edit-tool-feedback";
import { FeedbackMoveMouseListener } from "../tool-feedback/change-bounds-tool-feedback";
import { GLSP_TYPES } from "../../types";
import { HideEdgeReconnectHandlesFeedbackAction } from "../tool-feedback/edge-edit-tool-feedback";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import { MouseTool } from "sprotty/lib";
import { ReconnectConnectionOperationAction } from "../reconnect/action-definitions";
import { RemoveFeedbackEdgeAction } from "../tool-feedback/creation-tool-feedback";
import { RerouteConnectionOperationAction } from "../reconnect/action-definitions";
import { SelectionTracker } from "../select/selection-tracker";
import { ShowEdgeReconnectHandlesFeedbackAction } from "../tool-feedback/edge-edit-tool-feedback";
import { SModelElement } from "sprotty/lib";
import { SReconnectHandle } from "../reconnect/model";
import { SRoutableElement } from "sprotty/lib";
import { SRoutingHandle } from "sprotty/lib";
import { SwitchEditModeAction } from "sprotty/lib";
import { Tool } from "sprotty/lib";

import { feedbackEdgeId } from "../tool-feedback/creation-tool-feedback";
import { findParentByFeature } from "sprotty/lib";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isConfigurableEdge } from "../../base/edit-config/edit-config";
import { isConnectable } from "sprotty/lib";
import { isReconnectHandle } from "../reconnect/model";
import { isRoutable } from "../reconnect/model";
import { isRoutingHandle } from "../reconnect/model";
import { isSourceRoutingHandle } from "../reconnect/model";
import { isTargetRoutingHandle } from "../reconnect/model";
import { optional } from "inversify";

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
        // note: order is important here as we want the reconnect handles to cover the routing handles
        this.tool.dispatchFeedback([new SwitchEditModeAction([this.edge.id], []), new ShowEdgeReconnectHandlesFeedbackAction(this.edge.id)]);
    }

    private isEdgeSelected(): boolean {
        return this.edge !== undefined;
    }

    private setReconnectHandleSelected(edge: SRoutableElement, reconnectHandle: SReconnectHandle) {
        if (this.edge && this.edge.target && this.edge.source) {
            if (isSourceRoutingHandle(edge, reconnectHandle)) {
                this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction(),
                new ApplyCursorCSSFeedbackAction(CursorCSS.EDGE_RECONNECT),
                new DrawFeedbackEdgeSourceAction(this.edge.type, this.edge.targetId)]);
                this.reconnectMode = "NEW_SOURCE";
            } else if (isTargetRoutingHandle(edge, reconnectHandle)) {
                this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction(),
                new ApplyCursorCSSFeedbackAction(CursorCSS.EDGE_CREATION_TARGET),
                new DrawFeedbackEdgeAction(this.edge.type, this.edge.sourceId)]);
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
            this.reset();
        } else if (this.edge && this.routingHandle) {
            // we need to re-retrieve the edge as it might have changed due to a server udpate since we do not reset the state between reroute actions
            const latestEdge = target.index.getById(this.edge.id);
            if (latestEdge && isRoutable(latestEdge)) {
                result.push(new RerouteConnectionOperationAction(latestEdge.id, latestEdge.routingPoints));
                this.routingHandle = undefined;
            }
        }
        return result;
    }

    mouseOver(target: SModelElement, event: MouseEvent): Action[] {
        if (this.edge && this.isReconnecting()) {
            const currentTarget = findParentByFeature(target, isConnectable);
            if (currentTarget && isConfigurableEdge(this.edge)) {
                if ((this.reconnectMode === 'NEW_SOURCE' && this.edge.isAllowedSource(currentTarget.type)) ||
                    (this.reconnectMode === 'NEW_TARGET' && this.edge.isAllowedTarget(currentTarget.type))) {
                    this.setNewConnectable(currentTarget);
                    this.tool.dispatchFeedback([new ApplyCursorCSSFeedbackAction(CursorCSS.EDGE_RECONNECT)]);
                    return [];
                }
            }
            this.tool.dispatchFeedback([new ApplyCursorCSSFeedbackAction(CursorCSS.OPERATION_NOT_ALLOWED)]);
        }
        return [];
    }

    public reset() {
        this.resetFeedback();
        this.resetData();
    }

    private resetData() {
        this.isMouseDown = false;
        this.edge = undefined;
        this.reconnectMode = undefined;
        this.newConnectable = undefined;
        this.routingHandle = undefined;
    }

    private resetFeedback() {
        const result: Action[] = [];
        if (this.edge) {
            result.push(new SwitchEditModeAction([], [this.edge.id]));
        }
        result.push(...[new HideEdgeReconnectHandlesFeedbackAction(),
        new ApplyCursorCSSFeedbackAction(), new RemoveFeedbackEdgeAction()]);
        this.tool.dispatchFeedback(result);

    }
}
