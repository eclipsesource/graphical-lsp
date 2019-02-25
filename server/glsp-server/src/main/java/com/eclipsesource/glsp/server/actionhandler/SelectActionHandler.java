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

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.SelectAction;
import com.eclipsesource.glsp.api.action.kind.SelectAllAction;
import com.eclipsesource.glsp.api.model.IModelSelectionListener;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.google.inject.Inject;

public class SelectActionHandler extends AbstractActionHandler {
	@Inject
	protected IModelSelectionListener modelSelectionListener;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new SelectAction(), new SelectAllAction());
	}

	@Override
	public Optional<Action> execute(Action action, ModelState modelState) {
		switch (action.getKind()) {
		case Action.Kind.SELECT:
			return handleSelectAction((SelectAction) action, modelState);
		case Action.Kind.SELECT_ALL:
			return handleSelectAllAction((SelectAllAction) action, modelState);
		default:
			return Optional.empty();
		}

	}

	private Optional<Action> handleSelectAllAction(SelectAllAction action, ModelState modelState) {
		Set<String> selectedElements = modelState.getSelectedElements();
		if (action.isSelect()) {
			new SModelIndex(modelState.getCurrentModel()).allIds().forEach(id -> selectedElements.add(id));
		} else
			selectedElements.clear();
		if (modelSelectionListener != null) {
			modelSelectionListener.selectionChanged(action);
		}
		return Optional.empty();
	}

	private Optional<Action> handleSelectAction(SelectAction action, ModelState modelState) {
		Set<String> selectedElements = modelState.getSelectedElements();
		if (action.getDeselectedElementsIDs() != null) {
			selectedElements.removeAll(Arrays.asList(action.getDeselectedElementsIDs()));
		}
		if (action.getSelectedElementsIDs() != null) {
			selectedElements.addAll(Arrays.asList(action.getSelectedElementsIDs()));
		}
		if (modelSelectionListener != null) {
			modelSelectionListener.selectionChanged(action);
		}
		return Optional.empty();
	}

}
