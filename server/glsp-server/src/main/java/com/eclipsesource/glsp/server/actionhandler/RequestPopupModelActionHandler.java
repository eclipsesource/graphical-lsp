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

import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestPopupModelAction;
import com.eclipsesource.glsp.api.action.kind.SetPopupModelAction;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.google.inject.Inject;

public class RequestPopupModelActionHandler extends AbstractActionHandler {
	@Inject
	private PopupModelFactory popupModelFactory;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new RequestPopupModelAction());
	}

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		if (action instanceof RequestPopupModelAction) {
			RequestPopupModelAction requestAction = (RequestPopupModelAction) action;
			SModelRoot model = modelState.getRoot();
			Optional<SModelElement> element = modelState.getIndex().get(requestAction.getElementId());
			if (popupModelFactory != null && element.isPresent()) {
				SModelRoot popupModel = popupModelFactory.createPopuModel(element.get(), requestAction);
				if (popupModel != null) {
					return Optional.of(new SetPopupModelAction(popupModel, requestAction.getBounds()));
				}
			}
		}
		return Optional.empty();
	}

}
