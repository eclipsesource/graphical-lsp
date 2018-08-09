import { NotificationType, RequestType0, NotificationType0 } from "vscode-jsonrpc";
import { ActionMessage } from "sprotty/lib";

export namespace ActionMessageNotification {
    export const type = new NotificationType<ActionMessage, void>('process')
}

export namespace ShutdownRequest {
    export const type = new RequestType0<void, void, void>('shutdown');
}

export namespace ExitNotification {
    export const type = new NotificationType0<void>('exit');
}
