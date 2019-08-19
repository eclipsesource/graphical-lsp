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
import {
    Commands,
    ConnectionCloseHandler,
    ConnectionErrorHandler,
    ErrorHandler,
    InitializationFailedHandler,
    OutputChannel
} from "@theia/languages/lib/browser";
import { Disposable, Message, MessageConnection, NotificationHandler, NotificationType } from "vscode-jsonrpc";

import { ExitNotification, InitializeParameters, InitializeRequest, ShutdownRequest } from "../../common";


export const GLSPClient = Symbol.for('GLSPClient');

export interface GLSPClient {
    onReady(): Promise<void>
    start(): Disposable;
    initialize(params: InitializeParameters): Thenable<Boolean>;
    stop(): Thenable<void> | undefined
    onNotification<P, RO>(type: NotificationType<P, RO>, handler: NotificationHandler<P>): void;
    sendNotification<P, RO>(type: NotificationType<P, RO>, params?: P): void;

}
export namespace GLSPClient {
    export interface Options {
        name: string,
        id: string,
        clientOptions: GLSPClientOptions,
        connectionProvider: ConnectionProvider
    }
}

export interface GLSPClientOptions {
    commands?: Commands
    initializationFailedHandler?: InitializationFailedHandler;
    errorHandler?: ErrorHandler;
}

export const Connection = Symbol.for('Connection');

export interface Connection {
    listen(): void
    onNotification<P, RO>(type: NotificationType<P, RO>, handler: NotificationHandler<P>): void;
    sendNotification<P, RO>(type: NotificationType<P, RO>, params?: P): void;
    initialize(params: InitializeParameters): Thenable<Boolean>;
    shutdown(): Thenable<void>;
    exit(): void;
    dispose(): void;
}

export const ConnectionProvider = Symbol.for("ConnectionProvider");

export interface ConnectionProvider {
    get(errorHandler: ConnectionErrorHandler, closeHandler: ConnectionCloseHandler, outputChannel: OutputChannel | undefined): Thenable<Connection>;
}

export function createConnection(connection: MessageConnection, errorHandler: ConnectionErrorHandler, closeHandler: ConnectionCloseHandler) {
    connection.onError((data: [Error, Message, number]) => { errorHandler(data[0], data[1], data[2]); });
    connection.onClose(closeHandler);
    connection.onNotification;
    const result: Connection = {
        listen: () => connection.listen(),
        sendNotification: <P, RO>(type: NotificationType<P, RO>, params?: P): void => connection.sendNotification(type, params),
        onNotification: <P, RO>(type: NotificationType<P, RO>, handler: NotificationHandler<P>): void => connection.onNotification(type, handler),
        initialize: (params: InitializeParameters) => connection.sendRequest(InitializeRequest.type, params),
        shutdown: () => connection.sendRequest(ShutdownRequest.type, undefined),
        exit: () => connection.sendNotification(ExitNotification.type),
        dispose: () => connection.dispose()
    };
    return result;
}
