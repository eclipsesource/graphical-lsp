import { injectable, inject } from "inversify";
import { MouseListener, Point, SModelElement, Action, SModelRoot, findParentByFeature, isViewport, Viewport } from "sprotty/lib";
import { Operation, OperationKind } from "../../utils/operation";
import { OP_TYPES, OperationService } from "./operation-service";
import { ExecuteNodeCreationToolAction } from "../tool/execute-tool";

/**
 * Tool to create nodes in a Diagram, by selecting a location
 * on the diagram.
 */
@injectable()
export class CreationTool extends MouseListener {

    private target: string;
    private location: Point;
    private container: SModelElement;

    constructor(@inject(OP_TYPES.OperationService) private operationService: OperationService) {
        super();
    }

    createAction(operation: Operation): ExecuteNodeCreationToolAction | undefined {
        if (!operation.elementTypeId) {
            return undefined;
        }
        return new ExecuteNodeCreationToolAction(operation.elementTypeId, this.location)
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        // FIXME Use current diagram ID
        const operation = this.operationService.getCurrentOperation("widget-0");
        if (!operation || !operation.isActive || !operation.elementTypeId || operation.operationKind != OperationKind.CREATE_NODE) {
            return [];
        }
        operation.isActive = false;

        this.container = target;

        this.location = getAbsolutePosition(target, event);

        const action = this.createAction(operation);
        if (action) {
            return [action];
        }

        return []
    }

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
export function getAbsolutePosition(target: SModelElement, mouseEvent: MouseEvent): Point {
    var xPos = mouseEvent.pageX, yPos = mouseEvent.pageY;
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