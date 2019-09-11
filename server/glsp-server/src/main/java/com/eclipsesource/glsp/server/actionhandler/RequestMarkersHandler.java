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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestMarkersAction;
import com.eclipsesource.glsp.api.action.kind.SetMarkersAction;
import com.eclipsesource.glsp.api.markers.Marker;
import com.eclipsesource.glsp.api.markers.ModelValidator;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.google.inject.Inject;

public class RequestMarkersHandler extends AbstractActionHandler {

	@Inject
	protected ModelValidator validator;

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		RequestMarkersAction execAction = (RequestMarkersAction) action;
		List<String> elementsIDs = execAction.getElementsIDs();
		if (elementsIDs == null || elementsIDs.size() == 0) { // if no element ID is provided, compute the markers for
																// the complete model
			elementsIDs = Arrays.asList(modelState.getRoot().getId());
		}
		List<Marker> markers = new ArrayList<Marker>();
		GModelIndex currentModelIndex = modelState.getIndex();
		for (String elementID : elementsIDs) {
			Optional<GModelElement> modelElement = currentModelIndex.get(elementID);
			if (modelElement.isPresent()) {
				markers.addAll(validator.validate(modelState, modelElement.get()));
			}

		}

		SetMarkersAction setMarkersAction = new SetMarkersAction(markers);
		return Optional.of(setMarkersAction);
	}

	@Override
	public boolean handles(Action action) {
		return action instanceof RequestMarkersAction;
	}

}
