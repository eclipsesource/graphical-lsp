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
import { Emitter, Event } from "@theia/core/lib/common";
import {
    Action,
    ActionHandlerRegistry,
    CollapseExpandAction,
    CollapseExpandAllAction,
    ComputedBoundsAction,
    ExecuteServerCommandAction,
    ExportSvgAction,
    IdentifiableRequestAction,
    ModelSource,
    OpenAction,
    OperationKind,
    RequestBoundsCommand,
    RequestCommandPaletteActions,
    RequestMarkersAction,
    RequestModelAction,
    RequestOperationsAction,
    RequestPopupModelAction,
    RequestTypeHintsAction,
    SaveModelAction,
    ServerStatusAction,
    SetTypeHintsAction,
    SwitchEditModeCommand
} from "glsp-sprotty/lib";
import { injectable } from "inversify";
import { TheiaDiagramServer } from "sprotty-theia/lib";

import { injectable } from "inversify";

@injectable()
export class GLSPTheiaDiagramServer extends TheiaDiagramServer implements NotifyingModelSource {
    readonly handledActionEventEmitter: Emitter<Action> = new Emitter<Action>();

    initialize(registry: ActionHandlerRegistry): void {
        registry.register(RequestOperationsAction.KIND, this);
        registry.register(SaveModelAction.KIND, this);
        registry.register(OperationKind.CREATE_CONNECTION, this);
        registry.register(OperationKind.RECONNECT_CONNECTION, this);
        registry.register(OperationKind.REROUTE_CONNECTION, this);
        registry.register(OperationKind.CREATE_NODE, this);
        registry.register(OperationKind.CHANGE_BOUNDS, this);
        registry.register(ExecuteServerCommandAction.KIND, this);
        registry.register(RequestTypeHintsAction.KIND, this);
        registry.register(SetTypeHintsAction.KIND, this);
        registry.register(OperationKind.CHANGE_CONTAINER, this)
        registry.register(OperationKind.DELETE_ELEMENT, this);
        registry.register(ComputedBoundsAction.KIND, this);
        registry.register(RequestBoundsCommand.KIND, this);
        registry.register(RequestPopupModelAction.KIND, this);
        registry.register(CollapseExpandAction.KIND, this);
        registry.register(CollapseExpandAllAction.KIND, this);
        registry.register(OpenAction.KIND, this);
        registry.register(ServerStatusAction.KIND, this);
        registry.register(RequestModelAction.KIND, this);
        registry.register(ExportSvgAction.KIND, this);
        registry.register(RequestCommandPaletteActions.KIND, this);
        registry.register(IdentifiableRequestAction.KIND, this);
        registry.register(RequestMarkersAction.KIND, this);


        // Register an empty handler for SwitchEditMode, to avoid runtime exceptions.
        // We don't want to support SwitchEditMode, but sprotty still sends some corresponding
        // actions.
        registry.register(SwitchEditModeCommand.KIND, { handle: action => undefined });
    }

    public getSourceURI(): string {
        return this.sourceUri;
    }

    get onHandledAction(): Event<Action> {
        return this.handledActionEventEmitter.event;
    }

    handle(action: Action) {
        this.handledActionEventEmitter.fire(action);
        return super.handle(action);
    }

}

export interface NotifyingModelSource extends ModelSource {
    readonly onHandledAction: Event<Action>;
}

export namespace NotifyingModelSource {
    export function is(arg: any): arg is NotifyingModelSource {
        return !!arg && ('onHandledAction' in arg);
    }
}
