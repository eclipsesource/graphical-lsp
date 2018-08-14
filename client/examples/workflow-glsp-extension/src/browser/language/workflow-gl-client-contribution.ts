import { injectable } from "inversify";
import { BaseGraphicalLanguageClientContribution } from "glsp-theia-extension/lib/browser"
import { WorkflowLanguage } from "../../common/workflow-language";
@injectable()
export class WorkflowGLClientContribution extends BaseGraphicalLanguageClientContribution {
    readonly id = WorkflowLanguage.Id
    readonly name = WorkflowLanguage.Name
}