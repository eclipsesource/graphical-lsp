/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
import { SelectionService } from "@theia/core";
import { inject, injectable, optional } from "inversify";
import { Action, ActionHandlerRegistry, IActionHandler, IActionHandlerInitializer, SelectAction, SelectCommand } from "glsp-sprotty/lib";

class SprottySelectionForwardingActionHandler implements IActionHandler {

    constructor(readonly selectionService: SelectionService) { }

    handle(action: Action): void {
        if (action.kind === SelectCommand.KIND) {
            let selectAction = action as SelectAction
            this.selectionService.selection = selectAction.selectedElementsIDs
        }
    }
}

@injectable()
export class SprottySelectionForwardingInitializer implements IActionHandlerInitializer {

    @inject(SelectionService) @optional() protected readonly selectionService: SelectionService;

    initialize(registry: ActionHandlerRegistry): void {
        const selectionPropagatingHandler = new SprottySelectionForwardingActionHandler(this.selectionService);
        registry.register(SelectCommand.KIND, selectionPropagatingHandler);
    }
}
