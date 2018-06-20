
import { injectable, inject } from "inversify";
import { TheiaDiagramServer } from "theia-sprotty/lib";
import { ActionMessage, IActionDispatcher, TYPES, ViewerOptions, SModelStorage, ILogger, } from "sprotty/lib";
import { ExportSvgAction } from "sprotty/lib";
import { ServerStatusAction } from "sprotty/lib";
import { ActionHandlerRegistry } from "sprotty/lib";


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
export class GLSPLanguageContribution{
    public webSocket:WebSocket


}