/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
import { injectable } from "inversify";
import {
    Action,
    ActionHandlerRegistry,
    CollapseExpandAction,
    CollapseExpandAllAction,
    ComputedBoundsAction,
    DiagramServer,
    ExportSvgAction,
    ICommand,
    LayoutAction,
    OpenAction,
    RequestBoundsCommand,
    RequestModelAction,
    RequestPopupModelAction,
    ServerStatusAction,
    SwitchEditModeCommand,
    WebSocketDiagramServer
} from "sprotty";

import { RequestCommandPaletteActions } from "../features/command-palette/action-definitions";
import { ExecuteServerCommandAction } from "../features/execute/execute-command";
import { RequestTypeHintsAction } from "../features/hints/action-definition";
import { OperationKind, RequestOperationsAction } from "../features/operation/set-operations";
import { IdentifiableRequestAction } from "../features/request-response/action-definitions";
import { SaveModelAction } from "../features/save/save";
import { RequestMarkersAction } from "../features/validation/validate";


@injectable()
export class GLSPWebsocketDiagramServer extends WebSocketDiagramServer {
    protected _sourceUri: string;

    initialize(registry: ActionHandlerRegistry): void {
        registerDefaultGLSPServerActions(registry, this);
        this.clientId = this.viewerOptions.baseDiv;
    }
    handle(action: Action): void | ICommand | Action {
        if (action instanceof RequestModelAction && action.options !== undefined)
            this._sourceUri = action.options.sourceUri;
        return super.handle(action);
    }

    public getSourceURI(): string {
        return this._sourceUri;
    }
}

export function registerDefaultGLSPServerActions(registry: ActionHandlerRegistry, diagramServer: DiagramServer) {
    registry.register(RequestOperationsAction.KIND, diagramServer);
    registry.register(SaveModelAction.KIND, diagramServer);
    registry.register(OperationKind.CREATE_CONNECTION, diagramServer);
    registry.register(OperationKind.RECONNECT_CONNECTION, diagramServer);
    registry.register(OperationKind.REROUTE_CONNECTION, diagramServer);
    registry.register(OperationKind.CREATE_NODE, diagramServer);
    registry.register(OperationKind.CHANGE_BOUNDS, diagramServer);
    registry.register(OperationKind.DELETE_ELEMENT, diagramServer);
    registry.register(ExecuteServerCommandAction.KIND, diagramServer);
    registry.register(RequestTypeHintsAction.KIND, diagramServer);
    registry.register(ComputedBoundsAction.KIND, diagramServer);
    registry.register(RequestBoundsCommand.KIND, diagramServer);
    registry.register(RequestPopupModelAction.KIND, diagramServer);
    registry.register(CollapseExpandAction.KIND, diagramServer);
    registry.register(CollapseExpandAllAction.KIND, diagramServer);
    registry.register(OpenAction.KIND, diagramServer);
    registry.register(ServerStatusAction.KIND, diagramServer);
    registry.register(RequestModelAction.KIND, diagramServer);
    registry.register(ExportSvgAction.KIND, diagramServer);
    registry.register(RequestCommandPaletteActions.KIND, diagramServer);
    registry.register(IdentifiableRequestAction.KIND, diagramServer);
    registry.register(RequestMarkersAction.KIND, diagramServer);
    registry.register(LayoutAction.KIND, diagramServer);

    // Register an empty handler for SwitchEditMode, to avoid runtime exceptions.
    // We don't want to support SwitchEditMode, but sprotty still sends some corresponding
    // actions.
    registry.register(SwitchEditModeCommand.KIND, { handle: action => undefined });
}
