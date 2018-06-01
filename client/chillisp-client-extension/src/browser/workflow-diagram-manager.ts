import { DiagramManagerImpl, TheiaSprottyConnector, TheiaFileSaver, DiagramWidgetRegistry } from "theia-sprotty/lib";
import { inject } from "inversify";
import { LanguageClientContribution } from "@theia/languages/lib/browser";
import { EditorManager } from "@theia/editor/lib/browser";
import { ThemeManager } from "./theme-manager";
import { DiagramServer } from "sprotty/lib";

export class WorkflowDiagramManager extends DiagramManagerImpl {
    
    readonly diagramType = 'workflow-diagram'
    readonly iconClass = 'fa fa-microchip'

    _diagramConnector: TheiaSprottyConnector

    constructor(@inject(TheiaFileSaver) theiaFileSaver: TheiaFileSaver,
                @inject(EditorManager) editorManager: EditorManager,
                @inject(DiagramWidgetRegistry) diagramWidgetRegistry: DiagramWidgetRegistry,
                @inject(ThemeManager) themeManager: ThemeManager) {
        super()
        themeManager.initialize()
        this._diagramConnector = new TheiaSprottyConnector(undefined, theiaFileSaver, editorManager, diagramWidgetRegistry)
    }
    protected createModelSource(widgetId: string, svgContainerId: string): DiagramServer;
    get diagramConnector() {
        return this._diagramConnector
    }

    get label() {
        return 'Yang diagram'
    }
}
}