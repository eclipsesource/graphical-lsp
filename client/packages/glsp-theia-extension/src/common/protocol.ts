/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { ActionMessage } from "glsp-sprotty/lib";
import { NotificationType, NotificationType0, RequestType0 } from "vscode-jsonrpc";

export namespace ActionMessageNotification {
    export const type = new NotificationType<ActionMessage, void>('process')
}

export namespace ShutdownRequest {
    export const type = new RequestType0<void, void, void>('shutdown');
}

export namespace ExitNotification {
    export const type = new NotificationType0<void>('exit');
}
