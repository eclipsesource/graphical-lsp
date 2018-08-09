import { injectable } from "inversify";
import { BaseGraphicalLanguageClientContribution } from "glsp-client-extension/lib/browser"
@injectable()
export class WorkflowGLClientContribution extends BaseGraphicalLanguageClientContribution {
    readonly id = 'workflow'
    readonly name = 'Workflow'
}