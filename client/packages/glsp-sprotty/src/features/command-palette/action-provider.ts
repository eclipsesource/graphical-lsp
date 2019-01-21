/*******************************************************************************
 * Copyright (c) 2019 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/

import { inject, injectable, multiInject, optional } from "inversify";
import { CenterAction, ILogger, SelectAction, SModelElement, SModelRoot, TYPES } from "sprotty/lib";
import { toArray } from "sprotty/lib/utils/iterable";
import { IReadonlyModelAccessProvider } from "../../base/command-stack";
import { LabeledAction } from "../../base/diagram-ui-extension/diagram-ui-extension";
import { GLSP_TYPES } from "../../types";
import { isNameable, name } from "../nameable/model";

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
