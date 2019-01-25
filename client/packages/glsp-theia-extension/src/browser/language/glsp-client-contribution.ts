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
import { CommandRegistry, DisposableCollection, MaybePromise, MessageService } from "@theia/core";
import { FrontendApplication, WebSocketConnectionProvider, WebSocketOptions } from "@theia/core/lib/browser";
import { Deferred } from "@theia/core/lib/common/promise-util";
import { EditorManager } from "@theia/editor/lib/browser";
import { Commands, Disposable, InitializeParams, State } from '@theia/languages/lib/browser';
import { LanguageContribution } from "@theia/languages/lib/common";
import { WorkspaceService } from "@theia/workspace/lib/browser";
import { inject, injectable } from "inversify";
import { MessageConnection, ResponseError } from "vscode-jsonrpc";
import { GLSPClientFactory } from "./glsp-client";
import { GLSPClient, GLSPClientOptions } from "./glsp-client-services";

export const GLSPClientContribution = Symbol.for('GLSPClientContribution')

export interface GLSPClientContribution extends LanguageContribution {
    readonly running: boolean;
    readonly glspClient: Promise<GLSPClient>;
    waitForActivation(app: FrontendApplication): Promise<void>;
    activate(app: FrontendApplication): Disposable;
    deactivate(): void;
    restart(): void;
}

@injectable()
export abstract class BaseGLSPClientContribution implements GLSPClientContribution, Commands {
    abstract readonly id: string
    abstract readonly name: string
    abstract readonly fileExtensions: string[]

    protected _glspClient: GLSPClient | undefined

    protected resolveReady: (glspClient: GLSPClient) => void
    protected ready: Promise<GLSPClient>

    @inject(WorkspaceService) protected readonly workspaceService: WorkspaceService
    @inject(MessageService) protected readonly messageService: MessageService;
    @inject(WebSocketConnectionProvider) protected readonly connectionProvider: WebSocketConnectionProvider
    @inject(GLSPClientFactory) protected readonly languageClientFactory: GLSPClientFactory
    @inject(CommandRegistry) protected readonly registry: CommandRegistry
    @inject(EditorManager) protected readonly editorManager: EditorManager
    constructor() {
        this.waitForReady()
    }

    get glspClient(): Promise<GLSPClient> {
        return this._glspClient ? Promise.resolve(this._glspClient) : this.ready
    }

    waitForActivation(app: FrontendApplication): Promise<any> {
        const activationPromises: Promise<any>[] = [];
        const workspaceContains = this.workspaceContains;
        if (workspaceContains.length !== 0) {
            activationPromises.push(this.waitForItemInWorkspace());
        }
        const fileExtensions = this.fileExtensions;
        if (fileExtensions) {
            activationPromises.push(this.waitForOpenDocument(fileExtensions));
        }

        if (activationPromises.length !== 0) {
            return Promise.all([
                this.ready,
                Promise.race(activationPromises.map(p => new Promise(async resolve => {
                    try {
                        await p;
                        resolve();
                    } catch (e) {
                        console.error(e);
                    }
                })))
            ]);
        }
        return this.ready
    }

    protected readonly toDeactivate = new DisposableCollection();
    activate(): Disposable {
        if (this.toDeactivate.disposed) {
            this.doActivate(this.toDeactivate);
        }
        return this.toDeactivate;
    }
    deactivate(): void {
        this.toDeactivate.dispose();
    }

    protected async doActivate(toDeactivate: DisposableCollection): Promise<void> {
        const options: WebSocketOptions = {};
        toDeactivate.push(Disposable.create(() => options.reconnecting = false));
        this.connectionProvider.listen({
            path: LanguageContribution.getPath(this),
            onConnection: messageConnection => {
                if (toDeactivate.disposed) {
                    messageConnection.dispose();
                    return;
                }
                const languageClient = this.createLanguageClient(messageConnection);
                this.onWillStart(languageClient);
                toDeactivate.pushAll([
                    messageConnection,
                    this.toRestart.push(Disposable.create(async () => {
                        await languageClient.onReady();
                        languageClient.stop();
                    })),
                    languageClient.start()
                ]);
            }
        }, options);
    }

    protected state: State | undefined;
    get running(): boolean {
        return !this.toDeactivate.disposed && this.state === State.Running;
    }
    restart(): void {
        this.deactivate();
        this.activate();
    }

    protected onWillStart(languageClient: GLSPClient): void {
        languageClient.onReady().then(() => this.onReady(languageClient));
    }

    protected onReady(languageClient: GLSPClient): void {
        this._glspClient = languageClient;
        this.resolveReady(this._glspClient);
        this.waitForReady();
    }
    protected readonly toRestart = new DisposableCollection();

    protected waitForReady(): void {
        this.ready = new Promise<GLSPClient>(resolve =>
            this.resolveReady = resolve
        )
    }

    protected createLanguageClient(connection: MessageConnection | (() => MaybePromise<MessageConnection>)): GLSPClient {
        const clientOptions = this.createOptions();
        return this.languageClientFactory.get(this, clientOptions, connection);
    }


    protected createOptions(): GLSPClientOptions {
        const { id } = this;
        return {
            initializationFailedHandler: err => this.handleInitializationFailed(err),

        };
    }

    protected handleInitializationFailed(err: ResponseError<InitializeParams> | Error | any): boolean {
        this.deactivate();
        const detail = err instanceof Error ? `: ${err.message}` : '.';
        this.messageService.error(`Failed to start ${this.name} glsp server${detail}`, 'Retry').then(result => {
            if (result) {
                this.activate();
            }
        });
        return false;
    }
    protected deferredConnection = new Deferred<MessageConnection>();

    protected get workspaceContains(): string[] {
        return [];
    }

    protected async waitForOpenDocument(fileExtensions: string[]): Promise<any> {
        const currentEditor = this.editorManager.currentEditor
        if (currentEditor) {
            const uri = currentEditor.editor.uri.toString()
            for (const extension of fileExtensions) {
                if (uri.endsWith(extension)) {
                    return Promise.resolve();
                }
            }
        }
    }
    protected async waitForItemInWorkspace(): Promise<any> {
        const doesContain = await this.workspaceService.containsSome(this.workspaceContains);
        if (!doesContain) {
            return new Promise(resolve => { });
        }
        return doesContain;
    }




    protected stop = Promise.resolve();


    registerCommand(id: string, callback: (...args: any[]) => any, thisArg?: any): Disposable {
        const execute = callback.bind(thisArg);
        return this.registry.registerCommand({ id }, { execute });
    }
}