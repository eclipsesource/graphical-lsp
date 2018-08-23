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
package at.tortmayr.glsp.server.di;

import com.google.inject.Binder;

import at.tortmayr.glsp.api.action.ActionHandler;
import at.tortmayr.glsp.api.factory.GraphicalModelState;
import at.tortmayr.glsp.api.factory.ModelFactory;
import at.tortmayr.glsp.api.factory.PopupModelFactory;
import at.tortmayr.glsp.api.jsonrpc.GraphicalLanguageServer;
import at.tortmayr.glsp.api.listener.GraphicalModelExpansionListener;
import at.tortmayr.glsp.api.listener.GraphicalModelSelectionListener;
import at.tortmayr.glsp.api.listener.ModelElementOpenListener;
import at.tortmayr.glsp.api.tool.ToolConfiguration;
import at.tortmayr.glsp.server.DefaultActionHandler;
import at.tortmayr.glsp.server.DefaultGraphicalLanguageServer;
import at.tortmayr.glsp.server.ModelStateImpl;
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

	public Class<? extends ToolConfiguration> bindToolConfiguration() {
		return ToolConfiguration.NullImpl.class;
	}

	public Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ILayoutEngine.NullImpl.class;
	}
}
