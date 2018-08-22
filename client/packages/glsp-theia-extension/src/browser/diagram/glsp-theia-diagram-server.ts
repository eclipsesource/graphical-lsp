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
import { TheiaDiagramServer } from "theia-sprotty/lib";
import { ActionHandlerRegistry, SetToolsCommand, RequestToolsAction, SaveModelAction, ActionMessage, SetToolsAction, TYPES, IActionDispatcher, ViewerOptions, SModelStorage, ILogger } from "glsp-sprotty/lib";


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
        registry.registerCommand(SetToolsCommand)
        // register actions
        registry.register(RequestToolsAction.KIND, this)
        registry.register(SaveModelAction.KIND, this)
    }

    messageReceived(message: ActionMessage) {
        if (message.action.kind === SetToolsCommand.KIND) {
            this.handleSetToolsAction(message.action as SetToolsAction)
        }
        super.messageReceived(message)
    }

    handleSetToolsAction(action: SetToolsAction) {
        alert(
            'Handle Tool action'
        )
    }
}

