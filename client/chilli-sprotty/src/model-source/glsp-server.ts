import { injectable } from "inversify";
import { DiagramServer, ActionHandlerRegistry } from "sprotty/lib";
import { SaveModelAction } from "../features/save/save";

@injectable()
export abstract class GLSPServer extends DiagramServer {
    protected initialize(registry: ActionHandlerRegistry): void {
        super.initialize(registry);

        registry.register(SaveModelAction.KIND, this)
    }
}