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

import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.WEIGHTED_EDGE;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.schema.ModelTypes;
import com.eclipsesource.glsp.example.workflow.schema.WeightedEdge;

public class CreateWeightedEdgeHandler implements IOperationHandler {
	private static Logger log = Logger.getLogger(CreateWeightedEdgeHandler.class);

	@Override
	public boolean handles(Action execAction) {
		if (execAction instanceof CreateConnectionOperationAction) {
			CreateConnectionOperationAction action = (CreateConnectionOperationAction) execAction;
			return ModelTypes.WEIGHTED_EDGE.equals(action.getElementTypeId());
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

		SModelIndex index = modelState.getIndex();

		Optional<SNode> source = index.findElement(action.getSourceElementId(),SNode.class);
		Optional<SNode> target = index.findElement(action.getTargetElementId(),SNode.class);

		if (!source.isPresent() || target.isPresent()) {
			log.warn("NULL source or target for source ID " + action.getSourceElementId() + " and target ID "
					+ action.getTargetElementId());
			return Optional.empty();
		}


		WeightedEdge edge = new WeightedEdge();
		edge.setSourceId(source.get().getId());
		edge.setTargetId(target.get().getId());
		edge.setType(WEIGHTED_EDGE);
		int newID = index.getTypeCount(WEIGHTED_EDGE);
		edge.setId(WEIGHTED_EDGE + newID);
		edge.setProbability("high");
		SModelRoot currentModel= modelState.getCurrentModel();
		currentModel.getChildren().add(edge);
		index.addToIndex(edge, currentModel);

		return Optional.of(currentModel);
	}

}
