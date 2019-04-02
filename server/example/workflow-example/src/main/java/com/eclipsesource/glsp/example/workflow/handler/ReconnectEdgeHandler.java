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

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.ReconnectConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

public class ReconnectEdgeHandler implements OperationHandler {
	private static Logger log = Logger.getLogger(ReconnectEdgeHandler.class);

	@Override
	public Class<?> handlesActionType() {
		return ReconnectConnectionOperationAction.class;
	}

	@Override
	public Optional<SModelRoot> execute(AbstractOperationAction operationAction, GraphicalModelState modelState) {
		if (!(operationAction instanceof ReconnectConnectionOperationAction)) {
			log.warn("Unexpected action " + operationAction);
			return Optional.empty();
		}

		// check for null-values
		final ReconnectConnectionOperationAction action = (ReconnectConnectionOperationAction) operationAction;
		if (action.getConnectionElementId() == null || action.getSourceElementId() == null
				|| action.getTargetElementId() == null) {
			log.warn("Incomplete reconnect connection action");
			return Optional.empty();
		}

		// check for existence of matching elements
		SModelIndex index = modelState.getIndex();
		Optional<SEdge> edge = index.findElement(action.getConnectionElementId(), SEdge.class);
		Optional<SNode> source = index.findElement(action.getSourceElementId(), SNode.class);
		Optional<SNode> target = index.findElement(action.getTargetElementId(), SNode.class);
		if (!edge.isPresent() || !source.isPresent() || !target.isPresent()) {
			log.warn("Invalid edge, source or target ID: edge ID " + action.getConnectionElementId() + ", source ID "
					+ action.getSourceElementId() + " and target ID " + action.getTargetElementId());
			return Optional.empty();
		}

		// reconnect
		edge.get().setSourceId(source.get().getId());
		edge.get().setTargetId(target.get().getId());
		edge.get().setRoutingPoints(null);

		SModelRoot currentModel = modelState.getRoot();
		return Optional.of(currentModel);
	}
}
