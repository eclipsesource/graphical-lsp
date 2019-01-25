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
