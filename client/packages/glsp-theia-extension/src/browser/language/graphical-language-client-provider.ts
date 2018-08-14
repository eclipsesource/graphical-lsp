/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { injectable, inject, named } from "inversify";
import { ContributionProvider } from "@theia/core";
import { GraphicalLanguageClientContribution } from "./graphical-langauge-client-contribution";
import { GraphicalLanguageClient } from "glsp-theia-extension/src/common/graphical-language-client-services";


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