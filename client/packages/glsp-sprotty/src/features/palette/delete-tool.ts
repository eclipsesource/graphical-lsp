/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
import { inject, injectable } from "inversify";
import { Action, MouseListener, SModelElement, SModelRoot } from "sprotty/lib";
import { Operation, OperationKind } from "../../utils/operation";
import { OperationService, OP_TYPES } from "./operation-service";

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
