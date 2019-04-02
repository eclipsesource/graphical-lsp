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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestOperationsAction;
import com.eclipsesource.glsp.api.action.kind.SetOperationsAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;
import com.google.inject.Inject;

public class RequestOperationsHandler extends AbstractActionHandler {
	@Inject
	private OperationConfiguration operationConfiguration;

	@Override
	public boolean handles(Action action) {
		return action instanceof RequestOperationsAction;
	}

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		if (action instanceof RequestOperationsAction) {
			RequestOperationsAction requestAction = (RequestOperationsAction) action;
			Optional<List<Operation>> operations = Optional.ofNullable(operationConfiguration)
					.map(config -> config.getOperations(requestAction));
			return Optional.of(new SetOperationsAction(operations.orElse(Collections.emptyList())));
		}
		return Optional.empty();
	}

}
