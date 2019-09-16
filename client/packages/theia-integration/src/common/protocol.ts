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
import { ActionMessage } from "@glsp/sprotty-client/lib";
import { NotificationType, NotificationType0, RequestType, RequestType0 } from "vscode-jsonrpc";


export interface InitializeParameters {
    options?: any
}

export namespace ActionMessageNotification {
    export const type = new NotificationType<ActionMessage, void>('process');
}

export namespace InitializeRequest {
    export const type = new RequestType<InitializeParameters, Boolean, void, void>('initialize');
}

export namespace ShutdownRequest {
    export const type = new RequestType0<void, void, void>('shutdown');
}

export namespace ExitNotification {
    export const type = new NotificationType0<void>('exit');
}
