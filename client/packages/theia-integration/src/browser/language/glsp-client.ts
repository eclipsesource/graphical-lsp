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
import { MaybePromise } from "@theia/core";
import { WebSocketConnectionProvider } from "@theia/core/lib/browser";
import { Disposable } from "@theia/core/lib/common/disposable";
import { CloseAction, ErrorAction, Message, NotificationHandler } from "@theia/languages/lib/browser";
import { LanguageContribution } from "@theia/languages/lib/common";
import { inject, injectable } from "inversify";
import { MessageConnection, NotificationType } from "vscode-jsonrpc";

import { InitializeParameters } from "../../common";
import { Connection, ConnectionProvider, createConnection, GLSPClient, GLSPClientOptions } from "./glsp-client-services";

enum ClientState {
    Initial,
    Starting,
    StartFailed,
    Running,
    Stopping,
    Stopped
}
export class BaseGLSPClient implements GLSPClient {
    protected id: string;
    protected name: string;
    protected readonly connectionProvider: ConnectionProvider;
    protected connectionPromise: Thenable<Connection> | undefined;
    protected resolvedConnection: Connection | undefined;
    protected state: ClientState;

    protected clientOptions: GLSPClientOptions;
    protected onStop: Thenable<void> | undefined;
    protected _onReady: Promise<void>;

    constructor({ id, name, clientOptions, connectionProvider }: GLSPClient.Options) {
        this.connectionProvider = connectionProvider;
        this.id = id;
        this.name = name;
        this.clientOptions = clientOptions;
        this.onStop = undefined;
        this._onReady = Promise.resolve();
        this.state = ClientState.Initial;
    }

    start(): Disposable {
        this.state = ClientState.Starting;
        this.resolveConnection().then((connection) => {
            connection.listen();
            this.resolvedConnection = connection;
            this.state = ClientState.Running;
        }).then(undefined, (error) => {
            this.state = ClientState.StartFailed;
        });
        return {
            dispose: () => this.stop()
        };

    }

    initialize(params: InitializeParameters): Thenable<Boolean> {
        if (this.connectionPromise && this.resolvedConnection) {
            return this.resolvedConnection.initialize(params);
        }
        return Promise.resolve(false);
    }

    public stop(): Thenable<void> | undefined {
        if (!this.connectionPromise) {
            this.state = ClientState.Stopped;
            return Promise.resolve();
        }
        if (this.state === ClientState.Stopping && this.onStop) {
            return this.onStop;
        }
        this.state = ClientState.Stopping;
        return this.onStop = this.resolveConnection().then(connection => {
            return connection.shutdown().then(() => {
                connection.exit();
                connection.dispose();
                this.state = ClientState.Stopped;
                this.onStop = undefined;
                this.connectionPromise = undefined;
                this.resolvedConnection = undefined;
            });
        });

    }
    onReady(): Promise<void> {
        return this._onReady;
    }

    private resolveConnection(): Thenable<Connection> {
        if (!this.connectionPromise) {
            this.connectionPromise = this.doCreateConnection();
        }
        return this.connectionPromise;
    }

    protected doCreateConnection(): Thenable<Connection> {
        const errorHandler = this.handleConnectionError.bind(this);
        const closeHandler = this.handleConnectionClosed.bind(this);
        return this.connectionProvider.get(errorHandler, closeHandler, undefined);
    }

    protected handleConnectionError(error: Error, message: Message, count: number) {
        const action = this.clientOptions.errorHandler!.error(error, message, count);
        if (action === ErrorAction.Shutdown) {
            console.error('Connection to server is erroring. Shutting down server.');
            this.stop();
        }
    }

    protected handleConnectionClosed() {
        if (this.state === ClientState.Stopping || this.state === ClientState.Stopped) {
            return;
        }
    }

    onNotification<P, RO>(type: NotificationType<P, RO>, handler: NotificationHandler<P>): void {
        if (!this.isConnectionActive()) {
            throw new Error('GLSP client is not ready yet');
        }
        this.resolvedConnection!.onNotification(type, handler);
    }

    sendNotification<P, RO>(type: NotificationType<P, RO>, params?: P): void {
        if (!this.isConnectionActive()) {
            throw new Error('GLSP client is not ready yet');
        }
        this.resolvedConnection!.sendNotification(type, params);
    }

    private isConnectionActive(): boolean {
        return this.state === ClientState.Running && !!this.resolvedConnection;
    }
}

@injectable()
export class GLSPClientFactory {
    constructor(
        @inject(WebSocketConnectionProvider) protected readonly connectionProvider: WebSocketConnectionProvider) { }

    get(contribution: LanguageContribution, clientOptions: GLSPClientOptions, connectionProvider: MessageConnection | (() => MaybePromise<MessageConnection>)): GLSPClient {

        if (!clientOptions.errorHandler) {
            clientOptions.errorHandler = {
                error: () => ErrorAction.Continue,
                closed: () => CloseAction.DoNotRestart
            };
        }

        return new BaseGLSPClient({
            name: contribution.name,
            id: contribution.id,
            clientOptions: clientOptions,
            connectionProvider: {
                get: async (errorHandler, closeHandler) => {
                    const connection = typeof connectionProvider === 'function' ? await connectionProvider() : connectionProvider;
                    return createConnection(connection, errorHandler, closeHandler);
                }
            }
        });
    }
}
