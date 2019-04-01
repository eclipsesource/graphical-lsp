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
package com.eclipsesource.glsp.api.action;

import java.util.Collection;
import java.util.Optional;

import com.eclipsesource.glsp.api.handler.IActionHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.model.IModelStateProvider;
import com.google.inject.Inject;

public abstract class AbstractActionHandler implements IActionHandler {

	@Inject
	private IModelStateProvider modelStateProvider;

	protected IModelState getModelState(String clientId) {
		return modelStateProvider.getModelState(clientId);
	}

	protected abstract Collection<Action> handleableActionsKinds();

	public boolean handles(Action action) {

		return handleableActionsKinds().stream().anyMatch(
				hAction -> hAction.getKind().equals(action.getKind()) && hAction.getClass().equals(action.getClass()));
	}

	/**
	 * Processes and action and returns the response action which should be send to
	 * the client. If no response to the client is need a NoOpAction is returned
	 */
	public abstract Optional<Action> execute(Action action, String clientIdc);

}
