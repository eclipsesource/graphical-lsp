import { LanguageServerContribution, BaseLanguageServerContribution } from '@theia/languages/lib/node'
import { injectable } from 'inversify';
export const GraphicalLanguageServerContribution = Symbol('GraphicalLanguageServerContribution')
export interface GraphicalLanguageServerContribution extends LanguageServerContribution { }

@injectable()
export abstract class BaseGraphicalLanguageServerContribution extends BaseLanguageServerContribution implements GraphicalLanguageServerContribution {

}