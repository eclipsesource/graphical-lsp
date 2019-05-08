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
import { Container, interfaces } from "inversify";
import { defaultViewerOptions, ViewerOptions } from "sprotty";

import { GLSP_TYPES } from "../../types";

export interface GLSPViewerOptions extends ViewerOptions {
    noElementOverlap: boolean
}

export const defaultGLSPViewerOptions = () => (<GLSPViewerOptions>{
    ...defaultViewerOptions(),
    noElementOverlap: false
});

/**
 * Utility function to partially set viewer options. Default values (from `defaultGLSPViewerOptions`) are used for
 * options that are not specified.
 */
export function configureGLSPViewerOptions(context: { bind: interfaces.Bind, isBound: interfaces.IsBound, rebind: interfaces.Rebind },
    options: Partial<GLSPViewerOptions>): void {
    const opt: GLSPViewerOptions = {
        ...defaultGLSPViewerOptions(),
        ...options
    };
    if (context.isBound(GLSP_TYPES.ViewerOptions))
        context.rebind(GLSP_TYPES.ViewerOptions).toConstantValue(opt);
    else
        context.bind(GLSP_TYPES.ViewerOptions).toConstantValue(opt);
}

/**
* Utility function to partially override the currently configured viewer options in a DI container.
*/
export function overrideGLSPViewerOptions(container: Container, options: Partial<GLSPViewerOptions>): GLSPViewerOptions {
    const opt = container.get<GLSPViewerOptions>(GLSP_TYPES.ViewerOptions);
    for (const p in options) {
        if (options.hasOwnProperty(p))
            (opt as any)[p] = (options as any)[p];
    }
    return opt;
}
