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
package com.eclipsesource.glsp.server.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.model.IModelStateProvider;

public class ModelStateProvider implements IModelStateProvider {

	private Map<String, IModelState> modelStates;

	public ModelStateProvider() {
		modelStates = new HashMap<String, IModelState>();
	}

	@Override
	public synchronized IModelState getModelState(String clientId) {
		return this.modelStates.get(clientId);
	}

	@Override
	public IModelState registerModel(SModelRoot model, String clientId) {
		IModelState modelState = new ModelState();
		modelState.setCurrentModel(model);
		modelStates.put(clientId, modelState);
		return modelState;
	}

}
