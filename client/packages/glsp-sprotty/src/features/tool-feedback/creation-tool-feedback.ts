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
import { CommandResult } from "sprotty/lib";
import { FeedbackCommand } from "./model";
import { MouseListener } from "sprotty/lib";
import { MoveAction } from "sprotty/lib";
import { PolylineEdgeRouter } from "sprotty/lib";
import { SChildElement } from "sprotty/lib";
import { SConnectableElement } from "sprotty/lib";
import { SDanglingAnchor } from "sprotty/lib";
import { SModelElement } from "sprotty/lib";
import { SModelRoot } from "sprotty/lib";
import { SRoutableElement } from "sprotty/lib";
import { TYPES } from "sprotty/lib";

import { addCssClasses } from "../../utils/smodel-util";
import { center } from "sprotty/lib";
import { euclideanDistance } from "sprotty/lib";
import { findChildrenAtPosition } from "sprotty/lib";
import { findParentByFeature } from "sprotty/lib";
import { getAbsolutePosition } from "../../utils/viewpoint-util";
import { inject } from "inversify";
import { injectable } from "inversify";
import { isBoundsAware } from "sprotty/lib";
import { isConnectable } from "sprotty/lib";
import { isRoutable } from "../reconnect/model";
import { removeCssClasses } from "../../utils/smodel-util";

const NODE_CREATION_CSS_CLASS = 'node-creation-tool-mode';

export class ShowNodeCreationToolFeedbackAction implements Action {
    kind = ShowNodeCreationToolFeedbackCommand.KIND;
    constructor(readonly elementTypeId: string) { }
}

@injectable()
export class ShowNodeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.nodecreationtool.feedback.show';

    execute(context: CommandExecutionContext): CommandResult {
        addCssClasses(context.root, [NODE_CREATION_CSS_CLASS]);
        return context.root
    }
}

export class HideNodeCreationToolFeedbackAction implements Action {
    kind = HideNodeCreationToolFeedbackCommand.KIND;
    constructor(readonly elementTypeId: string) { }
}

@injectable()
export class HideNodeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.nodecreationtool.feedback.hide';

    execute(context: CommandExecutionContext): CommandResult {
        removeCssClasses(context.root, [NODE_CREATION_CSS_CLASS])
        return context.root;
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

@injectable()
export class ShowEdgeCreationSelectSourceFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.selectsource.feedback.show';

    execute(context: CommandExecutionContext): CommandResult {
        removeCssClasses(context.root, [EDGE_CREATION_TARGET_CSS_CLASS]);
        addCssClasses(context.root, [EDGE_CREATION_SOURCE_CSS_CLASS]);
        return context.root;
    }
}

export class FeedbackEdgeEnd extends SDanglingAnchor {
    static readonly TYPE = 'feedback-edge-end';
    type = FeedbackEdgeEnd.TYPE;
    constructor(readonly sourceId: string,
        readonly elementTypeId: string,
        public feedbackEdge: SRoutableElement | undefined = undefined) {
        super();
    }
}

@injectable()
export class ShowEdgeCreationSelectTargetFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.selecttarget.feedback.show';

    constructor(@inject(TYPES.Action) protected action: ShowEdgeCreationSelectTargetFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): CommandResult {
        drawFeedbackEdge(context, this.action.sourceId, this.action.elementTypeId);
        removeCssClasses(context.root, [EDGE_CREATION_SOURCE_CSS_CLASS]);
        addCssClasses(context.root, [EDGE_CREATION_TARGET_CSS_CLASS]);
        return context.root;
    }
}

@injectable()
export class HideEdgeCreationToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edgecreationtool.feedback.hide';

    execute(context: CommandExecutionContext): CommandResult {
        removeFeedbackEdge(context.root);
        removeCssClasses(context.root, [EDGE_CREATION_SOURCE_CSS_CLASS, EDGE_CREATION_TARGET_CSS_CLASS]);
        return context.root;
    }
}

export class FeedbackEdgeEndMovingMouseListener extends MouseListener {
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
            .find(e => isConnectable(e) && e.canConnect(edge, 'target'));

        if (endAtMousePosition instanceof SConnectableElement && edge.source && isBoundsAware(edge.source)) {
            const anchorComputer = this.anchorRegistry.get(PolylineEdgeRouter.KIND, endAtMousePosition.anchorKind);
            const anchor = anchorComputer.getAnchor(endAtMousePosition, center(edge.source.bounds));
            if (euclideanDistance(anchor, edgeEnd.position) > 1) {
                return [new MoveAction([{ elementId: edgeEnd.id, toPosition: anchor }], false)];
            }
        } else {
            return [new MoveAction([{ elementId: edgeEnd.id, toPosition: position }], false)];
        }

        return [];
    }
}

export function feedbackEdgeId(root: SModelRoot): string {
    return root.id + '_feedback_edge';
}

export function feedbackEdgeEndId(root: SModelRoot): string {
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
        root.add(edgeEnd);
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
