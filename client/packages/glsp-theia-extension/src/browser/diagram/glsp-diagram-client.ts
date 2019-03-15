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
import { ActionMessage } from "sprotty/lib";
import { ActionMessageNotification } from "../../common";
import { ActionMessageReceiver } from "sprotty-theia/lib/theia/languageserver/diagram-language-client";
import { ApplicationShell } from "@theia/core/lib/browser";
import { CommandRegistry } from "@theia/core";
import { EditorManager } from "@theia/editor/lib/browser";
import { GLSPClient } from "../language/glsp-client-services";
import { GLSPClientContribution } from "../language/glsp-client-contribution";

import { inject } from "inversify";
import { injectable } from "inversify";

@injectable()
export class GLSPDiagramClient {

    actionMessageReceivers: ActionMessageReceiver[] = [];

    @inject(ApplicationShell) readonly shell: ApplicationShell;
    @inject(CommandRegistry) readonly commandsRegistry: CommandRegistry;

    constructor(readonly glspClientContribution: GLSPClientContribution,
        readonly editorManager: EditorManager) {
        this.glspClientContribution.glspClient.then(
            gc => {
                gc.onNotification(ActionMessageNotification.type, this.onMessageReceived.bind(this));
            }
        ).catch(
            err => console.error(err)
        );
    }

    sendThroughLsp(message: ActionMessage) {
        this.glspClientContribution.glspClient.then(gc =>
            gc.onReady().then(() =>
                gc.sendNotification(ActionMessageNotification.type, message)
            )
        );
    }

    onMessageReceived(message: ActionMessage) {
        this.actionMessageReceivers.forEach(client => {
            client.onMessageReceived(message);
        });
    }

    get glspClient(): Promise<GLSPClient> {
        return this.glspClientContribution.glspClient;
    }

    didClose(clientId: string) {
        // this.glspClient.then(gc => gc.stop())
    }

    connect(client: ActionMessageReceiver) {
        this.actionMessageReceivers.push(client);
    }

    disconnect(client: ActionMessageReceiver) {
        const index = this.actionMessageReceivers.indexOf(client);
        if (index >= 0) {
            this.actionMessageReceivers.splice(index);
        }
    }
}
