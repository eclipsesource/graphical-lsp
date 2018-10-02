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
package com.eclipsesource.glsp.api.di;

import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageServer;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.model.ModelTypeConfiguration;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.ActionProvider;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.eclipsesource.glsp.api.provider.ServerCommandHandlerProvider;
import com.google.inject.AbstractModule;

import io.typefox.sprotty.api.ILayoutEngine;

public abstract class GLSPModule extends AbstractModule {

	@Override
	protected final void configure() {
		bind(GraphicalLanguageServer.class).to(bindGraphicalLanguageServer());
		bind(PopupModelFactory.class).to(bindPopupModelFactory());
		bind(ModelFactory.class).to(bindModelFactory());
		bind(ModelState.class).to(bindModelState());
		bind(ModelSelectionListener.class).to(bindModelSelectionListener());
		bind(ModelExpansionListener.class).to(bindModelExpansionListener());
		bind(ModelElementOpenListener.class).to(bindModelElementOpenListener());
		bind(ILayoutEngine.class).to(bindLayoutEngine());
		bind(OperationConfiguration.class).to(bindOperationConfiguration());
		bind(ModelTypeConfiguration.class).to(bindModelTypeConfiguration());
		bind(ActionProvider.class).to(bindActionProvider());
		bind(ActionHandlerProvider.class).to(bindActionHandlerProvider());
		bind(OperationHandlerProvider.class).to(bindOperatioHandlerProvider());
		bind(ServerCommandHandlerProvider.class).to(bindServerCommandHandler());
	}


	protected abstract Class<? extends GraphicalLanguageServer> bindGraphicalLanguageServer();

	protected abstract Class<? extends ModelState> bindModelState();

	protected Class<? extends ActionProvider> bindActionProvider() {
		return ActionProvider.NullImpl.class;
	}

	protected Class<? extends ActionHandlerProvider> bindActionHandlerProvider() {
		return ActionHandlerProvider.NullImpl.class;
	}

	protected Class<? extends OperationHandlerProvider> bindOperatioHandlerProvider() {
		return OperationHandlerProvider.NullImpl.class;
	}

	protected Class<? extends ModelExpansionListener> bindModelExpansionListener() {
		return ModelExpansionListener.NullImpl.class;
	}

	protected Class<? extends ModelFactory> bindModelFactory() {
		return ModelFactory.NullImpl.class;
	}

	protected Class<? extends ModelSelectionListener> bindModelSelectionListener() {
		return ModelSelectionListener.NullImpl.class;
	}

	protected Class<? extends ModelElementOpenListener> bindModelElementOpenListener() {
		return ModelElementOpenListener.NullImpl.class;
	}

	protected Class<? extends PopupModelFactory> bindPopupModelFactory() {
		return PopupModelFactory.NullImpl.class;
	}

	protected Class<? extends OperationConfiguration> bindOperationConfiguration() {
		return OperationConfiguration.NullOperationConfiguration.class;
	}

	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ILayoutEngine.NullImpl.class;
	}

	protected Class<? extends ModelTypeConfiguration> bindModelTypeConfiguration() {
		return ModelTypeConfiguration.NullImpl.class;
	}

	protected  Class<? extends ServerCommandHandlerProvider> bindServerCommandHandler(){
		return ServerCommandHandlerProvider.NullImpl.class;
	}
}
