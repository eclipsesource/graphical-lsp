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

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.schema.ModelTypes;
import com.eclipsesource.glsp.example.workflow.schema.WeightedEdge;

public class CreateWeightedEdgeHandler implements OperationHandler {
	private static Logger log = Logger.getLogger(CreateWeightedEdgeHandler.class);

	@Override
	public boolean handles(AbstractOperationAction execAction) {
		if (execAction instanceof CreateConnectionOperationAction) {
			CreateConnectionOperationAction action = (CreateConnectionOperationAction) execAction;
			return ModelTypes.WEIGHTED_EDGE.equals(action.getElementTypeId());
		}
		return false;
	}

	@Override
	public Optional<SModelRoot> execute(AbstractOperationAction operationAction, GraphicalModelState modelState) {
		CreateConnectionOperationAction action = (CreateConnectionOperationAction) operationAction;
		if (action.getSourceElementId() == null || action.getTargetElementId() == null) {
			log.warn("Incomplete create connection action");
			return Optional.empty();
		}

		SModelIndex index = modelState.getIndex();

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

		SModelRoot currentModel = modelState.getRoot();
		if (source == currentModel || target == currentModel) {
			log.warn("Can't create a link to the root node");
			return Optional.empty();
		}

		WeightedEdge edge = new WeightedEdge();
		edge.setSourceId(source.getId());
		edge.setTargetId(target.getId());
		edge.setType(WEIGHTED_EDGE);
		int newID = index.getTypeCount(WEIGHTED_EDGE);
		edge.setId(WEIGHTED_EDGE + newID);
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
