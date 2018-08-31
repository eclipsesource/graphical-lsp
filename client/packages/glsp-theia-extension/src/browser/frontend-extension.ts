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

import { bindContributionProvider, CommandContribution, MenuContribution } from "@theia/core";
import { FrontendApplicationContribution } from "@theia/core/lib/browser";
import { ContainerModule } from "inversify";
import { GLSPDiagramCommandContribution, GLSPDiagramMenuContribution } from "./diagram/glsp-diagram-commands";
import { GLSPTheiaSprottyConnector } from "./diagram/glsp-theia-sprotty-connector";
import { GraphicalLanguageClientContribution } from "./language/graphical-langauge-client-contribution";
import { GraphicalLanguageClientFactory } from "./language/graphical-language-client";
import { GraphicalLanguageClientProvider, GraphicalLanguageClientProviderImpl } from "./language/graphical-language-client-provider";
import { GraphicalLanguagesFrontendContribution } from "./language/graphical-languages-frontend-contribution";
export default new ContainerModule(bind => {



    bind(GraphicalLanguageClientFactory).toSelf().inSingletonScope();

    bindContributionProvider(bind, GraphicalLanguageClientContribution);
    bind(FrontendApplicationContribution).to(GraphicalLanguagesFrontendContribution);


    bind(GraphicalLanguageClientProviderImpl).toSelf().inSingletonScope();
    bind(GraphicalLanguageClientProvider).toDynamicValue(ctx => ctx.container.get(GraphicalLanguageClientProviderImpl)).inSingletonScope()

    bind(GLSPTheiaSprottyConnector).toSelf().inSingletonScope();

    bind(CommandContribution).to(GLSPDiagramCommandContribution).inSingletonScope()
    bind(MenuContribution).to(GLSPDiagramMenuContribution)

});