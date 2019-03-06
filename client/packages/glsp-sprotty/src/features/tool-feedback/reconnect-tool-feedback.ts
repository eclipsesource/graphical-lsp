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
import { CommandExecutionContext } from "sprotty/lib";
import { FeedbackCommand } from "./model";
import { FeedbackEdgeEnd } from "./creation-tool-feedback";
import { FeedbackEdgeEndMovingMouseListener } from "./creation-tool-feedback";
import { HideEdgeCreationToolFeedbackCommand } from "./creation-tool-feedback";
import { MouseListener } from "sprotty/lib";
import { MoveAction } from "sprotty/lib";
import { PolylineEdgeRouter } from "sprotty/lib";
import { SConnectableElement } from "sprotty/lib";
import { ShowEdgeCreationSelectTargetFeedbackAction } from "./creation-tool-feedback";
import { ShowEdgeCreationSelectTargetFeedbackCommand } from "./creation-tool-feedback";
import { SModelElement } from "sprotty/lib";
import { SModelRoot } from "sprotty/lib";
import { TYPES } from "sprotty/lib";

import { addReconnectHandles } from "../reconnect/model";
import { applyCssClassesToRoot } from "./model";
import { center } from "sprotty/lib";
import { euclideanDistance } from "sprotty/lib";
import { feedbackEdgeEndId } from "./creation-tool-feedback";
import { feedbackEdgeId } from "./creation-tool-feedback";
import { findChildrenAtPosition } from "sprotty/lib";
import { findParentByFeature } from "sprotty/lib";
import { getAbsolutePosition } from "../../utils/viewpoint-util";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isBoundsAware } from "sprotty/lib";
import { isConnectable } from "sprotty/lib";
import { isNotUndefined } from "../../utils/smodel-util";
import { isRoutable } from "../reconnect/model";
import { removeReconnectHandles } from "../reconnect/model";
import { unapplyCssClassesToRoot } from "./model";

/**
 * RECONNECT HANDLES FEEDBACK
 */

export class ShowEdgeReconnectHandlesFeedbackAction implements Action {
    kind = ShowEdgeReconnectHandlesFeedbackCommand.KIND;
    constructor(readonly elementId?: string) { }
}

export class HideEdgeReconnectHandlesFeedbackAction implements Action {
    kind = HideEdgeReconnectHandlesFeedbackCommand.KIND;
    constructor() { }
}

@injectable()
export class ShowEdgeReconnectHandlesFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edge-reconnect-tool.handles.feedback.show';

    constructor(@inject(TYPES.Action) protected action: ShowEdgeReconnectHandlesFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): SModelRoot {
        const index = context.root.index;
        index.all().filter(isRoutable).forEach(removeReconnectHandles);

        if (isNotUndefined(this.action.elementId)) {
            const routableElement = index.getById(this.action.elementId);
            if (isNotUndefined(routableElement) && isRoutable(routableElement)) {
                addReconnectHandles(routableElement);
            }
        }

        return context.root;
    }
}

@injectable()
export class HideEdgeReconnectHandlesFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edge-reconnect-tool.handles.feedback.hide';

    constructor(@inject(TYPES.Action) protected action: HideEdgeReconnectHandlesFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): SModelRoot {
        const index = context.root.index;
        index.all().filter(isRoutable).forEach(removeReconnectHandles);
        return context.root;
    }
}

/**
 * SOURCE AND TARGET EDGE FEEDBACK
 */

export class ShowEdgeReconnectSelectSourceFeedbackAction implements Action {
    kind = ShowEdgeReconnectSelectSourceFeedbackCommand.KIND;
    constructor(readonly elementTypeId: string, readonly targetId: string) { }
}


export class ShowEdgeReconnectSelectTargetFeedbackAction extends ShowEdgeCreationSelectTargetFeedbackAction {
    kind = ShowEdgeCreationSelectTargetFeedbackCommand.KIND; // re-use select target feedback from creation tool
    constructor(readonly elementTypeId: string, readonly sourceId: string) {
        super(elementTypeId, sourceId);
    }
}

export class HideEdgeReconnectToolFeedbackAction implements Action {
    kind = HideEdgeReconnectToolFeedbackCommand.KIND;
    constructor() { }
}

const EDGE_RECONNECT_SOURCE_CSS_CLASS = 'edge-reconnect-select-source-mode';

@injectable()
export class ShowEdgeReconnectSelectSourceFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edge-reconnect-tool.selectsource.feedback.show';

    constructor(@inject(TYPES.Action) protected action: ShowEdgeReconnectSelectSourceFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): SModelRoot {
        drawFeedbackEdgeSource(context, this.action.targetId, this.action.elementTypeId);
        return applyCssClassesToRoot(context, [EDGE_RECONNECT_SOURCE_CSS_CLASS]);
    }
}

@injectable()
export class HideEdgeReconnectToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edge-reconnect-tool.selectsource.feedback.hide';
    private hideCreationToolFeedbackCommand: HideEdgeCreationToolFeedbackCommand;

    constructor() {
        super();
        this.hideCreationToolFeedbackCommand = new HideEdgeCreationToolFeedbackCommand();
    }

    execute(context: CommandExecutionContext): SModelRoot {
        this.hideCreationToolFeedbackCommand.execute(context);
        unapplyCssClassesToRoot(context, [EDGE_RECONNECT_SOURCE_CSS_CLASS]);
        return context.root;
    }
}

/**
 * SOURCE AND TARGET MOUSE MOVE LISTENER
 */

export class FeedbackEdgeTargetMovingMouseListener extends FeedbackEdgeEndMovingMouseListener {
    constructor(protected anchorRegistry: AnchorComputerRegistry) {
        super(anchorRegistry);
    }
}

export class FeedbackEdgeSourceMovingMouseListener extends MouseListener {
    constructor(protected anchorRegistry: AnchorComputerRegistry) {
        super();
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        const root = target.root;
        const edgeEnd = root.index.getById(feedbackEdgeEndId(root));
        if (!(edgeEnd instanceof FeedbackEdgeEnd) || !edgeEnd.feedbackEdge) {
            return [];
        }

        const edge = edgeEnd.feedbackEdge;
        const position = getAbsolutePosition(edgeEnd, event);
        const endAtMousePosition = findChildrenAtPosition(target.root, position)
            .find(e => isConnectable(e) && e.canConnect(edge, 'source'));

        if (endAtMousePosition instanceof SConnectableElement && edge.target && isBoundsAware(edge.target)) {
            const anchorComputer = this.anchorRegistry.get(PolylineEdgeRouter.KIND, endAtMousePosition.anchorKind);
            const anchor = anchorComputer.getAnchor(endAtMousePosition, center(edge.target.bounds));
            if (euclideanDistance(anchor, edgeEnd.position) > 1) {
                return [new MoveAction([{ elementId: edgeEnd.id, toPosition: anchor }], false)];
            }
        } else {
            return [new MoveAction([{ elementId: edgeEnd.id, toPosition: position }], false)];
        }

        return [];
    }
}

/**
 * UTILITY FUNCTIONS
 */

function drawFeedbackEdgeSource(context: CommandExecutionContext, targetId: string, elementTypeId: string) {
    const root = context.root;
    const targetChild = root.index.getById(targetId);
    if (!targetChild) {
        return;
    }

    const target = findParentByFeature(targetChild, isConnectable);
    if (!target || !isBoundsAware(target)) {
        return;
    }

    const edgeEnd = new FeedbackEdgeEnd(target.id, elementTypeId);
    edgeEnd.id = feedbackEdgeEndId(root);
    edgeEnd.position = { x: target.bounds.x, y: target.bounds.y };

    const feedbackEdgeSchema = {
        type: 'edge',
        id: feedbackEdgeId(root),
        sourceId: edgeEnd.id,
        targetId: target.id,
        opacity: 0.3
    };

    const feedbackEdge = context.modelFactory.createElement(feedbackEdgeSchema);
    if (isRoutable(feedbackEdge)) {
        edgeEnd.feedbackEdge = feedbackEdge;
        context.root.add(edgeEnd);
        root.add(feedbackEdge);
    }
}
