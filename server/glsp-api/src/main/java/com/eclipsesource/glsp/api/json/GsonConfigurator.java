/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.json;

import org.eclipse.sprotty.server.json.EnumTypeAdapter;

import com.eclipsesource.glsp.api.action.ActionRegistry;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.inject.Inject;

public class GsonConfigurator {

	private ActionRegistry actionRegistry;

	@Inject
	public GsonConfigurator(ActionRegistry actionRegistry) {
		this.actionRegistry = actionRegistry;
	}

	protected TypeAdapterFactory getActionTypeAdapterFactory() {
		return new ActionTypeAdapter.Factory(actionRegistry.getAllActions());
	}

	public GsonBuilder configureGsonBuilder(GsonBuilder gsonBuilder) {
		gsonBuilder.registerTypeAdapterFactory(getActionTypeAdapterFactory());
		gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
		return gsonBuilder;
	}

}
