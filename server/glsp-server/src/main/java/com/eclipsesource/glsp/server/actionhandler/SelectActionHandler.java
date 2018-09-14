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
import java.util.Set;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ActionKind;
import com.eclipsesource.glsp.api.action.kind.SelectAction;
import com.eclipsesource.glsp.api.action.kind.SelectAllAction;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.google.inject.Inject;

public class SelectActionHandler extends AbstractActionHandler {
	@Inject
	ModelSelectionListener modelSelectionListener;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new SelectAction(), new SelectAllAction());
	}

	@Override
	public Optional<Action> handle(Action action) {
		switch (action.getKind()) {
		case ActionKind.SELECT:
			return handleSelectAction((SelectAction) action);
		case ActionKind.SELECT_ALL:
			return handleSelectAllAction((SelectAllAction) action);
		default:
			return Optional.empty();
		}

	}

	private Optional<Action> handleSelectAllAction(SelectAllAction action) {
		Set<String> selectedElements = getModelState().getSelectedElements();
		if (action.isSelect()) {
			new SModelIndex(getModelState().getCurrentModel()).allIds().forEach(id -> selectedElements.add(id));
		} else
			selectedElements.clear();
		if (modelSelectionListener != null) {
			modelSelectionListener.selectionChanged(action);
		}
		return Optional.empty();
	}

	private Optional<Action> handleSelectAction(SelectAction action) {
		Set<String> selectedElements = getModelState().getSelectedElements();
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
