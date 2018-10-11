/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server;

import com.eclipsesource.glsp.api.di.GLSPModule;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageServer;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.ActionProvider;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.eclipsesource.glsp.api.provider.ServerCommandHandlerProvider;
import com.eclipsesource.glsp.server.actionhandler.CollapseExpandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ComputedBoundsActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ExecuteServerCommandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OpenActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OperationActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestOperationsHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestPopupModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SaveModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SelectActionHandler;
import com.eclipsesource.glsp.server.model.FileBasedModelFactory;
import com.eclipsesource.glsp.server.provider.DefaultActionHandlerProvider;
import com.eclipsesource.glsp.server.provider.DefaultActionProvider;
import com.eclipsesource.glsp.server.provider.DefaultOperationHandlerProvider;
import com.eclipsesource.glsp.server.provider.DefaultServerCommandHandlerProvider;

public abstract class ServerModule extends GLSPModule {

	@Override
	protected Class<? extends ActionProvider> bindActionProvider() {
		return DefaultActionProvider.class;
	}

	@Override
	protected Class<? extends ActionHandlerProvider> bindActionHandlerProvider() {
		return DefaultActionHandlerProvider.class;
	}

	@Override
	protected Class<? extends OperationHandlerProvider> bindOperatioHandlerProvider() {
		return DefaultOperationHandlerProvider.class;
	}

	@Override
	protected Class<? extends ServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
		return DefaultServerCommandHandlerProvider.class;
	}

	@Override
	protected void multiBindOperationHandlers() {
	}

	@Override
	protected void multiBindServerCommandHandlers() {
	}

	@Override
	protected Class<? extends ModelFactory> bindModelFactory() {
		return FileBasedModelFactory.class;
	}

	@Override
	protected Class<? extends GraphicalLanguageServer> bindGraphicalLanguageServer() {
		return DefaultGraphicalLanguageServer.class;
	}

	@Override
	protected void multiBindActionHandlers() {
		bindActionHandler().to(CollapseExpandActionHandler.class);
		bindActionHandler().to(ComputedBoundsActionHandler.class);
		bindActionHandler().to(OpenActionHandler.class);
		bindActionHandler().to(OperationActionHandler.class);
		bindActionHandler().to(RequestModelActionHandler.class);
		bindActionHandler().to(RequestOperationsHandler.class);
		bindActionHandler().to(RequestPopupModelActionHandler.class);
		bindActionHandler().to(SaveModelActionHandler.class);
		bindActionHandler().to(SelectActionHandler.class);
		bindActionHandler().to(ExecuteServerCommandActionHandler.class);
	}

}
