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

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.DeleteElementOperationAction;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

/**
 * Generic handler implementation for {@link DeleteElementOperationAction}
 */
public class DeleteElementOperationHandler implements IOperationHandler {
	private static Logger log = Logger.getLogger(DeleteElementOperationHandler.class);

	@Override
	public boolean handles(Action action) {
		return action instanceof DeleteElementOperationAction;
	}

	@Override
	public Optional<SModelRoot> execute(Action execAction, IModelState modelState) {
		DeleteElementOperationAction action = (DeleteElementOperationAction) execAction;

		SModelIndex index = modelState.getCurrentModelIndex();

		boolean success = action.getElementIds().stream().allMatch(eId -> delete(eId, index, modelState));
		if (!success) {
			return Optional.empty();
		}

		SModelRoot currentModel = modelState.getCurrentModel();
		return Optional.of(currentModel);
	}

	protected boolean delete(String elementId, SModelIndex index, IModelState modelState) {
		SModelElement element = index.get(elementId);

		if (element == null) {
			log.warn("Element not found: " + elementId);
			return false;
		}

		// Always delete the top-level node
		SModelElement nodeToDelete = findTopLevelElement(element, index);
		SModelElement parent = index.getParent(nodeToDelete);
		if (parent == null) {
			log.warn("The requested node doesn't have a parent; it can't be deleted");
			return false; // Can't delete the root, or an element that doesn't belong to the model
		}

		Set<SModelElement> dependents = new LinkedHashSet<>();
		collectDependents(dependents, nodeToDelete, modelState);
		return deleteDependents(dependents, modelState);
	}
	
	protected boolean deleteDependents(Collection<SModelElement> dependents, IModelState modelState) {
		return dependents.stream().allMatch(element-> delete(element,modelState));
	}

	protected boolean delete(SModelElement element, IModelState modelState) {
		SModelElement parent = modelState.getCurrentModelIndex().getParent(element);
		modelState.getCurrentModelIndex().removeFromIndex(element);

		if (parent == null || parent.getChildren() == null) {
			return false; 
		}
		parent.getChildren().remove(element);
		return true;
	}

	protected void collectDependents(Set<SModelElement> dependents, SModelElement nodeToDelete,
			IModelState modelState) {
		if (dependents.contains(nodeToDelete)) {
			return;
		}

		// First, children
		if (nodeToDelete.getChildren() != null) {
			for (SModelElement child : nodeToDelete.getChildren()) {
				collectDependents(dependents, child, modelState);
			}
		}

		SModelIndex index = modelState.getCurrentModelIndex();

		// Then, incoming/outgoing links
		for (SModelElement incoming : index.getIncomingEdges(nodeToDelete)) {
			collectDependents(dependents, incoming, modelState);
		}
		for (SModelElement outgoing : index.getOutgoingEdges(nodeToDelete)) {
			collectDependents(dependents, outgoing, modelState);
		}

		// Finally, the node to delete
		dependents.add(nodeToDelete);
	}

	protected SModelElement findTopLevelElement(SModelElement element, SModelIndex index) {
		if (element instanceof SNode || element instanceof SEdge) {
			return element;
		}

		SModelElement parent = index.getParent(element);
		if (parent == null) {
			return element;
		}
		return findTopLevelElement(parent, index);
	}

}
