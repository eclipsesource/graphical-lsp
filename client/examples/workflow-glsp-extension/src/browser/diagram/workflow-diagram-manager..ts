import { injectable, inject } from "inversify";
import { DiagramManagerImpl, TheiaFileSaver, DiagramWidgetRegistry } from "theia-glsp/lib";
import { WorkflowLanguage } from "../../common/workflow-language";
import { GLSPTheiaSprottyConnector } from "glsp-client-extension/lib/browser";
import { WorkflowGLClientContribution } from "../language/workflow-gl-client-contribution";
import { GraphicalLanguageClientContribution } from "glsp-client-extension/lib/browser";
import { EditorManager } from "@theia/editor/lib/browser";
import { ThemeManager } from "./thememanager";
@injectable()
export class WorkflowDiagramManager extends DiagramManagerImpl {
    readonly diagramType = WorkflowLanguage.DiagramType;
    readonly iconClass = "fa fa-project-diagram";
    readonly label = WorkflowLanguage.Label;
    private _diagramConnector: GLSPTheiaSprottyConnector;
    constructor(
        @inject(WorkflowGLClientContribution)
        readonly languageClientContribution: WorkflowGLClientContribution,
        @inject(TheiaFileSaver)
        readonly theiaFileSaver: TheiaFileSaver,
        @inject(EditorManager)
        readonly editorManager: EditorManager,
        @inject(DiagramWidgetRegistry)
        readonly diagramWidgetRegistry: DiagramWidgetRegistry,
        @inject(ThemeManager)
        readonly themeManager: ThemeManager) {
        super();

    }

    get diagramConnector() {
        if (!this._diagramConnector) {
            this._diagramConnector = new GLSPTheiaSprottyConnector(
                this.languageClientContribution,
                this.theiaFileSaver,
                this.editorManager,
                this.diagramWidgetRegistry);
            this.themeManager.initialize();

        }
        return this._diagramConnector
    }
}