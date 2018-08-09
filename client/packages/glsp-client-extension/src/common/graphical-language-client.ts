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
import { Commands, Disposable } from "@theia/languages/lib/common"
import { ErrorHandler } from "vscode-base-languageclient/lib/base";
export const GraphicalLanguageClient = Symbol('GraphicalLanguageClient');
export interface GraphicalLanguageClient {
    createDefaultErrorHandler(): ErrorHandler
    onReady(): Promise<void>
    start(): Disposable;
}

export class BaseGraphcialLanguageClient implements GraphicalLanguageClient {
    start(): Disposable {
        throw new Error("Method not implemented.");
    }
    onReady(): Promise<void> {
        throw new Error("Method not implemented.");
    }
    createDefaultErrorHandler(): ErrorHandler {
        throw new Error("Method not implemented.");
    }


}

export interface GraphicalLanguageClientOptions {
    commands?: Commands
    errorHandler?: ErrorHandler;

}