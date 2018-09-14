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
import com.eclipsesource.glsp.api.action.kind.ComputedBoundsAction;
import com.eclipsesource.glsp.api.utils.LayoutUtil;
import com.google.inject.Inject;

import io.typefox.sprotty.api.SModelRoot;

public class ComputedBoundsActionHandler extends AbstractActionHandler {
	@Inject
	private ModelSubmissionHandler submissionHandler;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new ComputedBoundsAction());
	}

	@Override
	public Optional<Action> handle(Action action) {
		if (action instanceof ComputedBoundsAction) {
			ComputedBoundsAction computedBoundsAction = (ComputedBoundsAction) action;

			synchronized (submissionHandler.getModelLock()) {
				SModelRoot model = getModelState().getCurrentModel();
				if (model != null && model.getRevision() == computedBoundsAction.getRevision()) {
					LayoutUtil.applyBounds(model, computedBoundsAction);
					return submissionHandler.handleSubmission(model, true, getModelState());
				}
			}
		}
		return Optional.empty();
	}

}
