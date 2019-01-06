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
import { GLSPClientContribution } from "./glsp-client-contribution";
import { GLSPClient } from "./glsp-client-services";

export const GLSPClientProvider = Symbol.for('GLSPClientProvider')

export interface GLSPClientProvider {
    getLanguageClient(languageId: string): Promise<GLSPClient | undefined>
}

@injectable()
export class GLSPClientProviderImpl implements GLSPClientProvider {
    @inject(ContributionProvider) @named(GLSPClientContribution)
    private readonly contributions: ContributionProvider<GLSPClientContribution>

    async getLanguageClient(languageId: string): Promise<GLSPClient | undefined> {
        const contribution = this.getLanguageContribution(languageId);
        if (contribution) {
            return contribution.languageClient;
        }
        return undefined
    }

    protected getLanguageContribution(languageId: string): GLSPClientContribution | undefined {
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