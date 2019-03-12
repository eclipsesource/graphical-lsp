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
import { EdgeRouterRegistry } from "sprotty/lib";
import { ElementMove } from "sprotty/lib";
import { FeedbackCommand } from "./model";
import { FeedbackEdgeEnd } from "./creation-tool-feedback";
import { FeedbackEdgeEndMovingMouseListener } from "./creation-tool-feedback";
import { HideEdgeCreationToolFeedbackCommand } from "./creation-tool-feedback";
import { IModelFactory } from "sprotty/lib";
import { MouseListener } from "sprotty/lib";
import { MoveAction } from "sprotty/lib";
import { Point } from "sprotty/lib";
import { PolylineEdgeRouter } from "sprotty/lib";
import { SConnectableElement } from "sprotty/lib";
import { ShowEdgeCreationSelectTargetFeedbackAction } from "./creation-tool-feedback";
import { ShowEdgeCreationSelectTargetFeedbackCommand } from "./creation-tool-feedback";
import { SModelElement } from "sprotty/lib";
import { SModelRoot } from "sprotty/lib";
import { SRoutingHandle } from "sprotty/lib";
import { SwitchEditModeAction } from "sprotty/lib";
import { TYPES } from "sprotty/lib";
import { VNode } from "snabbdom/vnode";

import { addCssClasses } from "../../utils/smodel-util";
import { addReconnectHandles } from "../reconnect/model";
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
import { isRoutingHandle } from "../reconnect/model";
import { isSelected } from "../../utils/smodel-util";
import { isViewport } from "sprotty/lib";
import { removeCssClasses } from "../../utils/smodel-util";
import { removeReconnectHandles } from "../reconnect/model";

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
    static readonly KIND = 'glsp.edge-edit-tool.handles.feedback.show';

    constructor(@inject(TYPES.Action) protected action: ShowEdgeReconnectHandlesFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): CommandResult {
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
    static readonly KIND = 'glsp.edge-edit-tool.handles.feedback.hide';

    constructor(@inject(TYPES.Action) protected action: HideEdgeReconnectHandlesFeedbackAction) {
        super();
    }

    execute(context: CommandExecutionContext): CommandResult {
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

const EDGE_RECONNECT_SOURCE_CSS_CLASS = 'edge-edit-select-source-mode';

@injectable()
export class ShowEdgeReconnectSelectSourceFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edge-edit-tool.selectsource.feedback.show';

    constructor(@inject(TYPES.Action) protected action: ShowEdgeReconnectSelectSourceFeedbackAction,
        @inject(TYPES.IModelFactory) protected modelFactory: IModelFactory) {
        super();
    }


    execute(context: CommandExecutionContext): CommandResult {
        drawFeedbackEdgeSource(context.root, this.modelFactory, this.action.targetId, this.action.elementTypeId);
        addCssClasses(context.root, [EDGE_RECONNECT_SOURCE_CSS_CLASS]);
        return context.root;
    }
}

@injectable()
export class HideEdgeReconnectToolFeedbackCommand extends FeedbackCommand {
    static readonly KIND = 'glsp.edge-edit-tool.selectsource.feedback.hide';
    private hideCreationToolFeedbackCommand: HideEdgeCreationToolFeedbackCommand;

    constructor() {
        super();
        this.hideCreationToolFeedbackCommand = new HideEdgeCreationToolFeedbackCommand();
    }

    execute(context: CommandExecutionContext): CommandResult {
        this.hideCreationToolFeedbackCommand.execute(context);
        removeCssClasses(context.root, [EDGE_RECONNECT_SOURCE_CSS_CLASS])
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


export class FeedbackEdgeRouteMovingMouseListener extends MouseListener {
    hasDragged = false;
    lastDragPosition: Point | undefined;

    constructor(protected edgeRouterRegistry?: EdgeRouterRegistry) {
        super();
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        if (event.button === 0) {
            const routingHandle = findParentByFeature(target, isRoutingHandle);
            if (routingHandle !== undefined) {
                result.push(new SwitchEditModeAction([target.id], []));
                this.lastDragPosition = { x: event.pageX, y: event.pageY };
            } else {
                this.lastDragPosition = undefined;
            }
            this.hasDragged = false;
        }
        return result;
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        const result: Action[] = [];
        if (event.buttons === 0)
            this.mouseUp(target, event);
        else if (this.lastDragPosition) {
            const viewport = findParentByFeature(target, isViewport);
            this.hasDragged = true;
            const zoom = viewport ? viewport.zoom : 1;
            const dx = (event.pageX - this.lastDragPosition.x) / zoom;
            const dy = (event.pageY - this.lastDragPosition.y) / zoom;
            const handleMoves: ElementMove[] = [];
            target.root.index.all()
                .filter(element => isSelected(element))
                .forEach(element => {
                    if (isRoutingHandle(element)) {
                        const point = this.getHandlePosition(element);
                        if (point !== undefined) {
                            handleMoves.push({
                                elementId: element.id,
                                fromPosition: point,
                                toPosition: {
                                    x: point.x + dx,
                                    y: point.y + dy
                                }
                            });
                        }
                    }
                });
            this.lastDragPosition = { x: event.pageX, y: event.pageY };
            if (handleMoves.length > 0)
                result.push(new MoveAction(handleMoves, false));
        }
        return result;
    }

    protected getHandlePosition(handle: SRoutingHandle): Point | undefined {
        if (this.edgeRouterRegistry) {
            const parent = handle.parent;
            if (!isRoutable(parent))
                return undefined;
            const router = this.edgeRouterRegistry.get(parent.routerKind);
            const route = router.route(parent);
            return router.getHandlePosition(parent, route, handle);
        }
        return undefined;
    }

    mouseEnter(target: SModelElement, event: MouseEvent): Action[] {
        if (target instanceof SModelRoot && event.buttons === 0)
            this.mouseUp(target, event);
        return [];
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        this.hasDragged = false;
        this.lastDragPosition = undefined;
        return [];
    }

    decorate(vnode: VNode, element: SModelElement): VNode {
        return vnode;
    }
}

/**
 * UTILITY FUNCTIONS
 */

function drawFeedbackEdgeSource(root: SModelRoot, modelFactory: IModelFactory, targetId: string, elementTypeId: string) {
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

    const feedbackEdge = modelFactory.createElement(feedbackEdgeSchema);
    if (isRoutable(feedbackEdge)) {
        edgeEnd.feedbackEdge = feedbackEdge;
        root.add(edgeEnd);
        root.add(feedbackEdge);
    }
}
