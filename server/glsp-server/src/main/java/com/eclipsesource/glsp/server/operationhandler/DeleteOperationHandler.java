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
package com.eclipsesource.glsp.server.operationhandler;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.DeleteOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.eclipsesource.glsp.graph.GNode;

/**
 * Generic handler implementation for {@link DeleteOperationAction}
 */
public class DeleteOperationHandler implements OperationHandler {
	private static Logger log = Logger.getLogger(DeleteOperationHandler.class);
	private Set<String> allDependantsIds;

	@Override
	public boolean handles(AbstractOperationAction action) {
		return action instanceof DeleteOperationAction;
	}

	@Override
	public void execute(AbstractOperationAction execAction, GraphicalModelState modelState) {
		DeleteOperationAction action = (DeleteOperationAction) execAction;
		List<String> elementIds = action.getElementIds();
		if (elementIds == null || elementIds.size() == 0) {
			throw new IllegalArgumentException("Elements to delete are not specified");
		}
		GModelIndex index = modelState.getIndex();
		allDependantsIds = new HashSet<>();
		boolean success = elementIds.stream().allMatch(eId -> delete(eId, index, modelState));
		if (!success) {
			log.warn("Could not delete all elements as requested (see messages above to find out why)");
		}
	}

	protected boolean delete(String elementId, GModelIndex index, GraphicalModelState modelState) {
		if (allDependantsIds.contains(elementId)) {
			// The element as already been deleted as dependent of a previously deleted
			// element
			return true;
		}

		Optional<GModelElement> element = index.get(elementId);
		if (!element.isPresent()) {
			log.warn("Element not found: " + elementId);
			return false;
		}

		// Always delete the top-level node
		GModelElement nodeToDelete = findTopLevelElement(element.get());
		if (nodeToDelete.getParent() == null) {
			log.warn("The requested node doesn't have a parent; it can't be deleted");
			return false; // Can't delete the root, or an element that doesn't belong to the model
		}

		Set<GModelElement> dependents = new LinkedHashSet<>();
		collectDependents(dependents, nodeToDelete, modelState);

		dependents.forEach(EcoreUtil::delete);
		allDependantsIds.addAll(dependents.stream().map(GModelElement::getId).collect(Collectors.toSet()));
		return true;
	}

	protected void collectDependents(Set<GModelElement> dependents, GModelElement nodeToDelete,
			GraphicalModelState modelState) {
		if (dependents.contains(nodeToDelete)) {
			return;
		}

		// First, children
		if (nodeToDelete.getChildren() != null) {
			for (GModelElement child : nodeToDelete.getChildren()) {
				collectDependents(dependents, child, modelState);
			}
		}

		// Then, incoming/outgoing edges for nodes
		if (nodeToDelete instanceof GNode) {
			GModelIndex index = modelState.getIndex();

			// Then, incoming/outgoing links
			for (GModelElement incoming : index.getIncomingEdges(nodeToDelete)) {
				collectDependents(dependents, incoming, modelState);
			}
			for (GModelElement outgoing : index.getOutgoingEdges(nodeToDelete)) {
				collectDependents(dependents, outgoing, modelState);
			}
		}

		// Finally, the node to delete
		dependents.add(nodeToDelete);
	}

	protected GModelElement findTopLevelElement(GModelElement element) {
		if (element instanceof GNode || element instanceof GEdge) {
			return element;
		}

		GModelElement parent = element.getParent();
		if (parent == null) {
			return element;
		}
		return findTopLevelElement(parent);
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Delete element";
	}

}
