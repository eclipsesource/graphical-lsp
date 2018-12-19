/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/

import { injectable } from "inversify";
import { CommandStack, SModelRoot } from "sprotty/lib";

export interface IModelAccess {
    readonly model: Promise<SModelRoot>;
}

export type IModelAccessProvider = () => Promise<IModelAccess>;

@injectable()
export class ModelProvidingCommandStack extends CommandStack implements IModelAccess {
    get model(): Promise<SModelRoot> {
        return this.currentPromise.then(
            state => state.root
        );
    }
}
