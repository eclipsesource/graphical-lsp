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
import com.eclipsesource.glsp.api.action.kind.RequestOperationsAction;
import com.eclipsesource.glsp.api.action.kind.SetOperationsAction;
import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;
import com.google.inject.Inject;

public class RequestOperationsHandler extends AbstractActionHandler {
	@Inject
	private OperationConfiguration operationConfiguration;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new RequestOperationsAction());
	}

	@Override
	public Optional<Action> handle(Action action) {
		if (action instanceof RequestOperationsAction) {
			RequestOperationsAction requestAction = (RequestOperationsAction) action;
			Optional<Operation[]> operations = Optional.ofNullable(operationConfiguration)
					.map(config -> config.getOperations(requestAction));
			return Optional.of(new SetOperationsAction(operations.orElse(new Operation[0])));
		}
		return Optional.empty();
	}

}
