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

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ChangeRoutingPointsAction;
import com.eclipsesource.glsp.api.model.ElementAndRoutingPoints;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GPoint;
import com.google.inject.Inject;

public class SaveModelEdgesHandler extends AbstractActionHandler{

	private static final Logger LOG = Logger.getLogger(SaveModelActionHandler.class);
	
	@Inject
	private ModelSubmissionHandler modelSubmissionHandler;
	
	@Override
	public boolean handles(Action action) {
		return action instanceof ChangeRoutingPointsAction;
	}
	@Override
	protected Optional<Action> execute(Action action, GraphicalModelState modelState) {
		
		ChangeRoutingPointsAction saveModelEdgesAction = (ChangeRoutingPointsAction)action;
		List<ElementAndRoutingPoints> elementAndRoutingPointsList = saveModelEdgesAction.getNewRoutingPoints();
		for(ElementAndRoutingPoints elementAndRoutingPoints : elementAndRoutingPointsList) {
			Optional<GModelElement> modelElement = modelState.getIndex().get(elementAndRoutingPoints.getElementId());
			if(modelElement.isPresent() && modelElement.get() instanceof GEdge) {
				GEdge edge = (GEdge) modelElement.get();
				EList<GPoint> routingPoints = edge.getRoutingPoints();
				routingPoints.clear();
				routingPoints.addAll(elementAndRoutingPoints.getRoutingPoints());
			}
		}
		return modelSubmissionHandler.doSubmitModel(true, modelState);
	}
}
