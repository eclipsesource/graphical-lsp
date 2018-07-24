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
package at.tortmayr.chillisp.api.di;

import com.google.inject.Binder;

import at.tortmayr.chillisp.api.ActionHandler;
import at.tortmayr.chillisp.api.GraphicalLanguageServer;
import at.tortmayr.chillisp.api.GraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.GraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.GraphicalModelState;
import at.tortmayr.chillisp.api.ModelElementOpenListener;
import at.tortmayr.chillisp.api.ModelFactory;
import at.tortmayr.chillisp.api.PopupModelFactory;
import at.tortmayr.chillisp.api.ToolConfiguration;
import at.tortmayr.chillisp.api.impl.ActionHandlerImpl;
import at.tortmayr.chillisp.api.impl.GraphicalLanguageServerImpl;
import at.tortmayr.chillisp.api.impl.ModelStateImpl;
import io.typefox.sprotty.api.ILayoutEngine;

public abstract class ServerModule extends AbstractGenericModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
	}
	
	public Class<? extends GraphicalLanguageServer> bindGraphicalLanguageServer(){
		return GraphicalLanguageServerImpl.class;
	}

	public Class<? extends GraphicalModelState> bindGraphicalModelState() {
		return ModelStateImpl.class;
	}

	public Class<? extends ActionHandler> bindRequestActionHandler() {
		return ActionHandlerImpl.class;
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
