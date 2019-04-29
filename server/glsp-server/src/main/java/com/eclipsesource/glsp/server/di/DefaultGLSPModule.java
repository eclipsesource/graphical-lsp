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
package com.eclipsesource.glsp.server.di;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.eclipsesource.glsp.api.di.GLSPModule;
import com.eclipsesource.glsp.api.diagram.DiagramManager;
import com.eclipsesource.glsp.api.diagram.DiagramManagerProvider;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.handler.ActionHandler;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.handler.ServerCommandHandler;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.ActionProvider;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.eclipsesource.glsp.api.provider.ServerCommandHandlerProvider;
import com.eclipsesource.glsp.server.actionhandler.CollapseExpandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ComputedBoundsActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ExecuteServerCommandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.LayoutActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OpenActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OperationActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestCommandPaletteActionsHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestMarkersHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestOperationsHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestPopupModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestTypeHintsActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SaveModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SelectActionHandler;
import com.eclipsesource.glsp.server.diagram.DefaultDiagramManagerProvider;
import com.eclipsesource.glsp.server.jsonrpc.DefaultGLSPServer;
import com.eclipsesource.glsp.server.model.DefaultModelStateProvider;
import com.eclipsesource.glsp.server.model.FileBasedModelFactory;
import com.eclipsesource.glsp.server.provider.DIActionHandlerProvider;
import com.eclipsesource.glsp.server.provider.DIOperationHandlerProvider;
import com.eclipsesource.glsp.server.provider.DIServerCommandHandlerProvider;
import com.eclipsesource.glsp.server.provider.DefaultActionProvider;
import com.google.inject.multibindings.Multibinder;

public abstract class DefaultGLSPModule extends GLSPModule {
	protected Multibinder<ActionHandler> actionHandlerBinder;
	protected Multibinder<ServerCommandHandler> serverCommandHandler;
	protected Multibinder<OperationHandler> operationHandler;
	protected Multibinder<DiagramManager> diagramHandler;

	@Override
	protected void configure() {
		super.configure();
		// Configure multibindings
		actionHandlerBinder = Multibinder.newSetBinder(binder(), ActionHandler.class);
		serverCommandHandler = Multibinder.newSetBinder(binder(), ServerCommandHandler.class);
		operationHandler = Multibinder.newSetBinder(binder(), OperationHandler.class);
		diagramHandler = Multibinder.newSetBinder(binder(), DiagramManager.class);
		bindActionHandlers().forEach(h -> actionHandlerBinder.addBinding().to(h));
		bindServerCommandHandlers().forEach(h -> serverCommandHandler.addBinding().to(h));
		bindOperationHandlers().forEach(h -> operationHandler.addBinding().to(h));
		bindDiagramManagers().forEach(h -> diagramHandler.addBinding().to(h));
	}

	@Override
	protected Class<? extends GLSPServer> bindGLSPServer() {
		return DefaultGLSPServer.class;
	}

	@Override
	protected Class<? extends ActionProvider> bindActionProvider() {
		return DefaultActionProvider.class;
	}

	@Override
	protected Class<? extends ActionHandlerProvider> bindActionHandlerProvider() {
		return DIActionHandlerProvider.class;
	}

	@Override
	protected Class<? extends OperationHandlerProvider> bindOperatioHandlerProvider() {
		return DIOperationHandlerProvider.class;
	}

	@Override
	protected Class<? extends ServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
		return DIServerCommandHandlerProvider.class;
	}

	@Override
	protected Class<? extends ModelFactory> bindModelFactory() {
		return FileBasedModelFactory.class;
	}

	@Override
	protected Class<? extends ModelStateProvider> bindModelStateProvider() {
		return DefaultModelStateProvider.class;
	}

	@Override
	protected Class<? extends DiagramManagerProvider> bindDiagramManagerProvider() {
		return DefaultDiagramManagerProvider.class;
	}

	protected abstract Collection<Class<? extends OperationHandler>> bindOperationHandlers();

	protected abstract Collection<Class<? extends DiagramManager>> bindDiagramManagers();

	protected Collection<Class<? extends ActionHandler>> bindActionHandlers() {
		return Arrays.asList(CollapseExpandActionHandler.class, ComputedBoundsActionHandler.class,
				OpenActionHandler.class, OperationActionHandler.class, RequestModelActionHandler.class,
				RequestOperationsHandler.class, RequestPopupModelActionHandler.class, SaveModelActionHandler.class,
				SelectActionHandler.class, ExecuteServerCommandActionHandler.class, RequestTypeHintsActionHandler.class,
				RequestCommandPaletteActionsHandler.class, RequestMarkersHandler.class, LayoutActionHandler.class);
	}

	protected Collection<Class<? extends ServerCommandHandler>> bindServerCommandHandlers() {
		return Collections.emptySet();
	}

}
