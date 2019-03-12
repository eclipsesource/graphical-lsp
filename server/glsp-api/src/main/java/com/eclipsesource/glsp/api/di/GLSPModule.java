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

import java.util.Collections;
import java.util.List;

import org.eclipse.sprotty.ILayoutEngine;

import com.eclipsesource.glsp.api.factory.IModelFactory;
import com.eclipsesource.glsp.api.factory.IPopupModelFactory;
import com.eclipsesource.glsp.api.handler.IActionHandler;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.handler.IServerCommandHandler;
import com.eclipsesource.glsp.api.jsonrpc.IGLSPServer;
import com.eclipsesource.glsp.api.language.IGraphicaLanguage;
import com.eclipsesource.glsp.api.model.IModelElementOpenListener;
import com.eclipsesource.glsp.api.model.IModelExpansionListener;
import com.eclipsesource.glsp.api.model.IModelSelectionListener;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.model.IModelStateProvider;
import com.eclipsesource.glsp.api.model.ISaveModelDelegator;
import com.eclipsesource.glsp.api.operations.IOperationConfiguration;
import com.eclipsesource.glsp.api.provider.IActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.IActionProvider;
import com.eclipsesource.glsp.api.provider.ICommandPaletteActionProvider;
import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.eclipsesource.glsp.api.provider.IOperationHandlerProvider;
import com.eclipsesource.glsp.api.provider.IServerCommandHandlerProvider;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;

public abstract class GLSPModule extends AbstractModule {
	private Multibinder<IActionHandler> actionHandlerBinder;
	private Multibinder<IServerCommandHandler> serverCommandHandler;
	private Multibinder<IOperationHandler> operationHandler;

	@Override
	protected void configure() {
		bind(IGLSPServer.class).to(bindGLSPServer());
		bind(IPopupModelFactory.class).to(bindPopupModelFactory());
		bind(IModelFactory.class).to(bindModelFactory());
		bind(IModelSelectionListener.class).to(bindModelSelectionListener());
		bind(IModelExpansionListener.class).to(bindModelExpansionListener());
		bind(IModelElementOpenListener.class).to(bindModelElementOpenListener());
		bind(ILayoutEngine.class).to(bindLayoutEngine());
		bind(IOperationConfiguration.class).to(bindOperationConfiguration());
		bind(IActionProvider.class).to(bindActionProvider());
		bind(IActionHandlerProvider.class).to(bindActionHandlerProvider());
		bind(IOperationHandlerProvider.class).to(bindOperatioHandlerProvider());
		bind(IServerCommandHandlerProvider.class).to(bindServerCommandHandlerProvider());
		bind(IModelTypeConfigurationProvider.class).to(bindModelTypesConfigurationProvider());
		bind(ICommandPaletteActionProvider.class).to(bindCommandPaletteActionProvider());
		bind(ISaveModelDelegator.class).to(bindSaveModelDelegator());
		bind(IModelStateProvider.class).to(bindModelStateProvider()).asEagerSingleton();
		bind(IGraphicaLanguage.class).to(bindGraphicalLanguage());
		configureMultibindings();
	}

	protected void configureMultibindings() {
		actionHandlerBinder = Multibinder.newSetBinder(binder(), IActionHandler.class);
		serverCommandHandler = Multibinder.newSetBinder(binder(), IServerCommandHandler.class);
		operationHandler = Multibinder.newSetBinder(binder(), IOperationHandler.class);
		bindActionHandlers().forEach(ha -> bindActionHandler().to(ha));
		bindServerCommandHandlers().forEach(ha -> bindServerCommandHandler().to(ha));
		bindOperationsHandlers().forEach(ha -> bindOperationHandler().to(ha));
	}

	protected abstract List<Class<? extends IOperationHandler>> bindOperationsHandlers();

	protected List<Class<? extends IServerCommandHandler>> bindServerCommandHandlers() {
		return Collections.emptyList();
	}

	protected abstract List<Class<? extends IActionHandler>> bindActionHandlers();

	protected final LinkedBindingBuilder<IActionHandler> bindActionHandler() {
		return actionHandlerBinder.addBinding();
	}

	protected final LinkedBindingBuilder<IServerCommandHandler> bindServerCommandHandler() {
		return serverCommandHandler.addBinding();
	}

	protected final LinkedBindingBuilder<IOperationHandler> bindOperationHandler() {
		return operationHandler.addBinding();
	}

	protected abstract Class<? extends IGLSPServer> bindGLSPServer();

	protected abstract Class<? extends IModelTypeConfigurationProvider> bindModelTypesConfigurationProvider();

	protected Class<? extends ICommandPaletteActionProvider> bindCommandPaletteActionProvider() {
		return ICommandPaletteActionProvider.NullImpl.class;
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

	protected Class<? extends ISaveModelDelegator> bindSaveModelDelegator() {
		return ISaveModelDelegator.NullImpl.class;
	}

	protected abstract Class<? extends IModelStateProvider> bindModelStateProvider();

	protected abstract Class<? extends IGraphicaLanguage> bindGraphicalLanguage();

}
