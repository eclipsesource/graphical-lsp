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
import { Commands, ConnectionCloseHandler, ConnectionErrorHandler, ErrorHandler, OutputChannel, InitializationFailedHandler } from "@theia/languages/lib/browser";
import { Disposable, Message, MessageConnection, NotificationHandler, NotificationType } from "vscode-jsonrpc";
import { ExitNotification, ShutdownRequest } from "../../common";


export const GLSPClient = Symbol.for('GLSPClient')

export interface GLSPClient {
    onReady(): Promise<void>
    start(): Disposable;
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

export const Connection = Symbol.for('Connection')

export interface Connection {
    listen(): void
    onNotification<P, RO>(type: NotificationType<P, RO>, handler: NotificationHandler<P>): void;
    sendNotification<P, RO>(type: NotificationType<P, RO>, params?: P): void;
    shutdown(): Thenable<void>;
    exit(): void;
    dispose(): void;
}

export const ConnectionProvider = Symbol.for("ConnectionProvider")

export interface ConnectionProvider {
    get(errorHandler: ConnectionErrorHandler, closeHandler: ConnectionCloseHandler, outputChannel: OutputChannel | undefined): Thenable<Connection>;
}

export function createConnection(connection: MessageConnection, errorHandler: ConnectionErrorHandler, closeHandler: ConnectionCloseHandler) {
    connection.onError((data: [Error, Message, number]) => { errorHandler(data[0], data[1], data[2]); });
    connection.onClose(closeHandler);
    connection.onNotification
    const result: Connection = {
        listen: () => connection.listen(),
        sendNotification: <P, RO>(type: NotificationType<P, RO>, params?: P): void => connection.sendNotification(type, params),
        onNotification: <P, RO>(type: NotificationType<P, RO>, handler: NotificationHandler<P>): void => connection.onNotification(type, handler),
        shutdown: () => connection.sendRequest(ShutdownRequest.type, undefined),
        exit: () => connection.sendNotification(ExitNotification.type),
        dispose: () => connection.dispose()
    };
    return result;
}
