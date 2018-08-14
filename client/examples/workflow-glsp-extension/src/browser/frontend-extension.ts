import { ContainerModule, interfaces } from "inversify";
import { LanguageClientContribution } from "@theia/languages/lib/browser"
import { WorkflowGLClientContribution } from "./language/workflow-gl-client-contribution";
import { DiagramConfiguration, DiagramManagerProvider, DiagramManager } from "theia-sprotty/lib";
import { WorkflowDiagramConfiguration } from "./diagram/di.config";
import { WorkflowDiagramManager } from "./diagram/workflow-diagram-manager.";
import { WorkflowLanguage } from "../common/workflow-language";
import { FrontendApplicationContribution, OpenHandler } from "@theia/core/lib/browser";
import { ThemeManager } from "./diagram/thememanager";

export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    bind(WorkflowGLClientContribution).toSelf().inSingletonScope()
    bind(LanguageClientContribution).toDynamicValue(ctx => ctx.container.get(WorkflowGLClientContribution)).inSingletonScope();

    bind(DiagramConfiguration).to(WorkflowDiagramConfiguration).inSingletonScope()
    bind(DiagramManagerProvider).toProvider<DiagramManager>(context => {
        return () => {
            return new Promise<DiagramManager>((resolve) =>
                resolve(context.container.get(WorkflowDiagramManager))
            )
        }
    }).whenTargetNamed(WorkflowLanguage.DiagramType)

    bind(WorkflowDiagramManager).toSelf().inSingletonScope()
    bind(FrontendApplicationContribution).toDynamicValue(context =>
        context.container.get(WorkflowDiagramManager))
    bind(OpenHandler).toDynamicValue(context => context.container.get(WorkflowDiagramManager))
    bind(ThemeManager).toSelf().inSingletonScope()


})

