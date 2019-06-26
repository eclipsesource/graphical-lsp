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
import { CommandContribution } from "@theia/core";
import { ContainerModule } from "inversify";
import { FrontendApplicationContribution } from "@theia/core/lib/browser";
import { GLSPClientContribution } from "./language/glsp-client-contribution";
import { GLSPClientFactory } from "./language/glsp-client";
import { GLSPClientProvider } from "./language/glsp-client-provider";
import { GLSPClientProviderImpl } from "./language/glsp-client-provider";
import { GLSPDiagramCommandContribution } from "./diagram/glsp-diagram-commands";
import { GLSPDiagramKeybindingContribution } from "./diagram/glsp-diagram-commands";
import { GLSPDiagramMenuContribution } from "./diagram/glsp-diagram-commands";
import { GLSPFrontendContribution } from "./language/glsp-frontend-contribution";
import { GLSPTheiaSprottyConnector } from "./diagram/glsp-theia-sprotty-connector";
import { KeybindingContribution } from "@theia/core/lib/browser";
import { MenuContribution } from "@theia/core";

import { bindContributionProvider } from "@theia/core";


export default new ContainerModule(bind => {
    bind(GLSPClientFactory).toSelf().inSingletonScope();

    bindContributionProvider(bind, GLSPClientContribution);
    bind(FrontendApplicationContribution).to(GLSPFrontendContribution);

    bind(GLSPClientProviderImpl).toSelf().inSingletonScope();
    bind(GLSPClientProvider).toService(GLSPClientProviderImpl);

    bind(GLSPTheiaSprottyConnector).toSelf().inSingletonScope();

    bind(CommandContribution).to(GLSPDiagramCommandContribution).inSingletonScope();
    bind(MenuContribution).to(GLSPDiagramMenuContribution).inSingletonScope();
    bind(KeybindingContribution).to(GLSPDiagramKeybindingContribution).inSingletonScope();
});
