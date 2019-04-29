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
package com.eclipsesource.glsp.api.websocket;

import java.util.function.Consumer;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import com.eclipsesource.glsp.api.action.ActionMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GLSPServerEndpoint extends Endpoint implements Consumer<ActionMessage> {
	private Session session;
	private Consumer<Exception> exceptionHandler;

	private WebsocketGLSPServer glspServer;
	private Gson gson;

	public GLSPServerEndpoint() {
	}

	protected Session getSession() {
		return session;
	}

	public void setGson(Gson gson) {
		assert (gson != null);
		this.gson = gson;
	}

	public void setGLSPServer(WebsocketGLSPServer glpsServer) {
		this.glspServer = glpsServer;
	}

	public void setExceptionHandler(Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	protected void fireError(Exception message) {
		exceptionHandler.accept(message);
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		session.addMessageHandler(new ActionMessageHandler());

	}

	@Override
	public void accept(ActionMessage message) {
		String json = gson.toJson(message, ActionMessage.class);
		session.getAsyncRemote().sendText(json);

	}

	protected void fireMessageReceived(ActionMessage message) {
		if (glspServer != null) {
			if (!this.equals(glspServer.getRemoteEndpoint())) {
				glspServer.setRemoteEndpoint(this);
			}
			glspServer.process(message);
		}
	}

	class ActionMessageHandler implements MessageHandler.Whole<String> {

		@Override
		public void onMessage(String message) {
			try {
				ActionMessage actionMessage = gson.fromJson(message, ActionMessage.class);
				if (actionMessage.getAction() == null) {
					throw new IllegalArgumentException("Property 'action' must be set.");
				} else {
					fireMessageReceived(actionMessage);
				}
			} catch (JsonSyntaxException | IllegalArgumentException ex) {
				fireError(ex);
			}
		}
	}
}