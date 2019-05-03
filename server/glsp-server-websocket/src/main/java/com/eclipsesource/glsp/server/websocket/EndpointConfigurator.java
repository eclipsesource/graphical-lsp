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

import javax.websocket.server.ServerEndpointConfig;

import com.eclipsesource.glsp.api.json.GsonConfigurator;
import com.eclipsesource.glsp.api.websocket.GLSPServerEndpoint;
import com.eclipsesource.glsp.api.websocket.WebsocketGLSPServer;
import com.google.gson.GsonBuilder;

public class EndpointConfigurator extends ServerEndpointConfig.Configurator {

	private WebsocketGLSPServer server;
	private GsonConfigurator gsonConfigurator;

	public EndpointConfigurator(WebsocketGLSPServer server, GsonConfigurator gsonConfigurator) {
		this.server = server;
		this.gsonConfigurator = gsonConfigurator;
	}

	@Override
	public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
		T instance = super.getEndpointInstance(endpointClass);
		if (instance instanceof GLSPServerEndpoint) {
			GLSPServerEndpoint endpoint = (GLSPServerEndpoint) instance;
			endpoint.setExceptionHandler(new ExceptionLogger());
			endpoint.setGLSPServer(server);
			endpoint.setGson(gsonConfigurator.configureGsonBuilder(new GsonBuilder()).create());
		}
		return instance;
	}

	public class ExceptionLogger implements Consumer<Exception> {

		@Override
		public void accept(Exception e) {
			System.err.println(e);

		}
	}
}