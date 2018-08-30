import { Operation } from "../../utils/operation";
import { injectable } from "inversify";

export interface OperationService {
    getCurrentOperation(id: string): Operation | undefined;
    setCurrentOperation(id: string, operation: Operation);
    getPreviouslySelectedOperation(id: string);
}


@injectable()
export class OperationServiceImpl implements OperationService {

    private currentCommands: { [id: string]: Operation; } = {};
    private previouslySelectedOperations: { [id: string]: Operation; } = {};

    public setCurrentOperation(id: string, operation: Operation) {
        operation.isActive = true;
        this.previouslySelectedOperations[id] = this.currentCommands[id]
        this.currentCommands[id] = operation

        console.log("Active operation: " + id + "; " + operation.operationKind + "; " + operation.elementTypeId);

    }

    public getCurrentOperation(id: string): Operation | undefined {
        return this.currentCommands[id]
    }


    public getPreviouslySelectedOperation(id: string): Operation | undefined {
        return this.previouslySelectedOperations[id]
    }
}

export const OP_TYPES = {
    OperationService: Symbol.for("OperationService")
}
