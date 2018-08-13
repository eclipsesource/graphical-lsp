import { ContainerModule } from "inversify";
import { LanguageServerContribution } from "@theia/languages/lib/node";
import { WorkflowGLServerContribution } from "./workflow-gl-server-contribution";

export default new ContainerModule(bind => {
    bind(LanguageServerContribution).to(WorkflowGLServerContribution).inSingletonScope()
})