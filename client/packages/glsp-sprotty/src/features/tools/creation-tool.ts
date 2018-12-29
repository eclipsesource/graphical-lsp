/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Camille Letavernier - initial API and implementation
 *  Philip Langer - migration to tool manager API
 ******************************************************************************/

import { inject, injectable } from "inversify";
import {
    Action, IActionDispatcher, isCtrlOrCmd, MouseListener, //
    MouseTool, SModelElement, SModelRoot, TYPES
} from "sprotty/lib";
import { getAbsolutePosition } from "../../utils/viewpoint-util";
import { CreateConnectionOperationAction, CreateNodeOperationAction } from "../operation/operation-actions";
import {
    FeedbackEdgeEndMovingMouseListener, HideEdgeCreationToolFeedbackAction, HideNodeCreationToolFeedbackAction, ShowEdgeCreationSelectSourceFeedbackAction, //
    ShowEdgeCreationSelectTargetFeedbackAction, ShowNodeCreationToolFeedbackAction
} from "../tool-feedback/creation-tool-feedback";
import { EnableStandardToolsAction, Tool } from "../tool-manager/tool";


@injectable()
export class NodeCreationTool implements Tool {
    static ID = "glsp.nodecreationtool";
    public elementTypeId: string = "unknown";
    protected creationToolMouseListener: NodeCreationToolMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(TYPES.IActionDispatcher) protected actionDispatcher: IActionDispatcher) { }

    get id() {
        return `${NodeCreationTool.ID}.${this.elementTypeId}`;
    };

    enable() {
        this.creationToolMouseListener = new NodeCreationToolMouseListener(this.elementTypeId);
        this.mouseTool.register(this.creationToolMouseListener);
        this.actionDispatcher.dispatch(new ShowNodeCreationToolFeedbackAction(this.elementTypeId));
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
        this.actionDispatcher.dispatch(new HideNodeCreationToolFeedbackAction(this.elementTypeId));
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
        @inject(TYPES.IActionDispatcher) protected actionDispatcher: IActionDispatcher) { }

    get id() {
        return `${EdgeCreationTool.ID}.${this.elementTypeId}`;
    };

    enable() {
        this.creationToolMouseListener = new EdgeCreationToolMouseListener(this.elementTypeId);
        this.mouseTool.register(this.creationToolMouseListener);
        this.feedbackEndMovingMouseListener = new FeedbackEdgeEndMovingMouseListener();
        this.mouseTool.register(this.feedbackEndMovingMouseListener);
        this.actionDispatcher.dispatch(new ShowEdgeCreationSelectSourceFeedbackAction(this.elementTypeId));
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
        this.mouseTool.deregister(this.feedbackEndMovingMouseListener);
        this.actionDispatcher.dispatch(new HideEdgeCreationToolFeedbackAction(this.elementTypeId));
    }

}

@injectable()
export class EdgeCreationToolMouseListener extends MouseListener {
    private source?: string;
    private target?: string;

    private isMouseDown: boolean = false;
    private isMouseMove: boolean;

    constructor(protected elementTypeId: string) {
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
            this.source = undefined;
            this.target = undefined;
            return [new ShowEdgeCreationSelectSourceFeedbackAction(this.elementTypeId)];
        }
        return [];
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
            result.push(new ShowEdgeCreationSelectTargetFeedbackAction(this.elementTypeId, this.source));
        } else {
            this.target = target.id;
            if (this.source != null && this.target != null) {
                result.push(new CreateConnectionOperationAction(this.elementTypeId, this.source, this.target));
                this.source = undefined;
                this.target = undefined;

                if (!isCtrlOrCmd(event)) {
                    result.push(new EnableStandardToolsAction());
                }
            }
        }

        return result;
    }
}
