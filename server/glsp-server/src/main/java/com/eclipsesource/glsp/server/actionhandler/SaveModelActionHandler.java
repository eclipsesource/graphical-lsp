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
import com.eclipsesource.glsp.api.action.kind.ActionKind;
import com.eclipsesource.glsp.api.action.kind.SaveModelAction;
import com.eclipsesource.glsp.api.factory.FileBasedModelFactory;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.google.inject.Inject;

public class SaveModelActionHandler extends AbstractActionHandler {
	@Inject
	ModelFactory modelFactory;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new SaveModelAction());
	}

	@Override
	public Optional<Action> handle(Action action) {
		if (action instanceof SaveModelAction) {
			SaveModelAction saveAction = (SaveModelAction) action;
			if (saveAction != null && modelFactory instanceof FileBasedModelFactory) {
				((FileBasedModelFactory) modelFactory).saveModel(getModelState().getCurrentModel());
			}
		}
		return Optional.empty();
	}

}
