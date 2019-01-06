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
import com.eclipsesource.glsp.api.action.kind.RequestTypeHints;
import com.eclipsesource.glsp.api.action.kind.SetTypeHintsAction;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.provider.ModelTypeConfigurationProvider;
import com.google.inject.Inject;

public class RequestElementTypeHintsActionHandler extends AbstractActionHandler {
	@Inject
	private ModelTypeConfigurationProvider typeConfigurationProvider;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new RequestTypeHints());
	}

	@Override
	public Optional<Action> execute(Action action, ModelState modelState) {
		if (action instanceof RequestTypeHints) {
			return Optional.of(new SetTypeHintsAction(typeConfigurationProvider.getNodeTypeHints(),
					typeConfigurationProvider.getEdgeTypeHints()));
		}
		return Optional.empty();
	}
}
