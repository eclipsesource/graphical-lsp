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
import { injectable, inject } from "inversify";
import { WebSocketConnectionProvider } from "@theia/core/lib/browser";
import { GraphicalLanguageClient, GraphicalLanguageClientOptions, BaseGraphcialLanguageClient } from "../common/graphical-language-client";
import { LanguageContribution } from "@theia/languages/lib/common"
import { ErrorAction } from "vscode-base-languageclient/lib/base";
@injectable()
export class GraphicalLanguageClientFactory {

    constructor(
        @inject(WebSocketConnectionProvider) protected readonly connectionProvider: WebSocketConnectionProvider
    ) { }

    get(contribution: LanguageContribution, clientOptions: GraphicalLanguageClientOptions): GraphicalLanguageClient {

        if (!clientOptions.errorHandler) {
            clientOptions.errorHandler = {
                error: () => ErrorAction.Continue,
                closed: () => defaultErrorHandler.closed()
            };
        }

        const client = new BaseGraphcialLanguageClient();

        const defaultErrorHandler = client.createDefaultErrorHandler();
        return client
    }


}