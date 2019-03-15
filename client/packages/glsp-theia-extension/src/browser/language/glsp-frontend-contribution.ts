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
import { ContributionProvider } from "@theia/core";
import { FrontendApplication } from "@theia/core/lib/browser";
import { FrontendApplicationContribution } from "@theia/core/lib/browser";
import { GLSPClientContribution } from "./glsp-client-contribution";

import { inject } from "inversify";
import { injectable } from "inversify";
import { named } from "inversify";


@injectable()
export class GLSPFrontendContribution implements FrontendApplicationContribution {
    @inject(FrontendApplication)
    protected readonly app: FrontendApplication;

    constructor(
        @inject(ContributionProvider) @named(GLSPClientContribution)
        protected readonly contributions: ContributionProvider<GLSPClientContribution>
    ) { }

    onStart(app: FrontendApplication): void {
        for (const contribution of this.contributions.getContributions()) {
            contribution.activate(app);
        }
    }
}
