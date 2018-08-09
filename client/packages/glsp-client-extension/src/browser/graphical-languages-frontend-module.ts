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

import { ContainerModule } from "inversify";
import { GraphicalLanguageClientFactory } from "./graphical-language-client-factory";
import { bindContributionProvider } from "@theia/core";
import { GraphicalLanguageClientContribution } from "./graphical-langauge-client-contribution";
import { FrontendApplicationContribution } from "@theia/core/lib/browser";
import { GraphicalLanguagesFrontendContribution } from "./graphical-languages-frontend-contribution";
import { GraphicalLanguageClientProviderImpl, GraphicalLanguageClientProvider } from "./graphical-language-client-provider";
import { TestGLClientContribution } from "./test";

export default new ContainerModule(bind => {


    bind(GraphicalLanguageClientFactory).toSelf().inSingletonScope();

    bindContributionProvider(bind, GraphicalLanguageClientContribution);
    bind(FrontendApplicationContribution).to(GraphicalLanguagesFrontendContribution);


    bind(GraphicalLanguageClientProviderImpl).toSelf().inSingletonScope();
    bind(GraphicalLanguageClientProvider).toDynamicValue(ctx => ctx.container.get(GraphicalLanguageClientProviderImpl)).inSingletonScope();
    bind(GraphicalLanguageClientContribution).to(TestGLClientContribution).inSingletonScope()
});