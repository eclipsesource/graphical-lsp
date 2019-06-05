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
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.google.inject.Inject;

public class OperationActionHandler extends AbstractActionHandler {
	@Inject
	private OperationHandlerProvider operationHandlerProvider;
	@Inject
	private ModelSubmissionHandler submissionHandler;

	@Override
	public boolean handles(Action action) {
		return action instanceof AbstractOperationAction;
	}

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		switch (action.getKind()) {
		case Action.Kind.CREATE_NODE_OPERATION:
		case Action.Kind.CREATE_CONNECTION_OPERATION:
		case Action.Kind.RECONNECT_CONNECTION_OPERATION:
		case Action.Kind.REROUTE_CONNECTION_OPERATION:
		case Action.Kind.DELETE_ELEMENT_OPERATION:
		case Action.Kind.CHANGE_BOUNDS_OPERATION:
			return doHandle((AbstractOperationAction) action, modelState);
		default:
			return Optional.empty();
		}
	}

	public Optional<Action> doHandle(AbstractOperationAction action, GraphicalModelState modelState) {
		if (operationHandlerProvider.isHandled(action)) {
			OperationHandler handler = operationHandlerProvider.getHandler(action).get();
			Optional<GModelRoot> modelRoot = handler.execute(action, modelState);
			if (modelRoot.isPresent()) {
				return submissionHandler.submit(true, modelState);
			}
		}
		return Optional.empty();
	}

}
