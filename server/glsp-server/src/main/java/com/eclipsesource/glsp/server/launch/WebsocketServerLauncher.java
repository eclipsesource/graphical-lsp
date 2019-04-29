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
package com.eclipsesource.glsp.server.launch;

import java.net.InetSocketAddress;

import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import com.eclipsesource.glsp.api.di.GLSPModule;
import com.eclipsesource.glsp.api.json.GsonConfigurator;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.websocket.WebsocketGLSPServer;
import com.eclipsesource.glsp.server.websocket.EndpointConfigurator;
import com.eclipsesource.glsp.server.websocket.LoggingServerEndpoint;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class WebsocketServerLauncher extends AbstractServerLauncher {
	private static Logger logger = Logger.getLogger(WebsocketServerLauncher.class);

	public WebsocketServerLauncher(String host, int port, GLSPModule module) {
		super(host, port, module);
	}

	public void run() {
		try {
			Server server = new Server(new InetSocketAddress(getHost(), getPort()));
			ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
			contextHandler.setContextPath("/");
			server.setHandler(contextHandler);
			ServerContainer container = WebSocketServerContainerInitializer.configureContext(contextHandler);
			ServerEndpointConfig.Builder builder = ServerEndpointConfig.Builder.create(LoggingServerEndpoint.class,
					"/process");

			Injector injector = Guice.createInjector(getModule());
			GLSPServer glspServer = injector.getInstance(GLSPServer.class);
			if (glspServer instanceof WebsocketGLSPServer) {
				GsonConfigurator gsonConfigurator = injector.getInstance(GsonConfigurator.class);
				builder = builder
						.configurator(new EndpointConfigurator((WebsocketGLSPServer) glspServer, gsonConfigurator));
				ServerEndpointConfig config = builder.build();
				container.addEndpoint(config);
				server.start();
				server.join();
			} else {
				logger.error("No matching binding for WebsocketGLSPServer was found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
