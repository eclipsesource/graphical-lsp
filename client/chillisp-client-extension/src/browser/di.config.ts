import { DiagramConfiguration, TheiaKeyTool } from "theia-sprotty/lib"
import { createWorkflowDiagramContainer } from "workflow-sprotty/lib"
import { KeyTool, TYPES } from 'sprotty/lib'
import { Container } from "inversify";
import { TheiaWebsocketDiagramServer } from "./diagram-server";
export class WorkflowDiagramContribution implements DiagramConfiguration {
    diagramType: string = "workflow-diagram"

    createContainer(widgetId: string): Container {
        const container = createWorkflowDiagramContainer(widgetId);
        container.bind(TYPES.ModelSource).to(TheiaWebsocketDiagramServer)
        container.rebind(KeyTool).to(TheiaKeyTool).inSingletonScope();
        return container;
    }

}