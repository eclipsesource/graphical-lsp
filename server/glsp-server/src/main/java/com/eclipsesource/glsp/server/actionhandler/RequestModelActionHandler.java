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

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.ClientOptions;
import com.eclipsesource.glsp.api.utils.ClientOptions.ParsedClientOptions;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.google.inject.Inject;

public class RequestModelActionHandler extends AbstractActionHandler {

	@Inject
	private ModelFactory modelFactory;

	@Inject
	private ModelSubmissionHandler submissionHandler;

	@Override
	public Optional<Action> execute(String clientId, Action action) {
		this.clientId = clientId;
		Optional<GraphicalModelState> modelState = modelStateProvider.getModelState(clientId);
		if (modelState.isPresent()) {
			return execute(action, modelState.get());
		}
		return execute(action, modelStateProvider.create(clientId));
	}

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		if (action instanceof RequestModelAction) {
			RequestModelAction requestAction = (RequestModelAction) action;
			ParsedClientOptions options = ClientOptions.parse(requestAction.getOptions());
			GModelRoot model = modelFactory.loadModel(requestAction);
			modelState.setRoot(model);
			modelState.setClientOptions(options);
			return submissionHandler.submit(false, modelState);
		}
		return Optional.empty();
	}

	@Override
	public boolean handles(Action action) {
		return action instanceof RequestModelAction;
	}

}
