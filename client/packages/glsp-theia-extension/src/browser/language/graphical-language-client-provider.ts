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
import { inject, injectable, named } from "inversify";
import { GraphicalLanguageClientContribution } from "./graphical-langauge-client-contribution";
import { GraphicalLanguageClient } from "./graphical-language-client-services";


export const GraphicalLanguageClientProvider = Symbol('GraphicalLanguageClientProvider')

export interface GraphicalLanguageClientProvider {
    getLanguageClient(languageId: string): Promise<GraphicalLanguageClient | undefined>
}

@injectable()
export class GraphicalLanguageClientProviderImpl implements GraphicalLanguageClientProvider {
    @inject(ContributionProvider) @named(GraphicalLanguageClientContribution)
    private readonly contributions: ContributionProvider<GraphicalLanguageClientContribution>

    async getLanguageClient(languageId: string): Promise<GraphicalLanguageClient | undefined> {
        const contribution = this.getLanguageContribution(languageId);
        if (contribution) {
            return contribution.languageClient;
        }
        return undefined
    }

    protected getLanguageContribution(languageId: string): GraphicalLanguageClientContribution | undefined {
        const contributions = this.contributions.getContributions();
        if (contributions) {
            for (const contribution of contributions) {
                if (contribution.id === languageId) {
                    return contribution;
                }
            }
        }
        return undefined;
    }
}