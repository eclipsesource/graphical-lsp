/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.server.websocket;

import java.util.function.Consumer;

import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.websocket.GLSPServerEndpoint;
import com.eclipsesource.glsp.api.websocket.WebsocketGLSPServer;
import com.eclipsesource.glsp.server.jsonrpc.DefaultGLSPServer;

public class DefaultWebsocketGLSPServer extends DefaultGLSPServer implements WebsocketGLSPServer {
	private GLSPServerEndpoint endpoint;

	public DefaultWebsocketGLSPServer() {
		connect(new WebsocketClient());
	}

	public void setRemoteEndpoint(GLSPServerEndpoint endpoint) {
		this.endpoint = endpoint;
	}

	public GLSPServerEndpoint getRemoteEndpoint() {
		return this.endpoint;
	}

	private final class WebsocketClient implements GLSPClient {

		@Override
		public void process(ActionMessage message) {
			Consumer<ActionMessage> remoteEndpoint = getRemoteEndpoint();
			if (remoteEndpoint != null) {
				remoteEndpoint.accept(message);
			}
		}
	}
}
