/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { injectable, inject } from "inversify";
import { TheiaDiagramServer } from "theia-glsp/lib";
import { ActionMessage, ActionHandlerRegistry, SaveModelAction, TYPES, IActionDispatcher, ViewerOptions, SModelStorage, ILogger, Action, ExecuteNodeCreationToolAction, ExecuteToolAction, alignFeature, CreateConnectionAction, MoveAction, MoveCommand, SetOperationsCommand, RequestOperationsAction, DeleteElementAction, MoveElementAction, SwitchEditModeAction, SwitchEditModeCommand } from "glsp-sprotty/lib";






@injectable()
export class GLSPTheiaDiagramServer extends TheiaDiagramServer {


    constructor(@inject(TYPES.IActionDispatcher) public actionDispatcher: IActionDispatcher,
        @inject(TYPES.ActionHandlerRegistry) actionHandlerRegistry: ActionHandlerRegistry,
        @inject(TYPES.ViewerOptions) viewerOptions: ViewerOptions,
        @inject(TYPES.SModelStorage) storage: SModelStorage,
        @inject(TYPES.ILogger) logger: ILogger
    ) {
        super(actionDispatcher, actionHandlerRegistry, viewerOptions, storage, logger)
    }
    initialize(registry: ActionHandlerRegistry): void {
        super.initialize(registry);
        //register commads
        registry.registerCommand(SetOperationsCommand)
        // register actions
        registry.register(RequestOperationsAction.KIND, this)
        registry.register(SaveModelAction.KIND, this)
        registry.register(ExecuteNodeCreationToolAction.KIND, this)
        registry.register(CreateConnectionAction.KIND, this)
        registry.register(MoveElementAction.KIND, this)
        registry.register(DeleteElementAction.KIND, this)
        // Register an empty handler for SwitchEditMode, to avoid runtime exceptions.
        // We don't want to support SwitchEditMode, but sprotty still sends some corresponding
        // actions.
        registry.register(SwitchEditModeCommand.KIND, { handle: action => undefined })
    }

    messageReceived(message: ActionMessage) {
        super.messageReceived(message)
    }

    public getSourceURI(): string {
        return this.sourceUri
    }



}
