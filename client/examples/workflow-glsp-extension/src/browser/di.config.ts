import { DiagramConfiguration, TheiaKeyTool } from "theia-glsp/lib"
import { createWorkflowDiagramContainer } from "workflow-sprotty/lib"

import { Container, injectable } from "inversify";
import { TheiaWebsocketDiagramServer } from "theia-glsp/lib"
import { TYPES, KeyTool } from "sprotty/lib";

@injectable()
export class WorkflowDiagramConfiguration implements DiagramConfiguration {
    diagramType: string = "workflow-diagram"

    createContainer(widgetId: string): Container {
        const container = createWorkflowDiagramContainer(widgetId);
        container.bind(TYPES.ModelSource).to(TheiaWebsocketDiagramServer)
        container.rebind(KeyTool).to(TheiaKeyTool).inSingletonScope()
        return container;
    }

}