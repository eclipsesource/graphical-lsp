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
package com.eclipsesource.glsp.server.actionhandler;

import java.util.Optional;

import org.eclipse.sprotty.ServerStatus;
import org.eclipse.sprotty.ServerStatus.Severity;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ServerStatusAction;
import com.eclipsesource.glsp.api.handler.ActionHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.google.inject.Inject;

public abstract class AbstractActionHandler implements ActionHandler {
	@Inject
	protected ModelStateProvider modelStateProvider;
	protected String clientId;

	/**
	 * Processes and action and returns the response action which should be send to
	 * the client. If no response to the client is need a NoOpAction is returned
	 */
	@Override
	public Optional<Action> execute(String clientId, Action action) {
		this.clientId = clientId;
		Optional<GraphicalModelState> modelState = modelStateProvider.getModelState(clientId);
		if (modelState.isPresent()) {
			return execute(action, modelState.get());
		}
		ServerStatus status = new ServerStatus(Severity.FATAL,
				"Could not retrieve the model state for client with id '" + clientId + "'");
		return Optional.of(new ServerStatusAction(status));
	}

	protected abstract Optional<Action> execute(Action action, GraphicalModelState modelState);
}
