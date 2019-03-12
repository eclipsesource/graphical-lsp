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

import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.EDGE;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.schema.ModelTypes;

public class CreateEdgeHandler implements OperationHandler {
	private static Logger log = Logger.getLogger(CreateEdgeHandler.class);

	@Override
	public boolean handles(Action execAction) {
		if (execAction instanceof CreateConnectionOperationAction) {
			CreateConnectionOperationAction action = (CreateConnectionOperationAction) execAction;
			return ModelTypes.EDGE.equals(action.getElementTypeId());
		}
		return false;
	}

	@Override
	public Optional<SModelRoot> execute(Action operationAction, IModelState modelState) {
		CreateConnectionOperationAction action = (CreateConnectionOperationAction) operationAction;
		if (action.getSourceElementId() == null || action.getTargetElementId() == null) {
			log.warn("Incomplete create connection action");
			return Optional.empty();
		}

		SModelIndex index = modelState.getCurrentModelIndex();

		Optional<SNode> source = index.findElement(action.getSourceElementId(), SNode.class);
		Optional<SNode> target = index.findElement(action.getTargetElementId(), SNode.class);
		if (!source.isPresent() || !target.isPresent()) {
			log.warn("Invalid source or target for source ID " + action.getSourceElementId() + " and target ID "
					+ action.getTargetElementId());
			return Optional.empty();
		}

		SEdge edge = new SEdge();
		edge.setSourceId(source.get().getId());
		edge.setTargetId(target.get().getId());
		edge.setType(EDGE);
		int newID = index.getTypeCount(EDGE);
		while (index.get(EDGE + newID) != null) {
			newID++;
		}
		edge.setId(EDGE + newID);

		SModelRoot currentModel = modelState.getCurrentModel();
		currentModel.getChildren().add(edge);
		index.addToIndex(edge, currentModel);

		return Optional.of(currentModel);
	}
}
