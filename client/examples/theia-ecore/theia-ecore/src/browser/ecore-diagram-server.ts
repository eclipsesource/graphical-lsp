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
import { GLSPTheiaDiagramServer } from "glsp-theia-extension/lib/browser";
import { ComputedBoundsAction, SModelRootSchema } from "sprotty";
import { isEcoreGraph } from "sprotty-ecore/lib";
function needsInitialServerLayout(root: SModelRootSchema) {
    return isEcoreGraph(root) && root.needsInitialLayout;
}
export class EcoreTheiaDiagramServer extends GLSPTheiaDiagramServer {

    protected handleComputedBounds(action: ComputedBoundsAction): boolean {
        if (needsInitialServerLayout(this.currentRoot)) {
            return true;
        } else {
            return super.handleComputedBounds(action);
        }

    }
}