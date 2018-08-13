
import { TheiaSprottyConnector, TheiaDiagramServer, OpenInTextEditorMessage, TheiaFileSaver, DiagramWidgetRegistry } from "theia-glsp/lib";
import { ExportSvgAction, ServerStatusAction, ActionMessage } from "glsp-sprotty/lib";
import { GraphicalLanguageClientContribution } from "../language/graphical-langauge-client-contribution";
import { EditorManager } from "@theia/editor/lib/browser";
import URI from "@theia/core/lib/common/uri";
import { ActionMessageNotification } from "../../common/";

export class GLSPTheiaSprottyConnector implements TheiaSprottyConnector {

    private servers: TheiaDiagramServer[] = []

    constructor(private graphicalLanguageClientContribution: GraphicalLanguageClientContribution,
        private fileSaver: TheiaFileSaver,
        private editorManager: EditorManager,
        private diagramWidgetRegistry: DiagramWidgetRegistry) {

        this.graphicalLanguageClientContribution.languageClient.then(
            lc => {
                lc.onNotification(ActionMessageNotification.type, this.onMessageReceived.bind(this))
            }
        ).catch(
            err => console.error(err)
        )
    }

    connect(diagramServer: TheiaDiagramServer): void {
        this.servers.push(diagramServer)
        diagramServer.connect(this)
    }
    disconnect(diagramServer: TheiaDiagramServer): void {
        const index = this.servers.indexOf(diagramServer)
        if (index >= 0)
            this.servers.splice(index, 0)
        diagramServer.disconnect()
        this.graphicalLanguageClientContribution.languageClient.then(lc => lc.stop())
    }
    save(uri: string, action: ExportSvgAction): void {
        this.fileSaver.save(uri, action)
    }

    openInTextEditor(message: OpenInTextEditorMessage): void {
        const uri = new URI(message.location.uri)
        if (!message.forceOpen) {
            this.editorManager.all.forEach(editorWidget => {
                const currentTextEditor = editorWidget.editor
                if (editorWidget.isVisible && uri.toString() === currentTextEditor.uri.toString()) {
                    currentTextEditor.cursor = message.location.range.start
                    currentTextEditor.revealRange(message.location.range)
                    currentTextEditor.selection = message.location.range
                }
            })
        } else {
            this.editorManager.open(uri).then(
                editorWidget => {
                    const editor = editorWidget.editor
                    editor.cursor = message.location.range.start
                    editor.revealRange(message.location.range)
                    editor.selection = message.location.range
                })
        }
    }
    showStatus(widgetId: string, status: ServerStatusAction): void {
        const widget = this.diagramWidgetRegistry.getWidgetById(widgetId)
        if (widget)
            widget.setStatus(status)
    }
    sendMessage(message: ActionMessage): void {
        this.graphicalLanguageClientContribution.languageClient.then(lc => lc.sendNotification(ActionMessageNotification.type, message))
    }
    onMessageReceived(message: ActionMessage): void {
        this.servers.forEach(element => {
            element.messageReceived(message)
        }
        )
    }
}
