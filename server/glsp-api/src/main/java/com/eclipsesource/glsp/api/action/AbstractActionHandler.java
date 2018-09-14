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
package com.eclipsesource.glsp.api.action;

import java.util.Collection;
import java.util.Optional;

import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.model.ModelStateProvider;

public abstract class AbstractActionHandler implements ActionHandler {

	private ModelStateProvider modelStateProvider;

	protected abstract Collection<Action> handleableActionsKinds();

	public boolean canHandle(Action action) {

		return handleableActionsKinds().stream().anyMatch(
				hAction -> hAction.getKind().equals(action.getKind()) && hAction.getClass().equals(action.getClass()));
	}

	/**
	 * Processes and action and returns the response action which should be send to
	 * the client. If no response to the client is need a NoOpAction is returned
	 */
	public abstract Optional<Action> handle(Action action);

	public void setModelStateProvider(ModelStateProvider modelStateProvider) {
		this.modelStateProvider = modelStateProvider;
	}

	protected ModelState getModelState() {
		return modelStateProvider.getModelState();
	}

	@Override
	public int getPriority() {
		return Integer.MIN_VALUE;
	}

}
