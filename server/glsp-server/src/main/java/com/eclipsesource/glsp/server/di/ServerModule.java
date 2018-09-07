/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server.di;

import com.eclipsesource.glsp.api.action.ActionHandler;
import com.eclipsesource.glsp.api.factory.GraphicalModelState;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageServer;
import com.eclipsesource.glsp.api.listener.GraphicalModelExpansionListener;
import com.eclipsesource.glsp.api.listener.GraphicalModelSelectionListener;
import com.eclipsesource.glsp.api.listener.ModelElementOpenListener;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;
import com.eclipsesource.glsp.api.operations.OperationHandlerProvider;
import com.eclipsesource.glsp.server.DefaultActionHandler;
import com.eclipsesource.glsp.server.DefaultGraphicalLanguageServer;
import com.eclipsesource.glsp.server.ModelStateImpl;
import com.google.inject.Binder;

import io.typefox.sprotty.api.ILayoutEngine;

public abstract class ServerModule extends AbstractGenericModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
	}
	
	public Class<? extends GraphicalLanguageServer> bindGraphicalLanguageServer(){
		return DefaultGraphicalLanguageServer.class;
	}

	public Class<? extends GraphicalModelState> bindGraphicalModelState() {
		return ModelStateImpl.class;
	}

	public Class<? extends ActionHandler> bindRequestActionHandler() {
		return DefaultActionHandler.class;
	}

	public Class<? extends ModelFactory> bindModelFactory() {
		return ModelFactory.NullImpl.class;
	}

	public Class<? extends GraphicalModelExpansionListener> bindGraphicalModelExpansionListener() {
		return GraphicalModelExpansionListener.NullImpl.class;
	}

	public Class<? extends GraphicalModelSelectionListener> bindGraphicalModelSelectionListener() {
		return GraphicalModelSelectionListener.NullImpl.class;
	}

	public Class<? extends ModelElementOpenListener> bindModelElementOpenListener() {
		return ModelElementOpenListener.NullImpl.class;
	}

	public Class<? extends PopupModelFactory> bindPopupModelFactory() {
		return PopupModelFactory.NullImpl.class;
	}

	public Class<? extends OperationConfiguration> bindOperationConfiguration() {
		return OperationConfiguration.NullOperationConfiguration.class;
	}
	
	public Class<? extends OperationHandlerProvider> bindOperationHandlerProvider() {
		return OperationHandlerProvider.NullOperationHandlerProvider.class;
	}

	public Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ILayoutEngine.NullImpl.class;
	}
}
