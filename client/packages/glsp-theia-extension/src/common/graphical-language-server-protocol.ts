/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { NotificationType, RequestType0, NotificationType0 } from "vscode-jsonrpc";
import { ActionMessage } from "glsp-sprotty/lib";

export namespace ActionMessageNotification {
    export const type = new NotificationType<ActionMessage, void>('process')
}

export namespace ShutdownRequest {
    export const type = new RequestType0<void, void, void>('shutdown');
}

export namespace ExitNotification {
    export const type = new NotificationType0<void>('exit');
}
