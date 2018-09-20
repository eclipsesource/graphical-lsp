/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server;

import com.eclipsesource.glsp.api.di.GLSPModule;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageServer;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.server.provider.DefaultActionHandlerProvider;
import com.eclipsesource.glsp.server.provider.DefaultActionProvider;

public abstract class ServerModule extends GLSPModule {

	@Override
	protected void bindActionProviders() {
		bindActionProvider().to(DefaultActionProvider.class);
	}

	@Override
	protected void bindActionHandlerProviders() {
		bindActionHandlerProvider().to(DefaultActionHandlerProvider.class);
	}
	

	protected Class<? extends GraphicalLanguageServer> bindGraphicalLanguageServer() {
		return DefaultGraphicalLanguageServer.class;
	}

	protected Class<? extends ModelState> bindModelState() {
		return ModelStateImpl.class;
	}

}
