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
	private Multibinder<IActionHandler> actionHandlerBinder;
	private Multibinder<IServerCommandHandler> serverCommandHandler;
	private Multibinder<IOperationHandler> operationHandler;

	@Override
	protected final void configure() {
		bind(IGLSPServer.class).to(bindGLSPServer());
		bind(IPopupModelFactory.class).to(bindPopupModelFactory());
		bind(IModelFactory.class).to(bindModelFactory());
		bind(IModelSelectionListener.class).to(bindModelSelectionListener());
		bind(IModelExpansionListener.class).to(bindModelExpansionListener());
		bind(IModelElementOpenListener.class).to(bindModelElementOpenListener());
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
		actionHandlerBinder = Multibinder.newSetBinder(binder(), IActionHandler.class);
		serverCommandHandler = Multibinder.newSetBinder(binder(), IServerCommandHandler.class);
		operationHandler = Multibinder.newSetBinder(binder(), IOperationHandler.class);
		multiBindActionHandlers();
		multiBindServerCommandHandlers();
		multiBindOperationHandlers();
	}

	protected abstract void multiBindOperationHandlers();

	protected abstract void multiBindServerCommandHandlers();

	protected abstract void multiBindActionHandlers();
	
	protected final LinkedBindingBuilder<IActionHandler> bindActionHandler() {
		return actionHandlerBinder.addBinding();
	}

	protected final LinkedBindingBuilder<IServerCommandHandler> bindServerCommandHandler() {
		return serverCommandHandler.addBinding();
	}

	protected final LinkedBindingBuilder<IOperationHandler> bindOperationHandler() {
		return operationHandler.addBinding();
	}

	protected abstract Class<? extends GLSPServer> bindGLSPServer();

	protected abstract Class<? extends ModelTypeConfigurationProvider> bindModelTypesConfigurationProvider();

	protected Class<? extends CommandPaletteActionProvider> bindCommandPaletteActionProvider() {
		return CommandPaletteActionProvider.NullImpl.class;
	}

	protected Class<? extends IActionProvider> bindActionProvider() {
		return IActionProvider.NullImpl.class;
	}

	protected Class<? extends IActionHandlerProvider> bindActionHandlerProvider() {
		return IActionHandlerProvider.NullImpl.class;
	}

	protected Class<? extends IOperationHandlerProvider> bindOperatioHandlerProvider() {
		return IOperationHandlerProvider.NullImpl.class;
	}

	protected Class<? extends IModelExpansionListener> bindModelExpansionListener() {
		return IModelExpansionListener.NullImpl.class;
	}

	protected Class<? extends IModelFactory> bindModelFactory() {
		return IModelFactory.NullImpl.class;
	}

	protected Class<? extends IModelSelectionListener> bindModelSelectionListener() {
		return IModelSelectionListener.NullImpl.class;
	}

	protected Class<? extends IModelElementOpenListener> bindModelElementOpenListener() {
		return IModelElementOpenListener.NullImpl.class;
	}

	protected Class<? extends IPopupModelFactory> bindPopupModelFactory() {
		return IPopupModelFactory.NullImpl.class;
	}

	protected Class<? extends IOperationConfiguration> bindOperationConfiguration() {
		return IOperationConfiguration.NullOperationConfiguration.class;
	}

	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ILayoutEngine.NullImpl.class;
	}

	protected Class<? extends IServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
		return IServerCommandHandlerProvider.NullImpl.class;
	}
	
	protected Class<? extends ModelValidator> bindModelValidator() {
		return ModelValidator.NullImpl.class;
	}
}
