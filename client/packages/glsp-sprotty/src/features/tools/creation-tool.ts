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
    Action, isCtrlOrCmd, MouseListener, //
    MouseTool, SModelElement, SModelRoot
} from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { getAbsolutePosition } from "../../utils/viewpoint-util";
import { CreateConnectionOperationAction, CreateNodeOperationAction } from "../operation/operation-actions";
import {
    FeedbackEdgeEndMovingMouseListener, HideEdgeCreationToolFeedbackAction, HideNodeCreationToolFeedbackAction, ShowEdgeCreationSelectSourceFeedbackAction, //
    ShowEdgeCreationSelectTargetFeedbackAction, ShowNodeCreationToolFeedbackAction
} from "../tool-feedback/creation-tool-feedback";
import { IFeedbackActionDispatcher } from "../tool-feedback/feedback-action-dispatcher";
import { EnableStandardToolsAction, Tool } from "../tool-manager/tool";


@injectable()
export class NodeCreationTool implements Tool {
    static ID = "glsp.nodecreationtool";
    public elementTypeId: string = "unknown";
    protected creationToolMouseListener: NodeCreationToolMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher) { }

    get id() {
        return `${NodeCreationTool.ID}.${this.elementTypeId}`;
    };

    enable() {
        this.creationToolMouseListener = new NodeCreationToolMouseListener(this.elementTypeId);
        this.mouseTool.register(this.creationToolMouseListener);
        this.feedbackDispatcher.registerFeedback(this, [new ShowNodeCreationToolFeedbackAction(this.elementTypeId)])
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
        this.feedbackDispatcher.deregisterFeedback(this, [new HideNodeCreationToolFeedbackAction(this.elementTypeId)])
    }
}

@injectable()
export class NodeCreationToolMouseListener extends MouseListener {
    constructor(protected elementTypeId: string) {
        super();
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        const location = getAbsolutePosition(target, event);
        const containerId: string | undefined = target instanceof SModelRoot ? undefined : target.id;
        const result: Action[] = [];
        result.push(new CreateNodeOperationAction(this.elementTypeId, location, containerId));
        if (!isCtrlOrCmd(event)) {
            result.push(new EnableStandardToolsAction());
        }
        return result;
    }

}

/**
 * Tool to create connections in a Diagram, by selecting a source and target node.
 */
@injectable()
export class EdgeCreationTool implements Tool {
    static ID = "glsp.edgecreationtool";
    public elementTypeId: string = "unknown";
    protected creationToolMouseListener: EdgeCreationToolMouseListener;
    protected feedbackEndMovingMouseListener: FeedbackEdgeEndMovingMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(GLSP_TYPES.IFeedbackActionDispatcher) protected feedbackDispatcher: IFeedbackActionDispatcher) { }

    get id() {
        return `${EdgeCreationTool.ID}.${this.elementTypeId}`;
    };

    enable() {
        this.creationToolMouseListener = new EdgeCreationToolMouseListener(this.elementTypeId, this);
        this.mouseTool.register(this.creationToolMouseListener);
        this.feedbackEndMovingMouseListener = new FeedbackEdgeEndMovingMouseListener();
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
export class EdgeCreationToolMouseListener extends MouseListener {
    private source?: string;
    private target?: string;

    private isMouseDown: boolean = false;
    private isMouseMove: boolean;

    constructor(protected elementTypeId: string, protected tool: EdgeCreationTool) {
        super();
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        this.isMouseDown = true;
        return [];
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        if (this.isMouseDown) {
            // Detect that the mouse moved while the button was pressed
            // In that case, we're dragging something, and shouldn't create
            // a connection
            this.isMouseMove = true;
            this.reinitialize();
        }
        return [];
    }

    private reinitialize() {
        this.source = undefined;
        this.target = undefined;
        this.tool.dispatchFeedback([new ShowEdgeCreationSelectSourceFeedbackAction(this.elementTypeId)]);
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        this.isMouseDown = false;
        if (this.isMouseMove) {
            this.isMouseMove = false;
            return [];
        }

        const result: Action[] = [];

        if (this.source == null) {
            this.source = target.id;
            this.tool.dispatchFeedback([new ShowEdgeCreationSelectTargetFeedbackAction(this.elementTypeId, this.source)]);
        } else {
            this.target = target.id;
            if (this.source != null && this.target != null) {
                result.push(new CreateConnectionOperationAction(this.elementTypeId, this.source, this.target));
                this.source = undefined;
                this.target = undefined;

                if (!isCtrlOrCmd(event)) {
                    result.push(new EnableStandardToolsAction());
                } else {
                    this.reinitialize();
                }
            }
        }

        return result;
    }
}
