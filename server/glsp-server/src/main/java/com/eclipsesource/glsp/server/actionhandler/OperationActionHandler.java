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

import javax.inject.Inject;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ChangeBoundsOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.action.kind.DeleteElementOperationAction;
import com.eclipsesource.glsp.api.action.kind.ReconnectConnectionOperationAction;
import com.eclipsesource.glsp.api.action.kind.RerouteConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.provider.IOperationHandlerProvider;

public class OperationActionHandler extends AbstractActionHandler {
	@Inject
	protected IOperationHandlerProvider operationHandlerProvider;
	@Inject
	protected ModelSubmissionHandler submissionHandler;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new CreateNodeOperationAction(), new CreateConnectionOperationAction(),
				new DeleteElementOperationAction(), new ChangeBoundsOperationAction(),
				new ReconnectConnectionOperationAction(), new RerouteConnectionOperationAction());
	}

	@Override
	public Optional<Action> execute(Action action, String clientId) {
		switch (action.getKind()) {
		case Action.Kind.CREATE_NODE_OPERATION:
		case Action.Kind.CREATE_CONNECTION_OPERATION:
		case Action.Kind.RECONNECT_CONNECTION_OPERATION:
		case Action.Kind.REROUTE_CONNECTION_OPERATION:
		case Action.Kind.DELETE_ELEMENT_OPERATION:
		case Action.Kind.CHANGE_BOUNDS_OPERATION:
			return doHandle((Action) action, getModelState(clientId));
		default:
			return Optional.empty();
		}
	}

	public Optional<Action> doHandle(Action action, IModelState modelState) {
		if (operationHandlerProvider.isHandled(action)) {
			IOperationHandler handler = operationHandlerProvider.getHandler(action).get();
			Optional<SModelRoot> modelRoot = handler.execute(action, modelState);
			if (modelRoot.isPresent()) {
				return submissionHandler.submit(modelRoot.get(), true, modelState);
			}
		}
		return Optional.empty();
	}

}
