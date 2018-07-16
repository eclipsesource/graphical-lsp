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
package at.tortmayr.chillisp.api.di;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Injector;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;

public abstract class AbstractDIGraphicalServerProvider implements IGraphicalLanguageServer.Provider {
	private Injector injector;
	private Map<String, IGraphicalLanguageServer> servers;
	private Class<? extends IGraphicalLanguageServer> serverClass;

	public AbstractDIGraphicalServerProvider(Class<? extends IGraphicalLanguageServer> serverClass) {
		this.serverClass = serverClass;
		servers = new ConcurrentHashMap<>();
	}

	public abstract Injector createInjector();

	@Override
	public IGraphicalLanguageServer getGraphicalLanguageServer(String clientId) {
		IGraphicalLanguageServer server = servers.get(clientId);
		if (server == null) {
			server = getInjector().getInstance(serverClass);
			server.setClientId(clientId);
			servers.put(clientId, server);
		}
		return server;
	}

	public Injector getInjector() {
		if (injector == null) {
			injector = createInjector();
		}
		return injector;
	}
}
