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
package at.tortmayr.chillisp.api.impl;

import com.google.inject.Guice;
import com.google.inject.Injector;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.di.AbstractDIGraphicalServerProvider;
import at.tortmayr.chillisp.api.di.DefaultServerRuntimeModule;

public class DIGraphicalServerProvider extends AbstractDIGraphicalServerProvider {

	private DefaultServerRuntimeModule module;

	public DIGraphicalServerProvider(Class<? extends IGraphicalLanguageServer> serverClass,
			DefaultServerRuntimeModule module) {
		super(serverClass);
		this.module = module;
	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(module);
	}

}
