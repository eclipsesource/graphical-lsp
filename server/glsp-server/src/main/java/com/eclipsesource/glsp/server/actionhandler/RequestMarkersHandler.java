/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tanja Mayerhofer - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server.actionhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestMarkersAction;
import com.eclipsesource.glsp.api.action.kind.SetMarkersAction;
import com.eclipsesource.glsp.api.handler.ActionHandler;
import com.eclipsesource.glsp.api.markers.Marker;
import com.eclipsesource.glsp.api.markers.ModelValidator;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.google.inject.Inject;

public class RequestMarkersHandler implements ActionHandler {

	@Inject
	private ModelValidator validator;

	@Override
	public boolean handles(Action action) {
		return action instanceof RequestMarkersAction;
	}

	@Override
	public Optional<Action> execute(Action action, ModelState modelState) {
		RequestMarkersAction execAction = (RequestMarkersAction) action;
		String[] elementsIDs = execAction.getElementsIDs();
		if (elementsIDs == null || elementsIDs.length == 0) { // if no element ID is provided, compute the markers for
																// the complete model
			elementsIDs = new String[] { modelState.getCurrentModel().getId() };
		}
		List<Marker> markers = new ArrayList<Marker>();
		SModelIndex currentModelIndex = modelState.getCurrentModelIndex();
		for (String elementID : elementsIDs) {
			SModelElement modelElement = currentModelIndex.get(elementID);
			markers.addAll(validator.validate(modelState, modelElement));
		}

		// TODO get element ID of root element on client side and send it (see
		// glsp-palette-contribution.ts)
		// TODO wrap into setmarkersaction and return that action
		SetMarkersAction setMarkersAction = new SetMarkersAction(markers.toArray(new Marker[markers.size()]));
		return Optional.of(setMarkersAction);
	}

}
