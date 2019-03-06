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
import { CreateConnectionOperationAction } from "../operation/operation-actions";
import { CreateNodeOperationAction } from "../operation/operation-actions";
import { DragAwareMouseListener } from "./drag-aware-mouse-listener";
import { EnableDefaultToolsAction } from "sprotty/lib";
import { FeedbackEdgeEndMovingMouseListener } from "../tool-feedback/creation-tool-feedback";
import { GLSP_TYPES } from "../../types";
import { HideEdgeCreationToolFeedbackAction } from "../tool-feedback/creation-tool-feedback";
import { HideNodeCreationToolFeedbackAction } from "../tool-feedback/creation-tool-feedback";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import { MouseTool } from "sprotty/lib";
import { OperationKind } from "../operation/set-operations";
import { ShowEdgeCreationSelectSourceFeedbackAction } from "../tool-feedback/creation-tool-feedback";
import { ShowEdgeCreationSelectTargetFeedbackAction } from "../tool-feedback/creation-tool-feedback";
import { ShowNodeCreationToolFeedbackAction } from "../tool-feedback/creation-tool-feedback";
import { SModelElement } from "sprotty/lib";
import { SModelRoot } from "sprotty/lib";
import { Tool } from "sprotty/lib";
import { TypeAware } from "../../base/tool-manager/tool-manager-action-handler";

import { deriveOperationId } from "../operation/set-operations";
import { getAbsolutePosition } from "../../utils/viewpoint-util";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isCtrlOrCmd } from "sprotty/lib";


export const TOOL_ID_PREFIX = "tool";

export function deriveToolId(operationKind: string, elementTypeId?: string) {
    return `${TOOL_ID_PREFIX}_${deriveOperationId(operationKind, elementTypeId)}`;
}

@injectable()
export class NodeCreationTool implements Tool, TypeAware {
    public elementTypeId: string = "unknown";
    protected creationToolMouseListener: NodeCreationToolMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher) { }

    get id() {
        return deriveToolId(OperationKind.CREATE_NODE, this.elementTypeId);
    }

    enable() {
        this.creationToolMouseListener = new NodeCreationToolMouseListener(this.elementTypeId);
        this.mouseTool.register(this.creationToolMouseListener);
        this.feedbackDispatcher.registerFeedback(this, [new ShowNodeCreationToolFeedbackAction(this.elementTypeId)]);
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
        this.feedbackDispatcher.deregisterFeedback(this, [new HideNodeCreationToolFeedbackAction(this.elementTypeId)]);
    }
}

@injectable()
export class NodeCreationToolMouseListener extends DragAwareMouseListener {

    constructor(protected elementTypeId: string) {
        super();
    }

    nonDraggingMouseUp(target: SModelElement, event: MouseEvent): Action[] {
        const location = getAbsolutePosition(target, event);
        const containerId: string | undefined = target instanceof SModelRoot ? undefined : target.id;
        const result: Action[] = [];
        result.push(new CreateNodeOperationAction(this.elementTypeId, location, containerId));
        if (!isCtrlOrCmd(event)) {
            result.push(new EnableDefaultToolsAction());
        }
        return result;
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
        @inject(AnchorComputerRegistry) protected anchorRegistry: AnchorComputerRegistry) { }

    get id() {
        return deriveToolId(OperationKind.CREATE_CONNECTION, this.elementTypeId);
    }

    enable() {
        this.creationToolMouseListener = new EdgeCreationToolMouseListener(this.elementTypeId, this);
        this.mouseTool.register(this.creationToolMouseListener);
        this.feedbackEndMovingMouseListener = new FeedbackEdgeEndMovingMouseListener(this.anchorRegistry);
        this.mouseTool.register(this.feedbackEndMovingMouseListener);
        this.dispatchFeedback([new ShowEdgeCreationSelectSourceFeedbackAction(this.elementTypeId)]);
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
        this.mouseTool.deregister(this.feedbackEndMovingMouseListener);
        this.feedbackDispatcher.deregisterFeedback(this, [new HideEdgeCreationToolFeedbackAction(this.elementTypeId)]);
    }

    dispatchFeedback(actions: Action[]) {
        this.feedbackDispatcher.registerFeedback(this, actions);
    }

}

@injectable()
export class EdgeCreationToolMouseListener extends DragAwareMouseListener {

    private source?: string;
    private target?: string;

    constructor(protected elementTypeId: string, protected tool: EdgeCreationTool) {
        super();
    }

    private reinitialize() {
        this.source = undefined;
        this.target = undefined;
        this.tool.dispatchFeedback([
            new HideEdgeCreationToolFeedbackAction(this.elementTypeId),
            new ShowEdgeCreationSelectSourceFeedbackAction(this.elementTypeId)]);
    }

    nonDraggingMouseUp(element: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];

        if (this.source === undefined) {
            this.source = element.id;
            this.tool.dispatchFeedback([new ShowEdgeCreationSelectTargetFeedbackAction(this.elementTypeId, this.source)]);
        } else {
            this.target = element.id;
            if (this.source !== undefined && this.target !== undefined) {
                result.push(new CreateConnectionOperationAction(this.elementTypeId, this.source, this.target));
                if (!isCtrlOrCmd(event)) {
                    result.push(new EnableDefaultToolsAction());
                } else {
                    this.reinitialize();
                }
            }
        }

        return result;
    }
}
