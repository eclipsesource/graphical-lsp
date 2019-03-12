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
package com.eclipsesource.glsp.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.eclipsesource.glsp.api.di.GLSPModule;
import com.eclipsesource.glsp.api.factory.IModelFactory;
import com.eclipsesource.glsp.api.handler.IActionHandler;
import com.eclipsesource.glsp.api.jsonrpc.IGLSPServer;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.model.IModelStateProvider;
import com.eclipsesource.glsp.api.provider.IActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.IActionProvider;
import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.eclipsesource.glsp.api.provider.IOperationHandlerProvider;
import com.eclipsesource.glsp.api.provider.IServerCommandHandlerProvider;
import com.eclipsesource.glsp.server.actionhandler.CollapseExpandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ComputedBoundsActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ExecuteServerCommandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OpenActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OperationActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestCommandPaletteActionsHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestElementTypeHintsActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestMarkersHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestOperationsHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestPopupModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SaveModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SelectActionHandler;
import com.eclipsesource.glsp.server.model.FileBasedModelFactory;
import com.eclipsesource.glsp.server.model.IFileExtensionLoader;
import com.eclipsesource.glsp.server.model.ModelStateProvider;
import com.eclipsesource.glsp.server.provider.DefaultActionHandlerProvider;
import com.eclipsesource.glsp.server.provider.DefaultActionProvider;
import com.eclipsesource.glsp.server.provider.DefaultOperationHandlerProvider;
import com.eclipsesource.glsp.server.provider.DefaultServerCommandHandlerProvider;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;

public abstract class ServerModule extends GLSPModule {
	private Multibinder<IFileExtensionLoader<?>> modelExtensionHandler;

	protected final LinkedBindingBuilder<IFileExtensionLoader<?>> bindFileExtensionLoader() {
		return modelExtensionHandler.addBinding();
	}

	protected List<Class<? extends IFileExtensionLoader<?>>> bindFileExtensionLoaders() {
		return Collections.emptyList();
	}

	protected void multiBindFileExtensionLoader() {
	}

	protected void multiBindFileExtensions() {
	}

	@Override
	protected Class<? extends IModelStateProvider> bindModelStateProvider() {
		return ModelStateProvider.class;
	}

	@Override
	protected void configureMultibindings() {
		super.configureMultibindings();
		modelExtensionHandler = Multibinder.newSetBinder(binder(), new TypeLiteral<IFileExtensionLoader<?>>() {
		});
		bindFileExtensionLoaders().forEach(l -> bindFileExtensionLoader().to(l));
	}

	@Override
	protected Class<? extends IModelTypeConfigurationProvider> bindModelTypesConfigurationProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<? extends IActionProvider> bindActionProvider() {
		return DefaultActionProvider.class;
	}

	@Override
	protected Class<? extends IActionHandlerProvider> bindActionHandlerProvider() {
		return DefaultActionHandlerProvider.class;
	}

	@Override
	protected Class<? extends IOperationHandlerProvider> bindOperatioHandlerProvider() {
		return DefaultOperationHandlerProvider.class;
	}

	@Override
	protected Class<? extends IServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
		return DefaultServerCommandHandlerProvider.class;
	}

	@Override
	protected Class<? extends IModelFactory> bindModelFactory() {
		return FileBasedModelFactory.class;
	}

	@Override
	protected Class<? extends IGLSPServer> bindGLSPServer() {
		return GLSPServer.class;
	}

	@Override
	protected List<Class<? extends IActionHandler>> bindActionHandlers() {
		List<Class<? extends IActionHandler>> handlers = new ArrayList<>();
		handlers.add(CollapseExpandActionHandler.class);
		handlers.add(ComputedBoundsActionHandler.class);
		handlers.add(OpenActionHandler.class);
		handlers.add(OperationActionHandler.class);
		handlers.add(RequestModelActionHandler.class);
		handlers.add(RequestOperationsHandler.class);
		handlers.add(RequestPopupModelActionHandler.class);
		handlers.add(SaveModelActionHandler.class);
		handlers.add(SelectActionHandler.class);
		handlers.add(ExecuteServerCommandActionHandler.class);
		handlers.add(RequestElementTypeHintsActionHandler.class);
		handlers.add(RequestCommandPaletteActionsHandler.class);
		return handlers;
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
		bindActionHandler().to(RequestElementTypeHintsActionHandler.class);
		bindActionHandler().to(RequestCommandPaletteActionsHandler.class);
		bindActionHandler().to(RequestMarkersHandler.class);
	}

}
