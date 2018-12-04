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

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ComputedBoundsAction;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.utils.LayoutUtil;
import com.google.inject.Inject;

public class ComputedBoundsActionHandler extends AbstractActionHandler {
	@Inject
	private ModelSubmissionHandler submissionHandler;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new ComputedBoundsAction());
	}

	@Override
	public Optional<Action> execute(Action action, ModelState modelState) {
		if (action instanceof ComputedBoundsAction) {
			ComputedBoundsAction computedBoundsAction = (ComputedBoundsAction) action;

			synchronized (submissionHandler.getModelLock()) {
				SModelRoot model = modelState.getCurrentModel();
				if (model != null && model.getRevision() == computedBoundsAction.getRevision()) {
					LayoutUtil.applyBounds(model, computedBoundsAction);
					return submissionHandler.handleSubmission(model, true, modelState);
				}
			}
		}
		return Optional.empty();
	}

}
