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
import com.eclipsesource.glsp.api.action.kind.CollapseExpandAction;
import com.eclipsesource.glsp.api.action.kind.CollapseExpandAllAction;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.google.inject.Inject;

import io.typefox.sprotty.api.SModelIndex;

public class CollapseExpandActionHandler extends AbstractActionHandler {
	@Inject
	ModelExpansionListener expansionListener;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new CollapseExpandAction(), new CollapseExpandAllAction());
	}

	@Override
	public Optional<Action> handle(Action action) {
		switch (action.getKind()) {
		case ActionKind.COLLAPSE_EXPAND:
			return handleCollapseExpandAction((CollapseExpandAction) action);
		case ActionKind.COLLAPSE_EXPAND_ALL:
			return handleCollapseExpandAllAction((CollapseExpandAllAction) action);
		default:
			return Optional.empty();
		}
	}

	private Optional<Action> handleCollapseExpandAllAction(CollapseExpandAllAction action) {
		Set<String> expandedElements = getModelState().getExpandedElements();
		expandedElements.clear();
		if (action.isExpand()) {
			new SModelIndex(getModelState().getCurrentModel()).allIds().forEach(id -> expandedElements.add(id));
		}
		if (expansionListener != null) {
			expansionListener.expansionChanged(action);
		}
		return Optional.empty();
	}

	private Optional<Action> handleCollapseExpandAction(CollapseExpandAction action) {
		Set<String> expandedElements = getModelState().getExpandedElements();
		if (action.getCollapseIds() != null) {
			expandedElements.removeAll(Arrays.asList(action.getCollapseIds()));
		}
		if (action.getExpandIds() != null) {
			expandedElements.addAll(Arrays.asList(action.getExpandIds()));
		}

		if (expansionListener != null) {
			expansionListener.expansionChanged(action);
		}

		return Optional.empty();

	}

}
