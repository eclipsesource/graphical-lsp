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

import org.eclipse.sprotty.ILayoutEngine;

import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.handler.ActionHandler;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.handler.ServerCommandHandler;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageServer;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;
import com.eclipsesource.glsp.api.model.ModelTypeConfiguration;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.ActionProvider;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.eclipsesource.glsp.api.provider.ServerCommandHandlerProvider;
import com.google.inject.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;

public abstract class GLSPModule extends AbstractModule {
	private Multibinder<ActionHandler> actionHandlerBinder;
	private Multibinder<ServerCommandHandler> serverCommandHandler;
	private Multibinder<OperationHandler> operationHandler;

	@Override
	protected final void configure() {
		bind(GraphicalLanguageServer.class).to(bindGraphicalLanguageServer());
		bind(PopupModelFactory.class).to(bindPopupModelFactory());
		bind(ModelFactory.class).to(bindModelFactory());
		bind(ModelSelectionListener.class).to(bindModelSelectionListener());
		bind(ModelExpansionListener.class).to(bindModelExpansionListener());
		bind(ModelElementOpenListener.class).to(bindModelElementOpenListener());
		bind(ILayoutEngine.class).to(bindLayoutEngine());
		bind(OperationConfiguration.class).to(bindOperationConfiguration());
		bind(ModelTypeConfiguration.class).to(bindModelTypeConfiguration());
		bind(ActionProvider.class).to(bindActionProvider());
		bind(ActionHandlerProvider.class).to(bindActionHandlerProvider());
		bind(OperationHandlerProvider.class).to(bindOperatioHandlerProvider());
		bind(ServerCommandHandlerProvider.class).to(bindServerCommandHandlerProvider());
		configureMultibindings();
	}

	protected void configureMultibindings() {
		actionHandlerBinder = Multibinder.newSetBinder(binder(), ActionHandler.class);
		serverCommandHandler = Multibinder.newSetBinder(binder(), ServerCommandHandler.class);
		operationHandler = Multibinder.newSetBinder(binder(), OperationHandler.class);
		multiBindActionHandlers();
		multiBindServerCommandHandlers();
		multiBindOperationHandlers();
	}

	protected abstract void multiBindOperationHandlers();

	protected abstract void multiBindServerCommandHandlers();

	protected abstract void multiBindActionHandlers();

	protected final LinkedBindingBuilder<ActionHandler> bindActionHandler() {
		return actionHandlerBinder.addBinding();
	}

	protected final LinkedBindingBuilder<ServerCommandHandler> bindServerCommandHandler() {
		return serverCommandHandler.addBinding();
	}

	protected final LinkedBindingBuilder<OperationHandler> bindOperationHandler() {
		return operationHandler.addBinding();
	}

	protected abstract Class<? extends GraphicalLanguageServer> bindGraphicalLanguageServer();

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

	protected Class<? extends ServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
		return ServerCommandHandlerProvider.NullImpl.class;
	}
}
