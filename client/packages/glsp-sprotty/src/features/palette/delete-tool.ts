import { injectable, inject } from "inversify";
import { MouseListener, Point, SModelElement, Action, SModelRoot, findParentByFeature, isViewport, Viewport } from "sprotty/lib";
import { Operation, OperationKind } from "../../utils/operation";
import { OP_TYPES, OperationService } from "./operation-service";
import { ExecuteNodeCreationToolAction } from "../tool/execute-tool";

/**
 * Tool to delete elements in a Diagram, by clicking on them
 */
@injectable()
export class DeleteTool extends MouseListener {

    private target: SModelElement;

    constructor(@inject(OP_TYPES.OperationService) private operationService: OperationService) {
        super();
    }

    createAction(operation: Operation): DeleteElementAction {
        return new DeleteElementAction(this.target.id);
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        // FIXME Use current diagram ID
        const operation = this.operationService.getCurrentOperation("widget-0");
        if (!operation || !operation.isActive || operation.operationKind != OperationKind.DELETE_ELEMENT) {
            return [];
        }

        operation.isActive = false;

        if (target instanceof SModelRoot) {
            return [];
        }
        this.target = target;

        const action = this.createAction(operation);
        return [action];
    }

}

export class DeleteElementAction implements Action {
    public static KIND = "executeOperation_delete-node";
    kind = DeleteElementAction.KIND;

    constructor(public elementId: string) { }
}
