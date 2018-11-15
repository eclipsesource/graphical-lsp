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
import { ContributionProvider } from "@theia/core";
import { FrontendApplication, FrontendApplicationContribution } from "@theia/core/lib/browser";
import { inject, injectable, named } from "inversify";
import { GraphicalLanguageClientContribution } from "./graphical-langauge-client-contribution";

@injectable()
export class GraphicalLanguagesFrontendContribution implements FrontendApplicationContribution {
    constructor(
        @inject(ContributionProvider) @named(GraphicalLanguageClientContribution)
        protected readonly contributions: ContributionProvider<GraphicalLanguageClientContribution>
    ) { }

    onStart(app: FrontendApplication): void {
        for (const contribution of this.contributions.getContributions()) {
            contribution.activate(app)
        }
    }
}