import { Action, MouseListener, SModelElement } from "sprotty/lib";
import { inject, injectable } from "inversify";
import { OperationService, OP_TYPES } from "./operation-service";
import { OperationKind, Operation } from "../../utils/operation";

/**
 * Tool to create connections in a Diagram, by selecting a source and target node
 */
@injectable()
export class ConnectionTool extends MouseListener {

    private source?: string;
    private target?: string;

    private isMouseDown: boolean = false;
    private isMouseMove: boolean;
    private operation: Operation;

    constructor(@inject(OP_TYPES.OperationService) private operationService: OperationService) {
        super();
    }

    createAction(): CreateConnectionAction | undefined {
        if (!this.operation.elementTypeId) {
            return undefined;
        }
        return new CreateConnectionAction(this.operation.elementTypeId, this.source, this.target)
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {

        // FIXME Retrieve the current widget ID
        var operation = this.operationService.getCurrentOperation("widget-0");
        if (!operation || operation.operationKind != OperationKind.CREATE_CONNECTION || !operation.isActive) {
            return [];
        }

        this.operation = operation;
        this.isMouseDown = true;
        return [];
    }

    mouseMove(target: SModelElement, event: MouseEvent): Action[] {
        if (this.isMouseDown) {
            // Detect that the mouse moved while the button was pressed
            // In that case, we're dragging something, and shouldn't create
            // a connection
            this.isMouseMove = true;
            this.operation.isActive = false;
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

        if (this.operation && this.operation.isActive) {
            if (this.source == null) {
                this.source = target.id;
            } else {
                this.target = target.id;
                if (this.source != null && this.target != null) {
                    var result = this.createAction()
                    this.source = undefined;
                    this.target = undefined;

                    this.operation.isActive = false;
                    if (result) {
                        return [result];
                    }
                }
            }
        }

        return []
    }
}

export class CreateConnectionAction implements Action {

    static readonly KIND = 'executeOperation_create-connection'
    readonly kind = CreateConnectionAction.KIND

    constructor(public readonly elementTypeId: string,
        public readonly sourceElementId?: string,
        public readonly targetElementId?: string) { }
}
