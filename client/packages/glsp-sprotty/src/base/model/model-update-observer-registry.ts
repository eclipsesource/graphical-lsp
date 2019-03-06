/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
import { inject, injectable, multiInject, optional } from "inversify";
import { Action, CommandExecutionContext, CommandResult, SetModelAction, SetModelCommand, SModelRoot, TYPES, UpdateModelAction, UpdateModelCommand } from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { distinctAdd, remove } from "../../utils/array-utils";

export interface IModelUpdateObserver {
    /*Is called before an update model request from the server is applied*/
    beforeServerUpdate(model: SModelRoot): void
}

/**
 * Injectable registry for `IModelUpdateObservers` that will notifiy all registered observers before a server update is processed
 */
@injectable()
export class ModelUpdateObserverRegistry implements IModelUpdateObserver {

    constructor(@multiInject(GLSP_TYPES.IModelUpdateObserver) @optional() protected observers: IModelUpdateObserver[] = []) {
        observers.forEach(observer => this.register(observer))
    }

    register(observer: IModelUpdateObserver) {
        distinctAdd(this.observers, observer)
    }

    beforeServerUpdate(model: SModelRoot): void {
        this.observers.forEach(observer => observer.beforeServerUpdate(model))
    }

    deregister(observer: IModelUpdateObserver) {
        remove(this.observers, observer)
    }
}
/**
 * A special`UpdateModelCommand` that delegegates the `newRoot` to registered `IModelUpdateObserver`s before
 * performing the update
 */
@injectable()
export class ObserverableUpdateModelCommand extends UpdateModelCommand {
    constructor(@inject(TYPES.Action) action: UpdateModelAction,
        @inject(GLSP_TYPES.ModelUpdateObserverRegistry) protected readonly observerRegistry: ModelUpdateObserverRegistry) {
        super(action)
    }

    execute(context: CommandExecutionContext): CommandResult {
        let newRoot: SModelRoot;
        if (this.action.newRoot !== undefined) {
            newRoot = context.modelFactory.createRoot(this.action.newRoot);
        } else {
            newRoot = context.modelFactory.createRoot(context.root);
            if (this.action.matches !== undefined)
                this.applyMatches(newRoot, this.action.matches, context);
        }
        this.oldRoot = context.root;
        this.newRoot = newRoot;
        this.observerRegistry.beforeServerUpdate(newRoot)
        return this.performUpdate(this.oldRoot, this.newRoot, context);
    }
}

export function isUpdateModelAction(action: Action): action is UpdateModelAction {
    return action.kind === UpdateModelCommand.KIND &&
        ((<any>action)["newRoot"] !== undefined || (<any>action)["matches"] !== undefined)
        && (<any>action)["animate"] !== undefined
}

export function isSetModelAction(action: Action): action is SetModelAction {
    return action.kind === SetModelCommand.KIND && (<any>action)["newRoot"] !== undefined
}
