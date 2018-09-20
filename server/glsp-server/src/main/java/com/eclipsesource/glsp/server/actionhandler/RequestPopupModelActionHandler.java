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
import com.eclipsesource.glsp.api.action.kind.RequestPopupModelAction;
import com.eclipsesource.glsp.api.action.kind.SetPopupModelAction;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.google.inject.Inject;

import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;

public class RequestPopupModelActionHandler extends AbstractActionHandler {
	@Inject
	private PopupModelFactory popupModelFactory;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new RequestPopupModelAction());
	}

	@Override
	public Optional<Action> handle(Action action) {
		if (action instanceof RequestPopupModelAction) {
			RequestPopupModelAction requestAction = (RequestPopupModelAction) action;
			SModelRoot model = getModelState().getCurrentModel();
			SModelElement element = SModelIndex.find(model, requestAction.getElementId());
			if (popupModelFactory != null) {
				SModelRoot popupModel = popupModelFactory.createPopuModel(element, requestAction);
				if (popupModel != null) {
					return Optional.of(new SetPopupModelAction(popupModel, requestAction.getBounds()));
				}
			}
		}
		return Optional.empty();
	}

}
