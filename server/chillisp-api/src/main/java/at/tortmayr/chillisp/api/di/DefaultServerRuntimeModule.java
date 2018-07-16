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

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IGraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.IGraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.IGraphicalModelState;
import at.tortmayr.chillisp.api.IModelElementOpenListener;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.IPopupModelFactory;
import at.tortmayr.chillisp.api.IRequestActionHandler;
import at.tortmayr.chillisp.api.IToolConfiguration;
import at.tortmayr.chillisp.api.impl.DefaultGraphicalLanguageServer;
import at.tortmayr.chillisp.api.impl.DefaultModelState;
import at.tortmayr.chillisp.api.impl.DefaultRequestActionHandler;
import io.typefox.sprotty.api.ILayoutEngine;

public abstract class DefaultServerRuntimeModule extends AbstractGenericModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
	}

	public Class<? extends IGraphicalModelState> bindGraphicalModelState() {
		return DefaultModelState.class;
	}

	public Class<? extends IRequestActionHandler> bindRequestActionHandler() {
		return DefaultRequestActionHandler.class;
	}

	public Class<? extends IGraphicalLanguageServer> bindGraphicalLanguageServer() {
		return DefaultGraphicalLanguageServer.class;
	}

	public Class<? extends IModelFactory> bindModelFactory() {
		return IModelFactory.NullImpl.class;
	}

	public Class<? extends IGraphicalModelExpansionListener> bindGraphicalModelExpansionListener() {
		return IGraphicalModelExpansionListener.NullImpl.class;
	}

	public Class<? extends IGraphicalModelSelectionListener> bindGraphicalModelSelectionListener() {
		return IGraphicalModelSelectionListener.NullImpl.class;
	}

	public Class<? extends IModelElementOpenListener> bindModelElementOpenListener() {
		return IModelElementOpenListener.NullImpl.class;
	}

	public Class<? extends IPopupModelFactory> bindPopupModelFactory() {
		return IPopupModelFactory.NullImpl.class;
	}

	public Class<? extends IToolConfiguration> bindToolConfiguration() {
		return IToolConfiguration.NullImpl.class;
	}

	public Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ILayoutEngine.NullImpl.class;
	}
}
