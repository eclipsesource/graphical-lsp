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

import { bindContributionProvider } from "@theia/core";
import { FrontendApplicationContribution } from "@theia/core/lib/browser";
import { ContainerModule } from "inversify";
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

});