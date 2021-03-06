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
package com.eclipsesource.glsp.example.modelserver.workflow.handler;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.DeleteOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.modelserver.coffee.model.coffee.Flow;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;

public class DeleteOperationHandler implements OperationHandler {

	private Set<EObject> toDelete;

	@Override
	public boolean handles(AbstractOperationAction action) {
		return action instanceof DeleteOperationAction;
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Delete elements";
	}

	@Override
	public void execute(AbstractOperationAction action, GraphicalModelState modelState) {
		DeleteOperationAction deleteAction = (DeleteOperationAction) action;
		toDelete = new HashSet<>();
		deleteAction.getElementIds().forEach(id -> collectElementsToDelete(id, modelState));
		EcoreUtil.deleteAll(toDelete, true);
	}

	protected void collectElementsToDelete(String id, GraphicalModelState modelState) {
		Optional<GModelElement> maybeModelElement = modelState.getIndex().get(id);
		if (maybeModelElement.isEmpty()) {
			return;
		}

		GModelElement element = findTopLevelElement(maybeModelElement.get());
		if (!isNodeOrEdge(element)) {
			return;
		}

		WorkflowModelServerAccess modelAccess = ModelServerAwareModelState.getModelAccess(modelState);
		if (element instanceof GNode) {
			Node node = modelAccess.getNodeById(element.getId());
			toDelete.add(node);

			Optional<DiagramElement> diagramElement = modelAccess.getWorkflowFacade().findDiagramElement(node);
			if (!diagramElement.isEmpty() && diagramElement.get() instanceof Shape) {
				toDelete.add(diagramElement.get());
			}

			modelState.getIndex().getIncomingEdges(element)
					.forEach(edge -> collectElementsToDelete(edge.getId(), modelState));
			modelState.getIndex().getOutgoingEdges(element)
					.forEach(edge -> collectElementsToDelete(edge.getId(), modelState));
		} else if (element instanceof GEdge) {
			GEdge gEdge = (GEdge) element;
			Node sourceNode = modelAccess.getNodeById(gEdge.getSourceId());
			Node targetNode = modelAccess.getNodeById(gEdge.getTargetId());
			Optional<Flow> maybeFlow = modelAccess.getFlow(sourceNode, targetNode);
			if (maybeFlow.isEmpty()) {
				return;
			}
			toDelete.add(maybeFlow.get());

			Optional<DiagramElement> edge = maybeFlow
					.flatMap(flow -> modelAccess.getWorkflowFacade().findDiagramElement(flow));
			if (edge.isEmpty()) {
				return;
			}
			toDelete.add(edge.get());
		}
	}

	protected GModelElement findTopLevelElement(GModelElement element) {
		if (isNodeOrEdge(element)) {
			return element;
		}

		GModelElement parent = element.getParent();
		if (parent == null) {
			return element;
		}

		return findTopLevelElement(parent);
	}

	public boolean isNodeOrEdge(GModelElement element) {
		return element instanceof GNode || element instanceof GEdge;
	}

}
