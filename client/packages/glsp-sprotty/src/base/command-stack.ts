/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { inject, injectable } from "inversify";
import { AnimationFrameSyncer, CommandExecutionContext, CommandResult, CommandStack, CommandStackOptions, ICommand, ILogger, IModelFactory, IViewerProvider, SetModelCommand, SModelRoot, TYPES, UpdateModelCommand } from "sprotty/lib";


export interface CommandStackObserver {
    /*Is called before an update model request from the server is applied*/
    beforeServerUpdate(model: SModelRoot): void
}
@injectable()
export class ObservableCommandStack extends CommandStack {
    protected observers: CommandStackObserver[] = []

    private notifyObservers = false
    public serverSideUpdate: boolean = false
    constructor(@inject(TYPES.IModelFactory) protected modelFactory: IModelFactory,
        @inject(TYPES.IViewerProvider) protected viewerProvider: IViewerProvider,
        @inject(TYPES.ILogger) protected logger: ILogger,
        @inject(TYPES.AnimationFrameSyncer) protected syncer: AnimationFrameSyncer,
        @inject(TYPES.CommandStackOptions) protected options: CommandStackOptions) {
        super(modelFactory, viewerProvider, logger, syncer, options);
    }

    registerObserver(observer: CommandStackObserver): boolean | void {
        if (this.observers.indexOf(observer) < 0) {
            this.observers.push(observer)
            return true
        }
        return false
    }
    deregisterObserver(observer: CommandStackObserver): boolean | void {
        const index = this.observers.indexOf(observer)
        if (index >= 0) {
            this.observers.splice(index, 1)
            return true
        }
        return false
    }
    async update(model: SModelRoot): Promise<void> {
        if (this.viewer === undefined)
            this.viewer = await this.viewerProvider();
        if (this.notifyObservers && this.serverSideUpdate) {
            this.observers.forEach(obs => obs.beforeServerUpdate(model))
            this.notifyObservers = false
            this.serverSideUpdate = false
        }
        this.viewer.update(model);
    }

    protected handleCommand(command: ICommand,
        operation: (context: CommandExecutionContext) => CommandResult,
        beforeResolve: (command: ICommand, context: CommandExecutionContext) => void) {

        if (isObservedCommand) {
            this.notifyObservers = true
        }
        super.handleCommand(command, operation, beforeResolve)
    }

}

function isObservedCommand(command: ICommand) {
    return (command instanceof SetModelCommand || command instanceof UpdateModelCommand)
}