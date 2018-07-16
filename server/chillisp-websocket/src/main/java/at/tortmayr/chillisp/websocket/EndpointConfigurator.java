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
package at.tortmayr.chillisp.websocket;

import java.util.function.Consumer;

import javax.websocket.server.ServerEndpointConfig;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer.Provider;

public class EndpointConfigurator extends ServerEndpointConfig.Configurator {

	private Provider provider;

	public EndpointConfigurator(IGraphicalLanguageServer.Provider provider) {
		this.provider = provider;
	}


	@Override
	public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
		T instance = super.getEndpointInstance(endpointClass);
		if (instance instanceof GraphicalLanguageServerEndpoint) {
			GraphicalLanguageServerEndpoint endpoint = (GraphicalLanguageServerEndpoint) instance;
			endpoint.setExceptionHandler(new ExceptionLogger());
			endpoint.setGraphicalLanguageServerProvider(provider);
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
