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

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestOperationsAction;
import com.eclipsesource.glsp.api.action.kind.SetOperationsAction;
import com.eclipsesource.glsp.api.operations.IOperationConfiguration;
import com.eclipsesource.glsp.api.operations.Operation;
import com.google.inject.Inject;

public class RequestOperationsHandler extends AbstractActionHandler {
	@Inject
	protected IOperationConfiguration operationConfiguration;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new RequestOperationsAction());
	}

	@Override
	public Optional<Action> execute(Action action, String clientId) {
		if (action instanceof RequestOperationsAction) {
			RequestOperationsAction requestAction = (RequestOperationsAction) action;
			Optional<Operation[]> operations = Optional.ofNullable(operationConfiguration)
					.map(config -> config.getOperations(requestAction));
			return Optional.of(new SetOperationsAction(operations.orElse(new Operation[0])));
		}
		return Optional.empty();
	}

}
