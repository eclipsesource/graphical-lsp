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
import { GraphicalLanguageClient, GraphicalLanguageClientOptions } from "./graphical-language-client-services";
import { GraphicalLanguageClientFactory } from "./graphical-language-client";

export const GraphicalLanguageClientContribution = Symbol('GraphicalLanguageClientContribution')
export interface GraphicalLanguageClientContribution extends LanguageContribution {
    readonly languageClient: Promise<GraphicalLanguageClient>;
    activate(app: FrontendApplication): Disposable;
    waitForActivation(app: FrontendApplication): Promise<void>;
}

@injectable()
export abstract class BaseGraphicalLanguageClientContribution implements GraphicalLanguageClientContribution, Commands {
    abstract readonly id: string;
    abstract readonly name: string;

    protected _languageClient: GraphicalLanguageClient | undefined;

    protected resolveReady: (languageClient: GraphicalLanguageClient) => void;
    protected ready: Promise<GraphicalLanguageClient>;


    @inject(CommandRegistry) protected readonly registry: CommandRegistry;
    constructor(@inject(GraphicalLanguageClientFactory) protected readonly languageClientFactory: GraphicalLanguageClientFactory) {
        this.waitForReady()
    }

    get languageClient(): Promise<GraphicalLanguageClient> {
        return this._languageClient ? Promise.resolve(this._languageClient) : this.ready;
    }

    protected waitForReady(): void {
        this.ready = new Promise<GraphicalLanguageClient>(resolve =>
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
    protected onWillStart(languageClient: GraphicalLanguageClient): void {
        languageClient.onReady().then(() => this.onReady(languageClient));
    }

    protected onReady(languageClient: GraphicalLanguageClient): void {
        this._languageClient = languageClient;
        this.resolveReady(this._languageClient);
        this.waitForReady();
    }

    protected createLanguageClient(): GraphicalLanguageClient {
        const clientOptions = this.createOptions();
        return this.languageClientFactory.get(this, clientOptions);
    }

    protected createOptions(): GraphicalLanguageClientOptions {
        return {
            commands: this
        };
    }

    registerCommand(id: string, callback: (...args: any[]) => any, thisArg?: any): Disposable {
        const execute = callback.bind(thisArg);
        return this.registry.registerCommand({ id }, { execute });
    }
}