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
import { WebSocketConnectionProvider } from "@theia/core/lib/browser";
import { Disposable } from "@theia/core/lib/common/disposable";
import { ConnectionCloseHandler, ConnectionErrorHandler, LanguageContribution, OutputChannel } from "@theia/languages/lib/common";
import { inject, injectable } from "inversify";
import { CloseAction, ErrorAction, ErrorHandler, NotificationHandler } from "vscode-base-languageclient/lib/base";
import { Message, NotificationType } from "vscode-jsonrpc";
import { Connection, ConnectionProvider, createConnection, GraphicalLanguageClient, GraphicalLanguageClientOptions } from "../../common/graphical-language-client-services";

enum ClientState {
    Initial,
    Starting,
    StartFailed,
    Running,
    Stopping,
    Stopped
}
export class BaseGraphcialLanguageClient implements GraphicalLanguageClient {
    private id: string;
    private name: string;
    protected readonly connectionProvider: ConnectionProvider;
    private connectionPromise: Thenable<Connection> | undefined;
    private resolvedConnection: Connection | undefined;
    private state: ClientState
    private _outputChannel: OutputChannel;
    private clientOptions: GraphicalLanguageClientOptions;
    private onStop: Thenable<void> | undefined;
    private _onReady: Promise<void>;


    constructor({ id, name, clientOptions, connectionProvider }: GraphicalLanguageClient.Options) {
        this.connectionProvider = connectionProvider;
        this.id = id;
        this.name = name;
        this.clientOptions = clientOptions;
        this.onStop = undefined
        this._onReady = Promise.resolve()
        this.state = ClientState.Initial

    }
    start(): Disposable {
        this.state = ClientState.Starting;
        this.resolveConnection().then((connection) => {
            connection.listen()
            this.resolvedConnection = connection;
            this.state = ClientState.Running;
        }).then(undefined, (error) => {
            this.state = ClientState.StartFailed;
        });
        return {
            dispose: () => this.stop()
        }

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
    createDefaultErrorHandler(): ErrorHandler {
        return new DefaultErrorHandler(this.name);
    }


    private resolveConnection(): Thenable<Connection> {
        if (!this.connectionPromise) {
            this.connectionPromise = this.doCreateConnection();
        }
        return this.connectionPromise;
    }


    protected doCreateConnection(): Thenable<Connection> {
        const errorHandler = (this as any).handleConnectionError.bind(this);
        const closeHandler = this.handleConnectionClosed.bind(this);
        return this.connectionProvider.get(errorHandler, closeHandler, undefined);
    }


    private handleConnectionError(error: Error, message: Message, count: number) {
        let action = this.clientOptions.errorHandler!.error(error, message, count);
        if (action === ErrorAction.Shutdown) {
            console.error('Connection to server is erroring. Shutting down server.')
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
            throw new Error('Graphical Language client is not ready yet');
        }
        this.resolvedConnection!.onNotification(type, handler);
    }
    sendNotification<P, RO>(type: NotificationType<P, RO>, params?: P): void {
        if (!this.isConnectionActive()) {
            throw new Error('Graphical Language client is not ready yet');
        }
        this.resolvedConnection!.sendNotification(type, params);
    }

    private isConnectionActive(): boolean {
        return this.state === ClientState.Running && !!this.resolvedConnection;
    }
}

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

        const client = new BaseGraphcialLanguageClient({
            name: contribution.name,
            id: contribution.id,
            clientOptions: clientOptions,
            connectionProvider: {
                get: (errorHandler: ConnectionErrorHandler, closeHandler: ConnectionCloseHandler) =>
                    new Promise(resolve => {
                        this.connectionProvider.listen({
                            path: LanguageContribution.getPath(contribution),
                            onConnection: messageConnection => {
                                const connection = createConnection(messageConnection, errorHandler, closeHandler);
                                resolve(connection);
                            },
                        },
                            { reconnecting: false }
                        );
                    })
            }

        });

        const defaultErrorHandler = client.createDefaultErrorHandler();
        return client
    }


}

class DefaultErrorHandler implements ErrorHandler {

    private restarts: number[];

    constructor(private name: string) {
        this.restarts = [];
    }

    public error(_error: Error, _message: Message, count: number): ErrorAction {
        if (count && count <= 3) {
            return ErrorAction.Continue;
        }
        return ErrorAction.Shutdown;
    }
    public closed(): CloseAction {
        this.restarts.push(Date.now());
        if (this.restarts.length < 5) {
            return CloseAction.Restart;
        } else {
            let diff = this.restarts[this.restarts.length - 1] - this.restarts[0];
            if (diff <= 3 * 60 * 1000) {
                alert(`The ${this.name} server crashed 5 times in the last 3 minutes. The server will not be restarted.`);
                return CloseAction.DoNotRestart;
            } else {
                this.restarts.shift();
                return CloseAction.Restart;
            }
        }
    }
}