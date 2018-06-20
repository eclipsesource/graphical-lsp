import { injectable, inject } from "inversify";
import { DiagramManagerImpl, DiagramWidget, TheiaLSPDiagramServer, } from "theia-sprotty/lib";
import URI from "@theia/core/lib/common/uri";
import { ModelSource, DiagramServer, TYPES, IActionDispatcher, } from "sprotty/lib";
import { GLSPLanguageContribution, TheiaWebsocketDiagramServer } from "./diagram-server";
import { ThemeManager } from "./theme-manager";


@injectable()
export class WorkflowDiagramManager extends DiagramManagerImpl {
    protected glspClient: GLSPLanguageContribution;
    readonly diagramType = 'workflow-diagram'
    readonly iconClass = 'fa fa-microchip'



    constructor(@inject(GLSPLanguageContribution) glspClient: GLSPLanguageContribution,
        @inject(ThemeManager) themeManager: ThemeManager) {
        super()
        this.glspClient=glspClient
        themeManager.initialize()
    }




    protected createDiagramWidget(uri: URI): DiagramWidget {
        const widgetId = this.widgetRegistry.nextId()
        const svgContainerId = widgetId + '_sprotty'
        const diagramConfiguration = this.diagramConfigurationRegistry.get(this.diagramType)
        const diContainer = diagramConfiguration.createContainer(svgContainerId)
        const modelSource = diContainer.get<ModelSource>(TYPES.ModelSource)
        if (modelSource instanceof DiagramServer)
            modelSource.clientId = widgetId
        if (modelSource instanceof TheiaLSPDiagramServer && this.diagramConnector)
            this.diagramConnector.connect(modelSource)
        else if (modelSource instanceof TheiaWebsocketDiagramServer) {
            modelSource.listen(this.glspClient.webSocket)
        }
        const newWidget = this.diagramWidgetFactory({
            id: widgetId, svgContainerId, uri, diagramType: this.diagramType, modelSource,
            actionDispatcher: diContainer.get<IActionDispatcher>(TYPES.IActionDispatcher)
        })
        newWidget.title.closable = true
        newWidget.title.label = uri.path.base
        newWidget.title.icon = this.iconClass
        this.widgetRegistry.addWidget(uri, this.diagramType, newWidget)
        newWidget.disposed.connect(() => {
            this.widgetRegistry.removeWidget(uri, this.diagramType)
            if (modelSource instanceof TheiaLSPDiagramServer && this.diagramConnector)
                this.diagramConnector.disconnect(modelSource)
        })
        return newWidget


    }


}