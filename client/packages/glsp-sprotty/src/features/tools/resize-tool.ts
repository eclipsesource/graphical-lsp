/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Martin Fleck - initial API and implementation
 ******************************************************************************/
import { inject, injectable } from "inversify";
import {
    Action, ActionDispatcher, ElementAndBounds, findParentByFeature, isViewport, KeyTool, MouseListener, MouseTool, // 
    SetBoundsAction, SModelElement, TYPES, Viewport, ViewportAction
} from "sprotty/lib";
import { forEachElement, hasSelectedElements, isSelectedBoundsAware } from "../../lib/utils/smodel-util";
import { ChangeBoundsOperationAction } from "../operation/operation-actions";
import { Tool } from "../tool-manager/tool";
import { ExtendedKeyListener } from "./key-tool";


/**
 * A tool that allows to resize elements and is optimized for Client/Server operation.
 * Once a resize is peformed, a `ChangeBoundsAction` is sent to the server only at the end of the interaction.
 * Specifically, this tool supports resizing through mouse wheel and keyboard interaction.
 * On the client side we use `SetBoundsAction` to trigger a re-rendering.
 */
@injectable()
export class ResizeTool implements Tool {
    static ID = "glsp.resizetool";
    readonly id = ResizeTool.ID;

    protected resizeListener: ResizeListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool,
        @inject(KeyTool) protected keyTool: KeyTool,
        @inject(TYPES.IActionDispatcher) protected actionDispatcher: ActionDispatcher) { }

    enable() {
        this.resizeListener = new ResizeListener(this.actionDispatcher);
        this.mouseTool.register(this.resizeListener);
        this.keyTool.register(this.resizeListener);
    }

    disable() {
        this.mouseTool.deregister(this.resizeListener);
        this.keyTool.deregister(this.resizeListener);
    }
}

class ResizeListener extends MouseListener implements ExtendedKeyListener {
    private updateServerTimer: NodeJS.Timer;

    constructor(protected actionDispatcher: ActionDispatcher) {
        super();
    }

    /**
     * Resize Support for Mouse Wheel:
     * Send updates to server with delay so that we can smoothly resize on client side.
     */

    wheel(target: SModelElement, event: WheelEvent): Action[] {
        if (isResizeWheelEvent(event) && hasSelectedElements(target)) {
            this.updateServer(target, 300);
            if (event.deltaY > 0) {
                return handleElementResize(target, resize_grow, clientAction);
            }
            return handleElementResize(target, resize_shrink, clientAction);
        }
        return handleViewportZoom(target, event);
    }

    updateServer(target: SModelElement, delay: number) {
        if (this.updateServerTimer) {
            // cancel pending update
            clearTimeout(this.updateServerTimer);
        }
        this.updateServerTimer = setTimeout(function (actionDispatcher: ActionDispatcher) {
            // no resizing, simply send the latest data to the server
            actionDispatcher.dispatchAll(handleElementResize(target, no_resize, serverAction))
        }, delay, this.actionDispatcher);
    }

    /**
     * Resize Support for Keys:
     * Send updates to server on keyUp so that we can smoothly resize on client side with keyDown.
     */

    keyDown(target: SModelElement, event: KeyboardEvent): Action[] {
        if (isGrowKeyEvent(event)) {
            return handleElementResize(target, resize_grow, clientAction);
        }
        if (isShrinkKeyEvent(event)) {
            return handleElementResize(target, resize_shrink, clientAction);
        }
        return [];
    }

    keyUp(target: SModelElement, event: KeyboardEvent): Action[] {
        if (isResizeKeyEvent(event)) {
            // no resizing, simply send the latest data to the server
            return handleElementResize(target, no_resize, serverAction);
        }
        return [];
    }
}

const no_resize: number = 1;
const resize_grow: number = 1.1;
const resize_shrink: number = Math.pow(resize_grow, -1);
const clientAction = SetBoundsAction;
const serverAction = ChangeBoundsOperationAction;

function isResizeWheelEvent(event: WheelEvent) {
    return event.altKey;
}

function isResizeKeyEvent(event: KeyboardEvent) {
    return isGrowKeyEvent(event) || isShrinkKeyEvent(event);
}

function isGrowKeyEvent(event: KeyboardEvent) {
    return event.altKey && event.key === '+';
}

function isShrinkKeyEvent(event: KeyboardEvent) {
    return event.altKey && event.key === '-';
}

function handleElementResize(target: SModelElement, factor: number, ...ActionClassses: (new (bounds: ElementAndBounds[]) => Action)[]): Action[] {
    const newBounds: ElementAndBounds[] = [];
    forEachElement(target, isSelectedBoundsAware, element => newBounds.push({
        elementId: element.id,
        newBounds: {
            x: element.bounds.x,
            y: element.bounds.y,
            width: element.bounds.width * factor,
            height: element.bounds.height * factor
        }
    }));
    const actions: Action[] = [];
    ActionClassses.forEach(ActionClasss => actions.push(new ActionClasss(newBounds)));
    return actions;
}

// same functionality as the ZoomMouseListener from Sprotty
function handleViewportZoom(target: SModelElement, event: WheelEvent): Action[] {
    const viewport = findParentByFeature(target, isViewport);
    if (viewport) {
        const newZoom = Math.exp(-event.deltaY * 0.005);
        const factor = 1. / (newZoom * viewport.zoom) - 1. / viewport.zoom;
        const newViewport: Viewport = {
            scroll: {
                x: -(factor * event.offsetX - viewport.scroll.x),
                y: -(factor * event.offsetY - viewport.scroll.y)
            },
            zoom: viewport.zoom * newZoom
        };
        return [new ViewportAction(viewport.id, newViewport, false)];
    }
    return [];
}