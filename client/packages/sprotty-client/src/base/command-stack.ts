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
import { injectable } from "inversify";
import { CommandStack, SModelRoot } from "sprotty/lib";

/**
 * Provides access to the current `SModelRoot` instance.
 *
 * This is useful if you need to query the model for some tasks,
 *  e.g., determine the list of elements, etc.
 *
 * Note that this provider will only return a copy of the current instance.
 * Thus, changes to the returned `SModelRoot` won't have any effect.
 * Changes of the `SModelRoot` should be performed inside a command.
 */
export interface IReadonlyModelAccess {
    /**
     * The current `SModelRoot` instance.
     *
     * Note that this is a copy of the current instance.
     * Thus, changes to the returned `SModelRoot` won't have any effect.
     * Changes of the `SModelRoot` should be performed inside a command.
     */
    readonly model: Promise<SModelRoot>;
}

export type IReadonlyModelAccessProvider = () => Promise<IReadonlyModelAccess>;

@injectable()
export class GLSPCommandStack extends CommandStack implements IReadonlyModelAccess {

    get model(): Promise<SModelRoot> {
        return this.currentPromise.then(
            state => this.modelFactory.createRoot(state.root)
        );
    }
}

