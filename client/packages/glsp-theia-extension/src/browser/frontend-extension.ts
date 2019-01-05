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
import { GLSPClientFactory } from "./language/glsp-client";
import { GLSPClientContribution } from "./language/glsp-client-contribution";
import { GLSPClientProvider, GLSPClientProviderImpl } from "./language/glsp-client-provider";
import { GLSPFrontendContribution } from "./language/glsp-frontend-contribution";

export default new ContainerModule(bind => {

    bind(GLSPClientFactory).toSelf().inSingletonScope();

    bindContributionProvider(bind, GLSPClientContribution);
    bind(FrontendApplicationContribution).to(GLSPFrontendContribution);

    bind(GLSPClientProviderImpl).toSelf().inSingletonScope();
    bind(GLSPClientProvider).toDynamicValue(ctx => ctx.container.get(GLSPClientProviderImpl)).inSingletonScope()

    bind(GLSPTheiaSprottyConnector).toSelf().inSingletonScope();

});