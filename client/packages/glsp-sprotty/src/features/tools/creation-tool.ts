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
import { Action, findParentByFeature, isCtrlOrCmd, isViewport, MouseListener, MouseTool, Point, SModelElement, SModelRoot, Viewport } from "sprotty/lib";
import { ExecuteNodeCreationToolAction } from "./execute-tool";
import { EnableStandardToolsAction, Tool } from "./tool-manager";

@injectable()
export class NodeCreationTool implements Tool {

    static ID = "glsp.nodecreationtool";
    public elementTypeId: string = "unknown";
    protected creationToolMouseListener: NodeCreationToolMouseListener;

    constructor(@inject(MouseTool) protected mouseTool: MouseTool) { }

    get id() {
        return `${NodeCreationTool.ID}.${this.elementTypeId}`;
    };

    enable() {
        this.creationToolMouseListener = new NodeCreationToolMouseListener(this.elementTypeId);
        this.mouseTool.register(this.creationToolMouseListener);
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
    }

}

@injectable()
export class NodeCreationToolMouseListener extends MouseListener {

    constructor(protected elementTypeId: string) {
        super();
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        const location = getAbsolutePosition1(target, event);
        const containerId: string | undefined = target instanceof SModelRoot ? undefined : target.id;
        const result: Action[] = [];
        result.push(new ExecuteNodeCreationToolAction(this.elementTypeId, location, containerId));
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

    constructor(@inject(MouseTool) protected mouseTool: MouseTool) { }

    get id() {
        return `${EdgeCreationTool.ID}.${this.elementTypeId}`;
    };

    enable() {
        this.creationToolMouseListener = new EdgeCreationToolMouseListener(this.elementTypeId);
        this.mouseTool.register(this.creationToolMouseListener);
    }

    disable() {
        this.mouseTool.deregister(this.creationToolMouseListener);
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
        } else {
            this.target = target.id;
            if (this.source != null && this.target != null) {
                result.push(new CreateConnectionAction(this.elementTypeId, this.source, this.target));
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

export class CreateConnectionAction implements Action {

    static readonly KIND = 'executeOperation_create-connection'
    readonly kind = CreateConnectionAction.KIND

    constructor(public readonly elementTypeId: string,
        public readonly sourceElementId?: string,
        public readonly targetElementId?: string) { }
}

/**
 * Return the position corresponding to this mouse event (Browser coordinates)
 * in the diagram coordinates system (i.e. relative to the Diagram's 0;0 point)
 *
 * This functions takes into account the following transformations:
 * - Location of the Diagram Canvas inside of the browser's page
 * - Current viewport Scroll and Zoom
 *
 * @param target
 *  An element from the diagram
 * @param mouseEvent
 *  A mouseEvent
 */
export function getAbsolutePosition1(target: SModelElement, mouseEvent: MouseEvent): Point {
    let xPos = mouseEvent.pageX, yPos = mouseEvent.pageY;
    const canvasBounds = target.root.canvasBounds;
    xPos -= canvasBounds.x;
    yPos -= canvasBounds.y;

    const viewport: Viewport | undefined = findParentByFeature(target, isViewport);
    const zoom = viewport ? viewport.zoom : 1;
    if (viewport) {
        const scroll: Point = { x: viewport.scroll.x, y: viewport.scroll.y }
        xPos += scroll.x * zoom;
        yPos += scroll.y * zoom;

        xPos /= zoom;
        yPos /= zoom;
    }
    xPos

    return {
        x: xPos,
        y: yPos
    }
}
