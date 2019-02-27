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

import java.util.Optional;

import org.eclipse.sprotty.ILayoutEngine;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestBoundsAction;
import com.eclipsesource.glsp.api.action.kind.SetModelAction;
import com.eclipsesource.glsp.api.action.kind.UpdateModelAction;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.server.model.GLSPGraph;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ModelSubmissionHandler {
	@Inject
	protected ILayoutEngine layoutEngine;
	private Object modelLock = new Object();
	private String lastSubmittedModelType;
	private int revision = 0;

	public Optional<Action> submit(SModelRoot newRoot, boolean update, IModelState modelState) {
		if (modelState.getOptions().needsClientLayout()) {
			return Optional.of(new RequestBoundsAction(newRoot));
		} else {
			return doSubmitModel(newRoot, update, modelState);
		}
	}

	public Optional<Action> doSubmitModel(SModelRoot newRoot, boolean update, IModelState modelState) {
		if (modelState.getOptions().needsServerLayout() || needsInitialLayout(newRoot)) {
			layoutEngine.layout(newRoot);
			if (newRoot instanceof GLSPGraph) {
				((GLSPGraph) newRoot).setNeedsInitialLayout(false);
			}
		}
		synchronized (modelLock) {
			if (newRoot.getRevision() == revision) {
				String modelType = newRoot.getType();
				if (update && modelType != null && modelType.equals(lastSubmittedModelType)) {
					return Optional.of(new UpdateModelAction(newRoot, true));
				} else {
					lastSubmittedModelType = modelType;
					return Optional.of(new SetModelAction(newRoot));
				}
			}
		}
		return Optional.empty();
	}

	public synchronized Object getModelLock() {
		return modelLock;
	}

	public static boolean needsInitialLayout(SModelRoot modelRoot) {
		if (modelRoot instanceof GLSPGraph) {
			return ((GLSPGraph) modelRoot).isNeedsInitialLayout();
		}
		return false;
	}
}
