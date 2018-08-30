import { MouseListener, SModelElement, Action, Point, manhattanDistance, MoveAction, ElementMove, isMoveable, findParentByFeature, isViewport, Locateable } from "sprotty/lib";
import { injectable, inject } from "inversify";
import { OperationService, OP_TYPES } from "../palette/operation-service";
import { Operation, OperationKind } from "../../utils/operation";

/**
 * A custom Move Tool that is optimized for Client/Server operation
 * A MoveAction is sent to the server only at the end of the interaction
 * (Whereas the default Sprotty version is a simple client-side live update)
 */

@injectable()
export class MoveTool extends MouseListener {

    private isMouseDown: boolean;
    private initialLocation: Point;
    private initialElementLocation: Point;
    private isStillSincePress: boolean;
    private element: SModelElement & Locateable;

    constructor(@inject(OP_TYPES.OperationService) private operationService: OperationService) {
        super();
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        this.isStillSincePress = true;
        const elementToMove = findParentByFeature(target, isMoveable);
        if (elementToMove && isMoveable(elementToMove)) {
            this.isMouseDown = true;
            this.initialLocation = event;
            this.element = elementToMove as SModelElement & Locateable;
            this.initialElementLocation = {
                x: this.element.position.x,
                y: this.element.position.y
            };
        }
        return [];
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        if (this.isMouseDown) {
            var distance = manhattanDistance(this.initialLocation, event);
            if (distance > 3) {
                this.isStillSincePress = false;
            }
        }
        return [];
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        this.isMouseDown = false;
        if (this.isStillSincePress) {
            return [];
        }

        const viewport = findParentByFeature(target, isViewport);
        const zoom = viewport ? viewport.zoom : 1;
        const dx = (event.pageX - this.initialLocation.x) / zoom;
        const dy = (event.pageY - this.initialLocation.y) / zoom;

        const moveAction = new MoveElementAction(this.element.id, {
            x: this.initialElementLocation.x + dx,
            y: this.initialElementLocation.y + dy
        });

        return [moveAction];
    }

}

export class MoveElementAction implements Action {

    public static readonly KIND = "executeOperation_move";
    public kind = MoveElementAction.KIND;
    public targetContainerId?: string;

    constructor(public elementId: string, public location: Point) { }

}
