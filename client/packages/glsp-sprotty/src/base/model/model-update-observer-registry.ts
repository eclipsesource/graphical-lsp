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
import { Action } from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { ICommand } from "sprotty/lib";
import { IModelFactory } from "sprotty/lib";
import { SelfInitializingActionHandler } from "../diagram-ui-extension/diagram-ui-extension-registry";
import { SetModelAction } from "sprotty/lib";
import { SetModelCommand } from "sprotty/lib";
import { SModelRoot } from "sprotty/lib";
import { TYPES } from "sprotty/lib";
import { UpdateModelAction } from "sprotty/lib";
import { UpdateModelCommand } from "sprotty/lib";

import { distinctAdd } from "../../utils/array-utils";
import { inject } from "inversify";
import { injectable } from "inversify";
import { multiInject } from "inversify";
import { optional } from "inversify";
import { remove } from "../../utils/array-utils";

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
        observers.forEach(observer => this.register(observer));
    }

    register(observer: IModelUpdateObserver) {
        distinctAdd(this.observers, observer);
    }

    beforeServerUpdate(model: SModelRoot): void {
        this.observers.forEach(observer => observer.beforeServerUpdate(model));
    }

    deregister(observer: IModelUpdateObserver) {
        remove(this.observers, observer);
    }
}
/**
 * Actionhandler that replaces the default action handler for `SetModelAction` and `UpdateModelAction`
 * and allows registered `IModelUpdateObservers` to process and modify the model before the actual update is executed
 */
@injectable()
export class ModelUpdateActionInitializer extends SelfInitializingActionHandler {
    @inject(GLSP_TYPES.ModelUpdateObserverRegistry) protected readonly observerRegistry: ModelUpdateObserverRegistry;
    @inject(TYPES.IModelFactory) protected readonly modelFactory: IModelFactory;

    readonly handledActionKinds = [SetModelCommand.KIND, UpdateModelCommand.KIND];

    handle(action: Action): ICommand | Action | void {
        if (isSetModelAction(action) || isUpdateModelAction(action)) {
            if (action.newRoot) {
                const model = this.modelFactory.createRoot(action.newRoot);
                this.observerRegistry.beforeServerUpdate(model);
                // Transform the model back into the corresponding schema after
                // all observers have applied their modifications
                const updatedSchema = this.modelFactory.createSchema(model);

                return new UpdateModelCommand(new UpdateModelAction(updatedSchema, true));
            }
        }
    }
}

export function isSetModelAction(action: Action): action is SetModelAction {
    return action.kind === SetModelCommand.KIND && (<any>action)["newRoot"] !== undefined;
}

export function isUpdateModelAction(action: Action): action is UpdateModelAction {
    return action.kind === UpdateModelCommand.KIND &&
        ((<any>action)["newRoot"] !== undefined || (<any>action)["matches"] !== undefined)
        && (<any>action)["animate"] !== undefined;
}
