import { ContainerModule, interfaces } from "inversify";
import { DiagramConfiguration, DiagramManagerProvider, DiagramManager } from "theia-sprotty/lib";
import { WorkflowDiagramConfiguration } from "./di.config";
import { WorkflowDiagramManager } from "./workflow-diagram-manager";
import { FrontendApplicationContribution, OpenHandler } from '@theia/core/lib/browser'
import { GLSPLanguageContribution } from "./diagram-server";
import { ThemeManager } from "./theme-manager";

const WebSocket = require("reconnecting-websocket");


export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    bind(DiagramConfiguration).to(WorkflowDiagramConfiguration).inSingletonScope()
    bind(DiagramManagerProvider).toProvider<DiagramManager>(context => {
        return () => {
            return new Promise<DiagramManager>((resolve) =>
                resolve(context.container.get(WorkflowDiagramManager))
            )
        }
    }).whenTargetNamed('workflow-diagram')
    let test= new GLSPLanguageContribution()
    test.webSocket= new WebSocket("ws://localhost:8080/diagram")
    bind(GLSPLanguageContribution).toConstantValue(test).whenInjectedInto(WorkflowDiagramManager)
    bind(WorkflowDiagramManager).toSelf().inSingletonScope()
    bind(FrontendApplicationContribution).toDynamicValue(context => 
        context.container.get(WorkflowDiagramManager))
    bind(OpenHandler).toDynamicValue(context => context.container.get(WorkflowDiagramManager))
    bind(ThemeManager).toSelf().inSingletonScope()



})

