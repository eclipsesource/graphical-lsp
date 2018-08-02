/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */

import {
    ILogger, SelectCommand, ActionHandlerRegistry, IActionDispatcher, SModelStorage, TYPES,
    ViewerOptions, ActionMessage, ExportSvgAction, RequestModelAction, Action,
    ICommand, ServerStatusAction
} from 'sprotty/lib'
import { TheiaSprottyConnector } from './theia-sprotty-connector'
import { injectable, inject } from "inversify"
import {GLSPServer} from 'glsp-sprotty/lib'

/**
 * A sprotty DiagramServer that can be connected to a Theia language
 * server.
 *
 * This class is the sprotty side of the Theia/sprotty integration. It
 * is instantiated with the DI container of the sprotty diagram. Theia
 * services are available via the TheiaDiagramServerConnector.
 */



@injectable()
export abstract class TheiaDiagramServer extends GLSPServer {
    protected sourceUri: string

    constructor(@inject(TYPES.IActionDispatcher) public actionDispatcher: IActionDispatcher,
        @inject(TYPES.ActionHandlerRegistry) actionHandlerRegistry: ActionHandlerRegistry,
        @inject(TYPES.ViewerOptions) viewerOptions: ViewerOptions,
        @inject(TYPES.SModelStorage) storage: SModelStorage,
        @inject(TYPES.ILogger) logger: ILogger) {
        super(actionDispatcher, actionHandlerRegistry, viewerOptions, storage, logger)

    }

    initialize(registry: ActionHandlerRegistry): void {
        super.initialize(registry)
        registry.register(SelectCommand.KIND, this)
    }

    handle(action: Action): void | ICommand {
        if (action instanceof RequestModelAction && action.options !== undefined)
            this.sourceUri = action.options.sourceUri
        return super.handle(action)
    }
    abstract handleExportSvgAction(action: ExportSvgAction): boolean
    protected abstract handleServerStateAction(status: ServerStatusAction): boolean
    /**
     * made public
     */
    messageReceived(message: ActionMessage) {
        super.messageReceived(message)
    }
}

@injectable()
export class TheiaLSPDiagramServer extends TheiaDiagramServer {

    protected connector: Promise<TheiaSprottyConnector>
    private resolveConnector: (server: TheiaSprottyConnector) => void

    constructor(@inject(TYPES.IActionDispatcher) public actionDispatcher: IActionDispatcher,
        @inject(TYPES.ActionHandlerRegistry) actionHandlerRegistry: ActionHandlerRegistry,
        @inject(TYPES.ViewerOptions) viewerOptions: ViewerOptions,
        @inject(TYPES.SModelStorage) storage: SModelStorage,
        @inject(TYPES.ILogger) logger: ILogger) {
        super(actionDispatcher, actionHandlerRegistry, viewerOptions, storage, logger)
        this.waitForConnector()
    }

    connect(connector: TheiaSprottyConnector): void {
        this.resolveConnector(connector)
    }

    disconnect(): void {
        this.waitForConnector()
    }

    private waitForConnector(): void {
        this.connector = new Promise<TheiaSprottyConnector>(resolve =>
            this.resolveConnector = resolve)
    }

    handleExportSvgAction(action: ExportSvgAction): boolean {
        this.connector.then(c => c.save(this.sourceUri, action))
        return true
    }

    protected handleServerStateAction(status: ServerStatusAction): boolean {
        this.connector.then(c => c.showStatus(this.clientId, status))
        return false
    }

    sendMessage(message: ActionMessage) {
        this.connector.then(c => c.sendThroughLsp(message))
    }
}

@injectable()
export class TheiaWebsocketDiagramServer extends TheiaDiagramServer {
    protected webSocket?: WebSocket;

    constructor(@inject(TYPES.IActionDispatcher) public actionDispatcher: IActionDispatcher,
        @inject(TYPES.ActionHandlerRegistry) actionHandlerRegistry: ActionHandlerRegistry,
        @inject(TYPES.ViewerOptions) viewerOptions: ViewerOptions,
        @inject(TYPES.SModelStorage) storage: SModelStorage,
        @inject(TYPES.ILogger) logger: ILogger) {
        super(actionDispatcher, actionHandlerRegistry, viewerOptions, storage, logger)
    }

    listen(webSocket: WebSocket): void {
        webSocket.addEventListener('message', event => {
            this.messageReceived(event.data);
        });
        webSocket.addEventListener('error', event => {
            this.logger.error(this, 'error event received', event);
        });

        this.webSocket = webSocket;
    }

    disconnect() {
        if (this.webSocket) {
            this.webSocket.close();
            this.webSocket = undefined;
        }
    }



    handleExportSvgAction(action: ExportSvgAction): boolean {
        throw new Error("Method not implemented.");
    }
    protected handleServerStateAction(status: ServerStatusAction): boolean {
        throw new Error("Method not implemented.");
    }
    protected sendMessage(message: ActionMessage): void {
        if (this.webSocket) {
            this.webSocket.send(JSON.stringify(message));
        } else {
            throw new Error('WebSocket is not connected');
        }
    }
}


@injectable()
export class GLSPLanguageContribution {
    public webSocket: WebSocket

}