/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.Optional;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.action.kind.ExecuteOperationAction;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.operations.OperationHandler;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.WorkflowOperationConfiguration;
import com.eclipsesource.glsp.example.workflow.schema.WeightedEdge;

import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

public class CreateWeightedEdgeHandler implements OperationHandler {
	private static Logger log= Logger.getLogger(CreateWeightedEdgeHandler.class);
	@Override
	public boolean handles(ExecuteOperationAction execAction) {
		if (execAction instanceof CreateConnectionOperationAction) {
			CreateConnectionOperationAction action = (CreateConnectionOperationAction) execAction;
			return WorkflowOperationConfiguration.WEIGHTED_EDGE_ID.equals(action.getElementTypeId());
		}
		return false;
	}

	@Override
	public Optional<SModelRoot> execute(ExecuteOperationAction operationAction, ModelState modelState) {
		CreateConnectionOperationAction action = (CreateConnectionOperationAction) operationAction;
		if (action.getSourceElementId() == null || action.getTargetElementId() == null) {
			log.warn("Incomplete create connection action");
			return Optional.empty();
		}

		SModelIndex index = modelState.getCurrentModelIndex();

		SModelElement source = index.get(action.getSourceElementId());
		SModelElement target = index.get(action.getTargetElementId());

		if (source == null || target == null) {
			log.warn("NULL source or target for source ID " + action.getSourceElementId() + " and target ID "
					+ action.getTargetElementId());
			return Optional.empty();
		}

		if (false == source instanceof SNode) {
			source = findNode(source, index);
		}
		if (false == target instanceof SNode) {
			target = findNode(target, index);
		}

		SModelRoot currentModel = modelState.getCurrentModel();
		if (source == currentModel || target == currentModel) {
			log.warn("Can't create a link to the root node");
			return Optional.empty();
		}

		WeightedEdge edge = new WeightedEdge();
		edge.setSourceId(source.getId());
		edge.setTargetId(target.getId());
		edge.setType("edge:weighted");
		int newID = index.getTypeCount("edge:weighted");
		edge.setId("edge:weighted" + newID);
		edge.setProbability("high");

		currentModel.getChildren().add(edge);
		index.addToIndex(edge, currentModel);

		return Optional.of(currentModel);
	}

	private SModelElement findNode(SModelElement element, com.eclipsesource.glsp.api.utils.SModelIndex index) {
		if (element instanceof SNode) {
			return element;
		}

		SModelElement parent = index.getParent(element);
		if (parent == null) {
			return element;
		}
		return findNode(parent, index);
	}

}
