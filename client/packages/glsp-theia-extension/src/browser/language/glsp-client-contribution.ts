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
import { CommandRegistry } from "@theia/core";
import { FrontendApplication } from "@theia/core/lib/browser";
import { Commands, Disposable } from '@theia/languages/lib/browser';
import { LanguageContribution } from "@theia/languages/lib/common";
import { inject, injectable } from "inversify";
import { GLSPClientFactory } from "./glsp-client";
import { GLSPClient, GLSPClientOptions } from "./glsp-client-services";

export const GLSPClientContribution = Symbol.for('GLSPClientContribution')

export interface GLSPClientContribution extends LanguageContribution {
    readonly languageClient: Promise<GLSPClient>;
    activate(app: FrontendApplication): Disposable;
    waitForActivation(app: FrontendApplication): Promise<void>;
}

@injectable()
export abstract class BaseGLSPClientContribution implements GLSPClientContribution, Commands {
    abstract readonly id: string;
    abstract readonly name: string;

    protected _languageClient: GLSPClient | undefined;

    protected resolveReady: (languageClient: GLSPClient) => void;
    protected ready: Promise<GLSPClient>;


    @inject(CommandRegistry) protected readonly registry: CommandRegistry;
    constructor(@inject(GLSPClientFactory) protected readonly languageClientFactory: GLSPClientFactory) {
        this.waitForReady()
    }

    get languageClient(): Promise<GLSPClient> {
        return this._languageClient ? Promise.resolve(this._languageClient) : this.ready;
    }

    protected waitForReady(): void {
        this.ready = new Promise<GLSPClient>(resolve =>
            this.resolveReady = resolve
        );
    }

    activate(): Disposable {
        const languageClient = this.createLanguageClient();
        this.onWillStart(languageClient);
        return languageClient.start()
    }

    waitForActivation(app: FrontendApplication): Promise<any> {
        return Promise.resolve()
    }
    protected onWillStart(languageClient: GLSPClient): void {
        languageClient.onReady().then(() => this.onReady(languageClient));
    }

    protected onReady(languageClient: GLSPClient): void {
        this._languageClient = languageClient;
        this.resolveReady(this._languageClient);
        this.waitForReady();
    }

    protected createLanguageClient(): GLSPClient {
        const clientOptions = this.createOptions();
        return this.languageClientFactory.get(this, clientOptions);
    }

    protected createOptions(): GLSPClientOptions {
        return {
            commands: this
        };
    }

    registerCommand(id: string, callback: (...args: any[]) => any, thisArg?: any): Disposable {
        const execute = callback.bind(thisArg);
        return this.registry.registerCommand({ id }, { execute });
    }
}