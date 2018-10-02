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
package com.eclipsesource.glsp.server.actionhandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.inject.Inject;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ActionKind;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.action.kind.DeleteElementOperationAction;
import com.eclipsesource.glsp.api.action.kind.ExecuteOperationAction;
import com.eclipsesource.glsp.api.action.kind.MoveOperationAction;
import com.eclipsesource.glsp.api.operations.OperationHandler;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;

import io.typefox.sprotty.api.SModelRoot;

public class OperationActionHandler extends AbstractActionHandler {
	@Inject
	private OperationHandlerProvider operationHandlerProvider;
	@Inject
	private ModelSubmissionHandler submissionHandler;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new CreateNodeOperationAction(), new CreateConnectionOperationAction(),
				new DeleteElementOperationAction(), new MoveOperationAction());
	}

	@Override
	public Optional<Action> execute(Action action) {
		switch (action.getKind()) {
		case ActionKind.CREATE_NODE_OPERATION:
		case ActionKind.CREATE_CONNECTION_OPERATION:
		case ActionKind.DELETE_ELEMENT_OPERATION:
		case ActionKind.MOVE_OPERATION:
			return doHandle((ExecuteOperationAction) action);
		default:
			return Optional.empty();
		}
	}

	public Optional<Action> doHandle(ExecuteOperationAction action) {
		if (operationHandlerProvider.isHandled(action)) {
			OperationHandler handler = operationHandlerProvider.getOperationHandler(action).get();
			Optional<SModelRoot> modelRoot = handler.execute(action, getModelState());
			if (modelRoot.isPresent()) {
				return submissionHandler.handleSubmission(modelRoot.get(), false, getModelState());
			}
		}

		return Optional.empty();
	}

}
