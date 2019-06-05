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

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestBoundsAction;
import com.eclipsesource.glsp.api.action.kind.SetModelAction;
import com.eclipsesource.glsp.api.action.kind.UpdateModelAction;
import com.eclipsesource.glsp.api.layout.ILayoutEngine;
import com.eclipsesource.glsp.api.layout.ServerLayoutKind;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ModelSubmissionHandler {
	@Inject
	ILayoutEngine layoutEngine;
	private Object modelLock = new Object();
	private int revision = 0;

	public Optional<Action> submit(boolean update, GraphicalModelState modelState) {
		if (modelState.getClientOptions().needsClientLayout()) {
			return Optional.of(new RequestBoundsAction(modelState.getRoot()));
		} else {
			return doSubmitModel(update, modelState);
		}
	}

	public Optional<Action> doSubmitModel(boolean update, GraphicalModelState modelState) {
		GModelRoot newRoot = modelState.getRoot();
		if (modelState.getServerOptions().getLayoutKind() == ServerLayoutKind.AUTOMATIC) {
			if (layoutEngine != null) {
				layoutEngine.layout(newRoot);
			}
		}
		synchronized (modelLock) {
			if (newRoot.getRevision() == revision) {
				if (update) {
					return Optional.of(new UpdateModelAction(newRoot, true));
				} else {
					return Optional.of(new SetModelAction(newRoot));
				}
			}
		}
		return Optional.empty();
	}

	public synchronized Object getModelLock() {
		return modelLock;
	}
}
