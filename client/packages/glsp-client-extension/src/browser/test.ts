import { injectable } from "inversify";
import { BaseGraphicalLanguageClientContribution } from "./graphical-langauge-client-contribution";



@injectable()
export class TestGLClientContribution extends BaseGraphicalLanguageClientContribution {
    readonly id: "testl"
    readonly name: "TestL"
}