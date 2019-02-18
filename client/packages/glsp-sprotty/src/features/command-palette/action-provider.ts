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
import { Action, ActionHandlerRegistry, CenterAction, IActionDispatcher, IActionHandler, ICommand, ILogger, SelectAction, SModelElement, SModelRoot, TYPES } from "sprotty/lib";
import { toArray } from "sprotty/lib/utils/iterable";
import { IReadonlyModelAccessProvider } from "../../base/command-stack";
import { LabeledAction } from "../../base/diagram-ui-extension/diagram-ui-extension";
import { GLSP_TYPES } from "../../types";
import { isNameable, name } from "../nameable/model";
import { isSetCommandPaletteActionsAction, RequestCommandPaletteActions, SetCommandPaletteActions } from "./action-definitions";

export interface ICommandPaletteActionProvider {
    getActions(selectedElements: SModelElement[]): Promise<LabeledAction[]>;
}

export type ICommandPaletteActionProviderRegistry = () => Promise<ICommandPaletteActionProvider>;

@injectable()
export class CommandPaletteActionProviderRegistry implements ICommandPaletteActionProvider {
    public actionProvider: ICommandPaletteActionProvider[] = [];

    constructor(@multiInject(GLSP_TYPES.ICommandPaletteActionProvider) @optional() protected registeredActionProviders: ICommandPaletteActionProvider[] = []) {
        for (const registeredProvider of registeredActionProviders) {
            this.actionProvider.push(registeredProvider);
        }
    }

    getActions(selectedElements: SModelElement[]): Promise<LabeledAction[]> {
        const actionLists = this.actionProvider.map(provider => provider.getActions(selectedElements));
        return Promise.all(actionLists).then(p => p.reduce((acc, promise) => acc.concat(promise)));
    }
}

@injectable()
export class NavigationCommandPaletteActionProvider implements ICommandPaletteActionProvider {

    constructor(
        @inject(GLSP_TYPES.IReadonlyModelAccessProvider) protected modelAccessProvider: IReadonlyModelAccessProvider,
        @inject(TYPES.ILogger) protected logger: ILogger) { }

    getActions(selectedElements: SModelElement[]): Promise<LabeledAction[]> {
        return this.modelAccessProvider()
            .then(access => access.model)
            .then(model => this.createSelectActions(model))
            .catch(reason => {
                this.logger.error(this, 'Could not create navigation command palette actions.', reason);
                return [];
            });
    }

    createSelectActions(modelRoot: SModelRoot): LabeledAction[] {
        return toArray(modelRoot.index.all()
            .filter(element => isNameable(element))
            .map(nameable => new LabeledAction(`Select ${name(nameable)}`, [new SelectAction([nameable.id]), new CenterAction([nameable.id])])));
    }
}

@injectable()
export class ServerCommandPaletteActionProvider implements ICommandPaletteActionProvider, IActionHandler {
    readonly handledActionKinds = [SetCommandPaletteActions.KIND];
    private responseRequested: boolean;
    private responseActions?: LabeledAction[];

    constructor(@inject(TYPES.IActionDispatcher) protected actionDispatcher: IActionDispatcher,
        @inject(TYPES.ActionHandlerRegistry) protected registry: ActionHandlerRegistry) {
        registry.register(SetCommandPaletteActions.KIND, this);
    }

    handle(action: Action): ICommand | Action | void {
        if (isSetCommandPaletteActionsAction(action) && this.responseRequested) {
            this.responseActions = action.actions;
        }
    }

    cleanup() {
        this.responseRequested = false;
        this.responseActions = undefined;
    }

    async getActions(selectedElements: SModelElement[]): Promise<LabeledAction[]> {
        const selectedElementIDs = selectedElements.map(e => e.id);
        await this.actionDispatcher.dispatch(new RequestCommandPaletteActions(selectedElementIDs));
        this.responseRequested = true;
        let timeout: NodeJS.Timeout;
        let actionInterval: NodeJS.Timeout;
        const actionPromise = new Promise<LabeledAction[]>((resolve, reject) => {
            actionInterval = setInterval(() => {
                if (this.responseActions) {
                    clearTimeout(timeout);
                    clearInterval(actionInterval);
                    const clonedResponse = Object.assign([], this.responseActions);
                    this.cleanup();
                    return resolve(clonedResponse);
                }
            }, 100);
            return [];
        });
        const timeoutPromise = new Promise<LabeledAction[]>((resolve, reject) => {
            timeout = setTimeout(() => {
                clearTimeout(timeout);
                clearInterval(actionInterval);
                this.cleanup();
                // resolve empty but log error
                console.warn("ServerCommandPaletteActionProvider timed out after 2000ms!");
                return resolve([]);
            }, 2000);
            return [];
        });
        return Promise.race([actionPromise, timeoutPromise]);
    }
}
