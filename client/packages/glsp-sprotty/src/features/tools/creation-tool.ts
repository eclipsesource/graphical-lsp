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
import { CreateConnectionOperationAction } from "../operation/operation-actions";
import { CreateNodeOperationAction } from "../operation/operation-actions";
import { CursorCSS } from "../tool-feedback/cursor-feedback";
import { DragAwareMouseListener } from "./drag-aware-mouse-listener";
import { DrawEdgeFeedbackAction } from "../tool-feedback/creation-tool-feedback";
import { EdgeEditConfig } from "../../base/edit-config/edit-config";
import { EnableDefaultToolsAction } from "sprotty/lib";
import { FeedbackEdgeEndMovingMouseListener } from "../tool-feedback/creation-tool-feedback";
import { GLSP_TYPES } from "../../types";
import { IEditConfigProvider } from "../../base/edit-config/edit-config";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import { MouseTool } from "sprotty/lib";
import { OperationKind } from "../operation/set-operations";
import { RemoveEdgeFeedbackAction } from "../tool-feedback/creation-tool-feedback";
import { SModelElement } from "sprotty/lib";
import { SModelRoot } from "sprotty/lib";
import { SParentElement } from "sprotty/lib";
import { Tool } from "sprotty/lib";
import { TypeAware } from "../../base/tool-manager/tool-manager-action-handler";

import { deriveOperationId } from "../operation/set-operations";
import { findParent } from "sprotty/lib";
import { findParentByFeature } from "sprotty/lib";
import { getAbsolutePosition } from "../../utils/viewpoint-util";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isConfigurableNode } from "../../base/edit-config/edit-config";
import { isConnectable } from "sprotty/lib";
import { isCtrlOrCmd } from "sprotty/lib";






export const TOOL_ID_PREFIX = "tool"

export function deriveToolId(operationKind: string, elementTypeId?: string) {
    return `${TOOL_ID_PREFIX}_${deriveOperationId(operationKind, elementTypeId)}`
}

@injectable()
export class NodeCreationTool implements Tool, TypeAware {
    public elementTypeId: string = "unknown";
    protected creationToolMouseListener: NodeCreationToolMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher) { }

    get id() {
        return deriveToolId(OperationKind.CREATE_NODE, this.elementTypeId)
    };

    enable() {
        this.creationToolMouseListener = new NodeCreationToolMouseListener(this.elementTypeId, this.feedbackDispatcher);
        this.mouseTool.register(this.creationToolMouseListener);
        this.feedbackDispatcher.registerFeedback(this, [new ApplyCursorCSSFeedbackAction(CursorCSS.NODE_CREATION)])
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
        this.feedbackDispatcher.deregisterFeedback(this, [new ApplyCursorCSSFeedbackAction()])
    }
}

@injectable()
export class NodeCreationToolMouseListener extends DragAwareMouseListener {
    private currentContainer?: SModelElement;
    private creationAllowed: boolean = false;
    constructor(protected elementTypeId: string, protected feedbackDispatcher: IFeedbackActionDispatcher) {
        super();
    }

    nonDraggingMouseUp(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        if (this.currentContainer && this.creationAllowed) {
            const location = getAbsolutePosition(target, event);
            result.push(new CreateNodeOperationAction(this.elementTypeId, location, this.currentContainer.id));
        }

        if (!isCtrlOrCmd(event)) {
            result.push(new EnableDefaultToolsAction());
        }
        return result;
    }

    mouseOver(target: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
        const currentTarget = findParent(target, e => e instanceof SParentElement);
        if (currentTarget !== this.currentContainer) {
            this.currentContainer = currentTarget as SModelElement;
            this.currentContainer = currentTarget as SModelElement;
            if (this.currentContainer) {
                this.creationAllowed = isConfigurableNode(this.currentContainer) ?
                    this.currentContainer.isContainableElement(this.elementTypeId) :
                    this.currentContainer instanceof SModelRoot;

                if (this.creationAllowed) {
                    return [new ApplyCursorCSSFeedbackAction(CursorCSS.NODE_CREATION)];
                }
            }
            return [new ApplyCursorCSSFeedbackAction(CursorCSS.OPERATION_NOT_ALLOWED)];
        }
        return [];
    }
}

/**
 * Tool to create connections in a Diagram, by selecting a source and target node.
 */
@injectable()
export class EdgeCreationTool implements Tool, TypeAware {
    public elementTypeId: string = "unknown";
    protected creationToolMouseListener: EdgeCreationToolMouseListener;
    protected feedbackEndMovingMouseListener: FeedbackEdgeEndMovingMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher,
        @inject(AnchorComputerRegistry) protected anchorRegistry: AnchorComputerRegistry,
        @inject(GLSP_TYPES.IEditConfigProvider) protected editConfigProvider: IEditConfigProvider) { }

    get id() {
        return deriveToolId(OperationKind.CREATE_CONNECTION, this.elementTypeId)
    };

    enable() {
        const editConfig = this.editConfigProvider.getEditConfig(this.elementTypeId) as EdgeEditConfig;
        this.creationToolMouseListener = new EdgeCreationToolMouseListener(this.elementTypeId, this, editConfig);
        this.mouseTool.register(this.creationToolMouseListener);
        this.feedbackEndMovingMouseListener = new FeedbackEdgeEndMovingMouseListener(this.anchorRegistry);
        this.mouseTool.register(this.feedbackEndMovingMouseListener);
        this.dispatchFeedback([new ApplyCursorCSSFeedbackAction(CursorCSS.OPERATION_NOT_ALLOWED)]);
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
        this.mouseTool.deregister(this.feedbackEndMovingMouseListener);
        this.feedbackDispatcher.deregisterFeedback(this, [new RemoveEdgeFeedbackAction(), new ApplyCursorCSSFeedbackAction()]);
    }

    dispatchFeedback(actions: Action[]) {
        this.feedbackDispatcher.registerFeedback(this, actions);
    }
}

@injectable()
export class EdgeCreationToolMouseListener extends DragAwareMouseListener {

    private source?: string;
    private target?: string;
    private currentTarget?: SModelElement;
    private allowedTarget: boolean;

    constructor(protected elementTypeId: string, protected tool: EdgeCreationTool, protected edgeConfig?: EdgeEditConfig) {
        super();
    }

    private reinitialize() {
        this.source = undefined;
        this.target = undefined;
        this.currentTarget = undefined;
        this.allowedTarget = false;
        this.tool.dispatchFeedback([new ApplyCursorCSSFeedbackAction(CursorCSS.OPERATION_NOT_ALLOWED)])
    }

    nonDraggingMouseUp(element: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        if (event.button === 0) {
            if (this.source === undefined) {
                if (this.currentTarget && this.allowedTarget) {
                    this.source = this.currentTarget.id;
                    this.tool.dispatchFeedback([new DrawEdgeFeedbackAction(this.elementTypeId, this.source)]);
                }
            } else {
                if (this.currentTarget && this.allowedTarget) {
                    this.target = this.currentTarget.id;
                }
            }

            if (this.source !== undefined && this.target !== undefined) {
                result.push(new CreateConnectionOperationAction(this.elementTypeId, this.source, this.target));
                if (!isCtrlOrCmd(event)) {
                    result.push(new EnableDefaultToolsAction());
                } else {
                    this.reinitialize();
                }
            }
        } else if (event.button === 2) {
            result.push(new EnableDefaultToolsAction());
        }
        return result;
    }


    mouseOver(target: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
        const newCurrentTarget = findParentByFeature(target, isConnectable);
        if (newCurrentTarget !== this.currentTarget) {
            this.currentTarget = newCurrentTarget;
            if (this.currentTarget) {
                this.allowedTarget = this.edgeConfig ? this.edgeConfig.isAllowedTarget(this.currentTarget) : false;
                if (this.allowedTarget) {
                    const action = this.source === undefined ? new ApplyCursorCSSFeedbackAction(CursorCSS.EDGE_CREATION_SOURCE) :
                        new ApplyCursorCSSFeedbackAction(CursorCSS.EDGE_CREATION_TARGET);
                    return [action]
                }
            }
            return [new ApplyCursorCSSFeedbackAction(CursorCSS.OPERATION_NOT_ALLOWED)];
        }
        return [];
    }
}
