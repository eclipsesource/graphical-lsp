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
    Action, AnchorComputerRegistry, Connectable, EnableDefaultToolsAction, findParentByFeature, isConnectable, MouseTool, //
    SModelElement, SRoutableElement, SRoutingHandle, Tool
} from "sprotty/lib";
import { isConfigurableEdge } from "../../base/edit-config/edit-config";
import { GLSP_TYPES } from "../../types";
import { ReconnectConnectionOperationAction } from "../reconnect/action-definitions";
import { isReconnectHandle, isRoutable, isSourceRoutingHandle, isTargetRoutingHandle } from "../reconnect/model";
import { SelectionTracker } from "../select/selection-tracker";
import { DrawEdgeFeedbackAction, feedbackEdgeId, RemoveEdgeFeedbackAction } from "../tool-feedback/creation-tool-feedback";
import { ApplyCursorCSSFeedbackAction, CursorCSS } from "../tool-feedback/cursor-feedback";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import {
    DrawFeebackEdgeSourceAction, FeedbackEdgeSourceMovingMouseListener, FeedbackEdgeTargetMovingMouseListener, HideEdgeReconnectHandlesFeedbackAction, //
    ShowEdgeReconnectHandlesFeedbackAction
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
        this.feedbackEdgeSourceMovingListener = new FeedbackEdgeSourceMovingMouseListener(this.anchorRegistry);
        this.feedbackEdgeTargetMovingListener = new FeedbackEdgeTargetMovingMouseListener(this.anchorRegistry);
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
    private selectedEdge?: SRoutableElement;

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
        if (this.selectedEdge && this.selectedEdge !== edge) {
            // reset from a previously selected edge
            this.reset();
        }

        this.selectedEdge = edge;
        this.tool.dispatchFeedback([new ShowEdgeReconnectHandlesFeedbackAction(this.selectedEdge.id)]);
    }

    private setReconnectHandleSelected(edge: SRoutableElement, reconnectHandle: SRoutingHandle) {
        if (this.selectedEdge) {
            if (isSourceRoutingHandle(edge, reconnectHandle)) {
                this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction(),
                new ApplyCursorCSSFeedbackAction(CursorCSS.EDGE_RECONNECT),
                new DrawFeebackEdgeSourceAction(this.selectedEdge.type, this.selectedEdge.targetId)]);
                this.reconnectMode = "NEW_SOURCE";
            } else if (isTargetRoutingHandle(edge, reconnectHandle)) {
                this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction(),
                new ApplyCursorCSSFeedbackAction(CursorCSS.EDGE_CREATION_TARGET),
                new DrawEdgeFeedbackAction(this.selectedEdge.type, this.selectedEdge.sourceId)]);
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
        if (this.selectedEdge) {
            return this.selectedEdge.sourceId !== sourceId || this.selectedEdge.targetId !== targetId
        }
        return false;
    }

    private isReadyToReconnect() {
        return this.setEdgeSelected && this.isReconnecting() && this.newConnectable !== undefined;
    }


    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        this.isMouseDown = true;
        if (event.button === 0) {
            const reconnectHandle = findParentByFeature(target, isReconnectHandle);
            const edge = findParentByFeature(target, isRoutable);
            if (this.selectedEdge && edge && reconnectHandle) {
                // PHASE 2: Select reconnect handle on selected edge
                this.setReconnectHandleSelected(edge, reconnectHandle);
            } else if (this.isValidEdge(edge)) {
                // PHASE 1: Select edge
                this.setEdgeSelected(edge);
            }
        } else if (event.button === 2) {
            // Stop tool execution on right click
            result.push(new EnableDefaultToolsAction());
        }
        return result;
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        this.isMouseDown = false;
        if (!this.isReadyToReconnect()) {
            return [];
        }

        const result: Action[] = [];
        if (this.selectedEdge && this.newConnectable) {
            const sourceId = this.isReconnectingNewSource() ? this.newConnectable.id : this.selectedEdge.sourceId;
            const targetId = this.isReconnectingNewSource() ? this.selectedEdge.targetId : this.newConnectable.id;
            if (sourceId && targetId && this.requiresReconnect(sourceId, targetId)) {
                result.push(new ReconnectConnectionOperationAction(this.selectedEdge.id, sourceId, targetId));
            }
            this.reset();
        }
        this.reset();
        return result;
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        if (this.isMouseDown) {
            // reset any selected connectables when we are dragging, maybe the user is just panning
            this.newConnectable = undefined
        }
        return [];
    }
    mouseOver(target: SModelElement, event: MouseEvent): Action[] {
        if (this.selectedEdge && this.isReconnecting()) {
            const currentTarget = findParentByFeature(target, isConnectable);
            if (currentTarget && isConfigurableEdge(this.selectedEdge)) {
                if ((this.reconnectMode === 'NEW_SOURCE' && this.selectedEdge.isAllowedSource(currentTarget.type)) ||
                    (this.reconnectMode === 'NEW_TARGET' && this.selectedEdge.isAllowedTarget(currentTarget.type))) {
                    this.newConnectable = currentTarget;
                    return [new ApplyCursorCSSFeedbackAction(CursorCSS.EDGE_RECONNECT)]
                }
            }
            return [new ApplyCursorCSSFeedbackAction(CursorCSS.OPERATION_NOT_ALLOWED)]
        }
        return [];
    }

    public reset() {
        this.resetData();
        this.resetFeedback();
    }

    private resetData() {
        this.isMouseDown = false;
        this.selectedEdge = undefined;
        this.reconnectMode = undefined;
        this.newConnectable = undefined;
    }

    private resetFeedback() {
        this.tool.dispatchFeedback([new HideEdgeReconnectHandlesFeedbackAction()]);
        this.tool.dispatchFeedback([new ApplyCursorCSSFeedbackAction(), new RemoveEdgeFeedbackAction()]);
    }
}
