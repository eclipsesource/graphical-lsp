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
package com.eclipsesource.glsp.api.di;

import org.eclipse.sprotty.ILayoutEngine;

import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.handler.ActionHandler;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.handler.ServerCommandHandler;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.markers.ModelValidator;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.ActionProvider;
import com.eclipsesource.glsp.api.provider.CommandPaletteActionProvider;
import com.eclipsesource.glsp.api.provider.ModelTypeConfigurationProvider;
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
		bind(GLSPServer.class).to(bindGLSPServer());
		bind(PopupModelFactory.class).to(bindPopupModelFactory());
		bind(ModelFactory.class).to(bindModelFactory());
		bind(ModelSelectionListener.class).to(bindModelSelectionListener());
		bind(ModelExpansionListener.class).to(bindModelExpansionListener());
		bind(ModelElementOpenListener.class).to(bindModelElementOpenListener());
		bind(ILayoutEngine.class).to(bindLayoutEngine());
		bind(OperationConfiguration.class).to(bindOperationConfiguration());
		bind(ActionProvider.class).to(bindActionProvider());
		bind(ActionHandlerProvider.class).to(bindActionHandlerProvider());
		bind(OperationHandlerProvider.class).to(bindOperatioHandlerProvider());
		bind(ServerCommandHandlerProvider.class).to(bindServerCommandHandlerProvider());
		bind(ModelTypeConfigurationProvider.class).to(bindModelTypesConfigurationProvider());
		bind(CommandPaletteActionProvider.class).to(bindCommandPaletteActionProvider());
		bind(ModelValidator.class).to(bindModelValidator());
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

	protected abstract Class<? extends GLSPServer> bindGLSPServer();

	protected abstract Class<? extends ModelTypeConfigurationProvider> bindModelTypesConfigurationProvider();

	protected Class<? extends CommandPaletteActionProvider> bindCommandPaletteActionProvider() {
		return CommandPaletteActionProvider.NullImpl.class;
	}

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

	protected Class<? extends ServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
		return ServerCommandHandlerProvider.NullImpl.class;
	}
	
	protected Class<? extends ModelValidator> bindModelValidator() {
		return ModelValidator.NullImpl.class;
	}
}
