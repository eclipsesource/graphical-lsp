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

import java.util.Optional;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestBoundsAction;
import com.eclipsesource.glsp.api.action.kind.SetModelAction;
import com.eclipsesource.glsp.api.action.kind.UpdateModelAction;
import com.eclipsesource.glsp.api.model.ModelState;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import io.typefox.sprotty.api.ILayoutEngine;
import io.typefox.sprotty.api.SModelRoot;

@Singleton
public class ModelSubmissionHandler {
	@Inject
	ILayoutEngine layoutEngine;
	private Object modelLock = new Object();
	private String lastSubmittedModelType;
	private int revision = 0;

	public Optional<Action> handleSubmission(SModelRoot root, boolean update, ModelState modelState) {
		lastSubmittedModelType = root.getType();
		modelState.setCurrentModel(root);
		if (modelState.needsClientLayout()) {
			return Optional.of(new RequestBoundsAction(root));
		} else {
			return createSubmissionAction(root, update);
		}

	}

	private Optional<Action> createSubmissionAction(SModelRoot root, boolean update) {

		if (layoutEngine != null) {
			layoutEngine.layout(root);
		}
		synchronized (modelLock) {
			if (root.getRevision() == revision) {
				String modelType = root.getType();
				lastSubmittedModelType = modelType;
				if (update && modelType != null && modelType.equals(lastSubmittedModelType)) {
					return Optional.of(new UpdateModelAction(root, null, true));
				} else {
					return Optional.of(new SetModelAction(root));
				}
			}

		}
		return Optional.empty();
	}

	public synchronized Object getModelLock() {
		return modelLock;
	}
}
