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

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RedoAction;
import com.eclipsesource.glsp.api.action.kind.RequestBoundsAction;
import com.eclipsesource.glsp.api.action.kind.UndoAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;

public class UndoRedoActionHandler extends AbstractActionHandler {
	private static final Logger LOG = Logger.getLogger(UndoRedoActionHandler.class);

	@Override
	public boolean handles(Action action) {
		return action instanceof UndoAction || action instanceof RedoAction;
	}

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		if (action instanceof UndoAction && modelState.canUndo()) {
			modelState.undo();
			return Optional.of(new RequestBoundsAction(modelState.getRoot()));
		} else if (action instanceof RedoAction && modelState.canRedo()) {
			modelState.redo();
			return Optional.of(new RequestBoundsAction(modelState.getRoot()));
		}

		LOG.warn("Cannot undo or redo");
		return Optional.empty();
	}
}
