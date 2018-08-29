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
import { ActionMessage, Tool, ActionHandlerRegistry, SetToolsCommand, RequestToolsAction, SaveModelAction, SetToolsAction, TYPES, IActionDispatcher, ViewerOptions, SModelStorage, ILogger, ToolType, Action, ExecuteNodeCreationToolAction, ExecuteToolAction, alignFeature, CreateConnectionAction, MoveAction, MoveCommand } from "glsp-sprotty/lib";
import { MenuModelRegistry, CommandRegistry, SelectionService, Command } from "@theia/core/lib/common";
import { UriCommandHandler, UriAwareCommandHandler } from "@theia/core/lib/common/uri-command-handler";
import URI from "@theia/core/lib/common/uri";
import { EDITOR_CONTEXT_MENU } from "@theia/editor/lib/browser";
import { ActionMessage, ActionHandlerRegistry, SaveModelAction, TYPES, IActionDispatcher, ViewerOptions, SModelStorage, ILogger, Action, ExecuteNodeCreationToolAction, ExecuteToolAction, alignFeature, CreateConnectionAction, SetOperationsCommand, RequestOperationsAction } from "glsp-sprotty/lib";





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
        registry.register("executeOperation_move", this)
    }

    messageReceived(message: ActionMessage) {
        super.messageReceived(message)
    }

    public getSourceURI(): string {
        return this.sourceUri
    }



}
