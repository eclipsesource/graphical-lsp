/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Philip Langer - initial API and implementation
 ******************************************************************************/

import {
    Action, center, CommandExecutionContext, euclideanDistance, findChildrenAtPosition, //
    findParentByFeature, isBoundsAware, isConnectable, isRoutable, MouseListener, MoveAction, //
    Routable, SChildElement, SConnectableElement, SDanglingAnchor, SModelElement, SModelRoot
} from "sprotty/lib";
import { getAbsolutePosition } from "../../lib/utils/viewpoint-util";
import { applyCssClassesToRoot, FeedbackCommand, unapplyCssClassesToRoot } from "./model";

const NODE_CREATION_CSS_CLASS = 'node-creation-tool-mode';

export class ShowNodeCreationToolFeedbackAction implements Action {
    kind = ShowNodeCreationToolFeedbackCommand.KIND;
    constructor(readonly elementTypeId: string) { }
}

export class HideNodeCreationToolFeedbackAction implements Action {
    kind = HideNodeCreationToolFeedbackCommand.KIND;
    constructor(readonly elementTypeId: string) { }
}

export class ShowNodeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.nodecreationtool.feedback.show';
    execute(context: CommandExecutionContext): SModelRoot {
        return applyCssClassesToRoot(context, [NODE_CREATION_CSS_CLASS]);
    }
}

export class HideNodeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.nodecreationtool.feedback.hide';
    execute(context: CommandExecutionContext): SModelRoot {
        return unapplyCssClassesToRoot(context, [NODE_CREATION_CSS_CLASS]);
    }
}

const EDGE_CREATION_SOURCE_CSS_CLASS = 'edge-creation-select-source-mode';
const EDGE_CREATION_TARGET_CSS_CLASS = 'edge-creation-select-target-mode';

export class ShowEdgeCreationSelectSourceFeedbackAction implements Action {
    kind = ShowEdgeCreationSelectSourceFeedbackCommand.KIND;
    constructor(readonly elementTypeId: string) { }
}

export class ShowEdgeCreationSelectTargetFeedbackAction implements Action {
    kind = ShowEdgeCreationSelectTargetFeedbackCommand.KIND;
    constructor(readonly elementTypeId: string, readonly sourceId: string) { }
}

export class HideEdgeCreationToolFeedbackAction implements Action {
    kind = HideEdgeCreationToolFeedbackCommand.KIND;
    constructor(readonly elementTypeId: string) { }
}

export class ShowEdgeCreationSelectSourceFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.selectsource.feedback.show';
    execute(context: CommandExecutionContext): SModelRoot {
        unapplyCssClassesToRoot(context, [EDGE_CREATION_TARGET_CSS_CLASS]);
        return applyCssClassesToRoot(context, [EDGE_CREATION_SOURCE_CSS_CLASS]);
    }
}

export class FeedbackEdgeEnd extends SDanglingAnchor {
    constructor(readonly sourceId: string,
        readonly elementTypeId: string,
        public feedbackEdge: Routable | undefined = undefined) {
        super();
    }
}

export class ShowEdgeCreationSelectTargetFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.selecttarget.feedback.show';

    constructor(protected action: ShowEdgeCreationSelectTargetFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): SModelRoot {
        drawFeedbackEdge(context, this.action.sourceId, this.action.elementTypeId);
        unapplyCssClassesToRoot(context, [EDGE_CREATION_SOURCE_CSS_CLASS]);
        return applyCssClassesToRoot(context, [EDGE_CREATION_TARGET_CSS_CLASS]);
    }
}

export class HideEdgeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.feedback.hide';
    execute(context: CommandExecutionContext): SModelRoot {
        removeFeedbackEdge(context.root);
        return unapplyCssClassesToRoot(context, [EDGE_CREATION_SOURCE_CSS_CLASS, EDGE_CREATION_TARGET_CSS_CLASS]);
    }
}

export class FeedbackEdgeEndMovingMouseListener extends MouseListener {
    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        const root = target.root;
        const edgeEnd = root.index.getById(feedbackEdgeEndId(root));
        if (!(edgeEnd instanceof FeedbackEdgeEnd) || !edgeEnd.feedbackEdge) {
            return [];
        }

        const edge = edgeEnd.feedbackEdge;
        const position = getAbsolutePosition(edgeEnd, event);
        const endAtMousePosition = findChildrenAtPosition(target.root, position)
            .find(e => isConnectable(e) && e.canConnect(edge, 'target'));

        if (endAtMousePosition instanceof SConnectableElement && edge.source && isBoundsAware(edge.source)) {
            const anchor = endAtMousePosition.getAnchor(center(edge.source.bounds));
            if (euclideanDistance(anchor, edgeEnd.position) > 1) {
                return [new MoveAction([{ elementId: edgeEnd.id, toPosition: anchor }], false)];
            }
        } else {
            return [new MoveAction([{ elementId: edgeEnd.id, toPosition: position }], false)];
        }

        return [];
    }
}

function feedbackEdgeId(root: SModelRoot): string {
    return root.id + '_feedback_edge';
}

function feedbackEdgeEndId(root: SModelRoot): string {
    return root.id + '_feedback_anchor';
}

function drawFeedbackEdge(context: CommandExecutionContext, sourceId: string, elementTypeId: string) {
    const root = context.root;
    const sourceChild = root.index.getById(sourceId);
    if (!sourceChild) {
        return;
    }

    const source = findParentByFeature(sourceChild, isConnectable);
    if (!source || !isBoundsAware(source)) {
        return;
    }

    // TODO we need a view for SDanglingAnchor, otherwise MissingView will render a red string ?<id>?
    const edgeEnd = new FeedbackEdgeEnd(source.id, elementTypeId);
    edgeEnd.id = feedbackEdgeEndId(root);
    edgeEnd.position = { x: source.bounds.x, y: source.bounds.y };

    const feedbackEdgeSchema = {
        type: 'edge',
        id: feedbackEdgeId(root),
        sourceId: source.id,
        targetId: edgeEnd.id,
        opacity: 0.3
    };

    const feedbackEdge = context.modelFactory.createElement(feedbackEdgeSchema);
    if (isRoutable(feedbackEdge)) {
        edgeEnd.feedbackEdge = feedbackEdge;
        context.root.add(edgeEnd);
        root.add(feedbackEdge);
    }
}

function removeFeedbackEdge(root: SModelRoot) {
    const feedbackEdge = root.index.getById(feedbackEdgeId(root));
    const feedbackEdgeEnd = root.index.getById(feedbackEdgeEndId(root));
    if (feedbackEdge instanceof SChildElement)
        root.remove(feedbackEdge);
    if (feedbackEdgeEnd instanceof SChildElement)
        root.remove(feedbackEdgeEnd);
}
