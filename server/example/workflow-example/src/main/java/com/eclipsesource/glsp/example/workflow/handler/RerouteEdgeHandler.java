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
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.Arrays;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RerouteConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

public class RerouteEdgeHandler implements IOperationHandler {
	private static Logger log = Logger.getLogger(RerouteEdgeHandler.class);

	@Override
	public Class<?> handlesActionType() {
		return RerouteConnectionOperationAction.class;
	}

	@Override
	public Optional<SModelRoot> execute(Action operationAction, IModelState modelState) {
		if (!(operationAction instanceof RerouteConnectionOperationAction)) {
			log.warn("Unexpected action " + operationAction);
			return Optional.empty();
		}

		// check for null-values
		final RerouteConnectionOperationAction action = (RerouteConnectionOperationAction) operationAction;
		if (action.getConnectionElementId() == null || action.getRoutingPoints() == null) {
			log.warn("Incomplete reconnect connection action");
			return Optional.empty();
		}

		// check for existence of matching elements
		SModelIndex index = modelState.getIndex();
		Optional<SEdge> edge = index.findElement(action.getConnectionElementId(), SEdge.class);
		if (!edge.isPresent()) {
			log.warn("Invalid edge: edge ID " + action.getConnectionElementId());
			return Optional.empty();
		}

		// reroute
		edge.get().setRoutingPoints(Arrays.asList(action.getRoutingPoints()));

		SModelRoot currentModel = modelState.getCurrentModel();
		return Optional.of(currentModel);
	}

}
