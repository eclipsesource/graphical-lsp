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

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.factory.IModelFactory;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.model.IModelStateProvider;
import com.eclipsesource.glsp.api.utils.ModelOptions;
import com.eclipsesource.glsp.api.utils.ModelOptions.ParsedModelOptions;
import com.google.inject.Inject;

public class RequestModelActionHandler extends AbstractActionHandler {
	@Inject
	protected IModelFactory modelFactory;
	@Inject
	protected ModelSubmissionHandler submissionHandler;
	@Inject
	protected IModelStateProvider modelStateProvider;

	@Override
	public Optional<Action> execute(Action action, String clientId) {
		if (action instanceof RequestModelAction) {
			RequestModelAction requestAction = (RequestModelAction) action;
			ParsedModelOptions options = ModelOptions.parse(requestAction.getOptions());
			SModelRoot model = modelFactory.loadModel(requestAction, clientId);
			IModelState modelState=modelStateProvider.registerModel(model, clientId);
			modelState.setOptions(options);
			modelState.setClientId(clientId);
			return submissionHandler.submit(model, false, modelState);
		}
		return Optional.empty();
	}

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new RequestModelAction());
	}

}
