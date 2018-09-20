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

import javax.inject.Inject;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.OpenAction;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;

public class OpenActionHandler extends AbstractActionHandler {
	@Inject
	private ModelElementOpenListener modelElementOpenListener;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new OpenAction());
	}

	@Override
	public Optional<Action> handle(Action action) {
		if (action instanceof OpenAction) {
			if (modelElementOpenListener != null) {
				modelElementOpenListener.elementOpened((OpenAction) action);
			}
		}
		return Optional.empty();
	}

}
