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
import { Commands, ConnectionErrorHandler, ConnectionCloseHandler, OutputChannel } from "@theia/languages/lib/common"
import { ErrorHandler } from "vscode-base-languageclient/lib/base";
import { ActionMessage } from "sprotty/lib"
import { MessageConnection, Message, NotificationHandler, Disposable } from "vscode-jsonrpc";
import { ActionMessageNotification, ExitNotification, ShutdownRequest } from "./graphical-language-server-protocol";
export const GraphicalLanguageClient = Symbol('GraphicalLanguageClient');
export interface GraphicalLanguageClient {
    createDefaultErrorHandler(): ErrorHandler
    onReady(): Promise<void>
    start(): Disposable;
    stop(): Thenable<void> | undefined
    sendActionMessage(message: ActionMessage): void
    onActionMessage(handler: NotificationHandler<ActionMessage>): void

}
export namespace GraphicalLanguageClient {
    export interface Options {
        name: string,
        id: string,
        clientOptions: GraphicalLanguageClientOptions,
        connectionProvider: ConnectionProvider
    }
}

export interface GraphicalLanguageClientOptions {
    commands?: Commands
    errorHandler?: ErrorHandler;
    outputChannelName?: string
}

export const Connection = Symbol('Connection')
export interface Connection {
    listen(): void
    sendActionMessage(message: ActionMessage): void
    onActionMessage(handler: NotificationHandler<ActionMessage>): void
    shutdown(): Thenable<void>;
    exit(): void;
    dispose(): void;
}

export const ConnectionProvider = Symbol("ConnectionProvider")
export interface ConnectionProvider {
    get(errorHandler: ConnectionErrorHandler, closeHandler: ConnectionCloseHandler, outputChannel: OutputChannel | undefined): Thenable<Connection>;
}

export function createConnection(connection: MessageConnection, errorHandler: ConnectionErrorHandler, closeHandler: ConnectionCloseHandler) {
    connection.onError((data: [Error, Message, number]) => { errorHandler(data[0], data[1], data[2]); });
    connection.onClose(closeHandler);
    let result: Connection = {
        listen: () => connection.listen(),
        sendActionMessage: (message: ActionMessage) => connection.sendNotification(ActionMessageNotification.type, message),
        onActionMessage: (handler: NotificationHandler<ActionMessage>) => connection.onNotification(ActionMessageNotification.type, handler),
        shutdown: () => connection.sendRequest(ShutdownRequest.type, undefined),
        exit: () => connection.sendNotification(ExitNotification.type),
        dispose: () => connection.dispose()
    };
    return result;
}
