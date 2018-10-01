/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { injectable } from "inversify";
import { Operation } from "../../utils/operation";

export interface OperationService {
    getCurrentOperation(id: string): Operation | undefined;
    setCurrentOperation(id: string, operation: Operation): void;
    getPreviouslySelectedOperation(id: string): Operation | undefined;
}


@injectable()
export class OperationServiceImpl implements OperationService {

    private currentCommands: { [id: string]: Operation; } = {};
    private previouslySelectedOperations: { [id: string]: Operation; } = {};

    public setCurrentOperation(id: string, operation: Operation) {
        operation.isActive = true;
        this.previouslySelectedOperations[id] = this.currentCommands[id]
        this.currentCommands[id] = operation
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
