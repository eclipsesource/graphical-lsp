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
import { Action, ActionHandlerRegistry, ModelSource, registerDefaultGLSPServerActions } from "@glsp/sprotty-client/lib";
import { Emitter, Event } from "@theia/core/lib/common";
import { injectable } from "inversify";
import { TheiaDiagramServer } from "sprotty-theia/lib";

@injectable()
export class GLSPTheiaDiagramServer extends TheiaDiagramServer implements NotifyingModelSource {
    readonly handledActionEventEmitter: Emitter<Action> = new Emitter<Action>();

    initialize(registry: ActionHandlerRegistry): void {
        registerDefaultGLSPServerActions(registry, this);
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
