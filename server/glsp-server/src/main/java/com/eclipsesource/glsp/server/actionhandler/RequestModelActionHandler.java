/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server.actionhandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.utils.ModelOptions;
import com.eclipsesource.glsp.api.utils.ModelOptions.ParsedModelOptions;
import com.google.inject.Inject;

import io.typefox.sprotty.api.SModelRoot;

public class RequestModelActionHandler extends AbstractActionHandler {

	@Inject
	private ModelFactory modelFactory;

	@Inject
	private ModelSubmissionHandler submissionHandler;


	@Override
	public Optional<Action> handle(Action action) {
		if (action instanceof RequestModelAction) {
			RequestModelAction requestAction = (RequestModelAction) action;
			ParsedModelOptions options = ModelOptions.parse(requestAction.getOptions());
			getModelState().setNeedsClientLayout(options.needsClientLayout());
			SModelRoot model = modelFactory.loadModel(requestAction);
			getModelState().setCurrentModel(model);
			getModelState().setOptions(options);
			return submissionHandler.handleSubmission(model, false, getModelState());
		}
		return Optional.empty();
	}

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new RequestModelAction());
	}

}
