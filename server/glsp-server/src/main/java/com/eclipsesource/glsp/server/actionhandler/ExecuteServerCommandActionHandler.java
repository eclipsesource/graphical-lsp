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

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ExecuteServerCommandAction;
import com.eclipsesource.glsp.api.handler.ServerCommandHandler;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.provider.ServerCommandHandlerProvider;
import com.google.inject.Inject;

public class ExecuteServerCommandActionHandler extends AbstractActionHandler {
	@Inject
	ServerCommandHandlerProvider commandHandlerProvider;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new ExecuteServerCommandAction());
	}

	@Override
	public Optional<Action> execute(Action action, ModelState modelState) {
		if (action instanceof ExecuteServerCommandAction) {
			ExecuteServerCommandAction commandAction = (ExecuteServerCommandAction) action;
			Optional<ServerCommandHandler> handler = commandHandlerProvider.getHandler(commandAction.getCommandId());
			if (handler.isPresent()) {
				return handler.get().execute(commandAction.getCommandId(), commandAction.getOptions(), modelState);
			}
		}
		return Optional.empty();
	}

}
