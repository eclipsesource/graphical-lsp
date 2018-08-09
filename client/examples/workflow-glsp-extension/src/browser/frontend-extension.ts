import { ContainerModule, interfaces } from "inversify";
import { WorkflowGLClientContribution } from "./workflow-gl-client-contribution";
import { LanguageClientContribution } from "@theia/languages/lib/browser"

export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    bind(WorkflowGLClientContribution).toSelf().inSingletonScope()
    bind(LanguageClientContribution).toDynamicValue(ctx => ctx.container.get(WorkflowGLClientContribution))


})

