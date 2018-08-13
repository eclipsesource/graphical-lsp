import { DiagramConfiguration, TheiaKeyTool, TheiaDiagramServer } from "theia-glsp/lib"
import { createWorkflowDiagramContainer } from "workflow-sprotty/lib"
import { TYPES, KeyTool } from "glsp-sprotty/lib"
import { Container, injectable } from "inversify";
import { } from "sprotty/lib";
import { WorkflowLanguage } from "../../common/workflow-language";

@injectable()
export class WorkflowDiagramConfiguration implements DiagramConfiguration {
    diagramType: string = WorkflowLanguage.DiagramType

    createContainer(widgetId: string): Container {
        const container = createWorkflowDiagramContainer(widgetId);
        container.bind(TYPES.ModelSource).to(TheiaDiagramServer)
        container.rebind(KeyTool).to(TheiaKeyTool).inSingletonScope()
        return container;
    }

}