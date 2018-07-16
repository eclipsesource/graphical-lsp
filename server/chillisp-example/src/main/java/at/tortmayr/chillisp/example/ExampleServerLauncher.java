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
package at.tortmayr.chillisp.example;

import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import at.tortmayr.chillisp.api.impl.DIGraphicalServerProvider;
import at.tortmayr.chillisp.api.impl.DefaultGraphicalLanguageServer;
import at.tortmayr.chillisp.websocket.EndpointConfigurator;
import at.tortmayr.chillisp.websocket.LoggingServerEndpoint;
import io.typefox.sprotty.layout.ElkLayoutEngine;

public class ExampleServerLauncher {

	public static void main(String[] args) {
		try {
	
			
			Server server = new Server(8080);
			ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
			contextHandler.setContextPath("/");
			server.setHandler(contextHandler);
			ServerContainer container = WebSocketServerContainerInitializer.configureContext(contextHandler);
			ElkLayoutEngine.initialize(new LayeredOptions());

			ServerEndpointConfig.Builder builder = ServerEndpointConfig.Builder.create(LoggingServerEndpoint.class,
					"/diagram");
			builder = builder.configurator(new EndpointConfigurator(
					new DIGraphicalServerProvider(DefaultGraphicalLanguageServer.class, new WorkflowServerModule())));
			ServerEndpointConfig config = builder.build();
			container.addEndpoint(config);

			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
