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
import { ActionMessage, Tool, ActionHandlerRegistry, SetToolsCommand, RequestToolsAction, SaveModelAction, SetToolsAction, TYPES, IActionDispatcher, ViewerOptions, SModelStorage, ILogger, ToolType, Action, ExecuteNodeCreationToolAction, ExecuteToolAction, alignFeature } from "glsp-sprotty/lib";
import { MenuModelRegistry, CommandRegistry, SelectionService, Command } from "@theia/core/lib/common";
import { UriCommandHandler, UriAwareCommandHandler } from "@theia/core/lib/common/uri-command-handler";
import URI from "@theia/core/lib/common/uri";
import { EDITOR_CONTEXT_MENU } from "@theia/editor/lib/browser";





@injectable()
export class GLSPTheiaDiagramServer extends TheiaDiagramServer {


    constructor(@inject(TYPES.IActionDispatcher) public actionDispatcher: IActionDispatcher,
        @inject(TYPES.ActionHandlerRegistry) actionHandlerRegistry: ActionHandlerRegistry,
        @inject(TYPES.ViewerOptions) viewerOptions: ViewerOptions,
        @inject(TYPES.SModelStorage) storage: SModelStorage,
        @inject(TYPES.ILogger) logger: ILogger
        // @inject(MenuModelRegistry) private readonly menuRegistry: MenuModelRegistry
        // @inject(CommandRegistry) private readonly commandRegistry: CommandRegistry,
        // @inject(SelectionService) protected readonly selectionService: SelectionService,
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
        super.messageReceived(message)
    }

}
