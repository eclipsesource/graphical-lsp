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

import com.eclipsesource.glsp.api.action.ActionDispatcher;
import com.eclipsesource.glsp.api.diagram.DiagramConfigurationProvider;
import com.eclipsesource.glsp.api.factory.GraphGsonConfiguratorFactory;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.layout.ILayoutEngine;
import com.eclipsesource.glsp.api.markers.ModelValidator;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.ActionProvider;
import com.eclipsesource.glsp.api.provider.CommandPaletteActionProvider;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.eclipsesource.glsp.api.provider.ServerCommandHandlerProvider;
import com.eclipsesource.glsp.graph.GraphExtension;
import com.google.inject.AbstractModule;

public abstract class GLSPModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GLSPServer.class).to(bindGLSPServer());
		bind(PopupModelFactory.class).to(bindPopupModelFactory());
		bind(ModelFactory.class).to(bindModelFactory());
		bind(ModelSelectionListener.class).to(bindModelSelectionListener());
		bind(ModelExpansionListener.class).to(bindModelExpansionListener());
		bind(ModelElementOpenListener.class).to(bindModelElementOpenListener());
		bind(ILayoutEngine.class).to(bindLayoutEngine());
		bind(ActionProvider.class).to(bindActionProvider());
		bind(ActionHandlerProvider.class).to(bindActionHandlerProvider());
		bind(OperationHandlerProvider.class).to(bindOperatioHandlerProvider());
		bind(ServerCommandHandlerProvider.class).to(bindServerCommandHandlerProvider());
		bind(CommandPaletteActionProvider.class).to(bindCommandPaletteActionProvider());
		bind(ModelValidator.class).to(bindModelValidator());
		bind(ActionDispatcher.class).to(bindActionDispatcher());
		bind(DiagramConfigurationProvider.class).to(bindDiagramConfigurationProvider());
		bind(ModelStateProvider.class).to(bindModelStateProvider());
		bind(GraphGsonConfiguratorFactory.class).to(bindGraphGsonConfiguratorFactory());
		bind(GraphExtension.class).to(bindGraphExtension());
	}

	protected abstract Class<? extends ModelStateProvider> bindModelStateProvider();

	protected abstract Class<? extends DiagramConfigurationProvider> bindDiagramConfigurationProvider();

	protected abstract Class<? extends GLSPServer> bindGLSPServer();

	protected abstract Class<? extends GraphGsonConfiguratorFactory> bindGraphGsonConfiguratorFactory();

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

	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ILayoutEngine.NullImpl.class;
	}

	protected Class<? extends ServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
		return ServerCommandHandlerProvider.NullImpl.class;
	}

	protected Class<? extends ModelValidator> bindModelValidator() {
		return ModelValidator.NullImpl.class;
	}

	protected Class<? extends ActionDispatcher> bindActionDispatcher() {
		return ActionDispatcher.NullImpl.class;
	}

	protected Class<? extends GraphExtension> bindGraphExtension() {
		return null;
	}
}
