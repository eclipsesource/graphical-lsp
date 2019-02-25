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
import { IConnection } from "@theia/languages/lib/node";
import { BaseGLSPServerContribution } from 'glsp-theia-extension/lib/node';
import { injectable } from "inversify";
import * as net from 'net';
import { createSocketConnection } from 'vscode-ws-jsonrpc/lib/server';
import { EcoreLanguage } from "../common/ecore-language";

function getPort(): number | undefined {
    let arg = process.argv.filter(arg => arg.startsWith('--ECORE_LSP='))[0]
    if (!arg) {
        return undefined
    } else {
        return Number.parseInt(arg.substring('--ECORE_LSP='.length), 10)
    }
}
@injectable()
export class EcoreGLServerContribution extends BaseGLSPServerContribution {
    readonly id = EcoreLanguage.Id
    readonly name = EcoreLanguage.Name

    serverStarted = false

    readonly description = {
        id: 'ecore',
        name: 'Ecore',
        documentSelector: ['ecore'],
        fileEvents: [
            '**/*.ecorediagram'
        ]
    }

    start(clientConnection: IConnection): void {
        console.log('[EcoreGL] Start Server for Client Connection.')
        let socketPort = getPort();
        if (socketPort) {
            // if (!this.serverStarted) {
            //     const command = 'java';
            //     const jarPath = '~/.glsp-workflow/workflow-example-0.0.1-SNAPSHOT-glsp.jar';
            //     const args: string[] = [];
            //     args.push(
            //         '-jar', jarPath
            //     );

            //     console.log('[EcoreGL] Spawn Server Process from ' + jarPath + '.')
            //     const child = spawn(command, args, {
            //         detached: true,
            //         shell: true,
            //         stdio: 'inherit'
            //     });
            //     child.unref();
            // }

            const socket = new net.Socket()
            console.log('[EcoreGL] Create Socket Connection at ' + socketPort + '.')
            const serverConnection = createSocketConnection(socket, socket, () => {
                console.log('[EcoreGL] Socket Connection Disposed.')
                socket.destroy()
            });
            console.log('[EcoreGL] Forward Client Connections.')
            this.forward(clientConnection, serverConnection)
            socket.connect(socketPort)
            this.serverStarted = true;
            console.log('[EcoreGL] Client Connection Started.')
        } else {
            console.log('[EcoreGL] Unable to connect to Workflow Graphical Language Server: No Socket Port.')
        }
    }
}