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

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.Bounds;
import org.eclipse.sprotty.Dimension;
import org.eclipse.sprotty.ElementAndBounds;
import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ChangeBoundsOperationAction;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

/**
 * Generic handler implementation for {@link ChangeBoundsOperationAction}
 */
public class ChangeBoundsOperationHandler implements IOperationHandler {

	private static Logger log = Logger.getLogger(ChangeBoundsOperationHandler.class);

	@Override
	public Class<?> handlesActionType() {
		return ChangeBoundsOperationAction.class;
	}

	@Override
	public Optional<SModelRoot> execute(Action action, IModelState modelState) {
		ChangeBoundsOperationAction changeBoundsAction = (ChangeBoundsOperationAction) action;
		for (ElementAndBounds element : changeBoundsAction.getNewBounds()) {
			changeElementBounds(element.getElementId(), element.getNewBounds(), modelState);
		}
		SModelRoot currentModel = modelState.getCurrentModel();
		return Optional.of(currentModel);
	}

	private Optional<SNode> changeElementBounds(String elementId, Bounds newBounds, IModelState modelState) {
		if (elementId == null || newBounds == null) {
			log.warn("Invalid ChangeBounds Action; missing mandatory arguments");
			return Optional.empty();
		}

		Optional<SNode> nodeToUpdate = findMovableNode(modelState, elementId);
		nodeToUpdate.ifPresent(node -> setBounds(node, newBounds));

		return nodeToUpdate;
	}

	private static Optional<SNode> findMovableNode(IModelState modelState, String elementId) {
		SModelIndex index = modelState.getCurrentModelIndex();
		SModelElement element = index.get(elementId);
		if (element == null) {
			log.warn("Element with id " + elementId + " not found");
			return Optional.empty();
		}

		if (!(element instanceof SNode)) {
			log.warn("Element " + elementId + " is not moveable");
			return Optional.empty();
		}

		return Optional.of((SNode) element);
	}

	private static void setBounds(SNode node, Bounds bounds) {
		Point newPosition = new Point(bounds.getX(), bounds.getY());
		node.setPosition(newPosition);

		Dimension newSize = new Dimension(bounds.getWidth(), bounds.getHeight());
		node.setSize(newSize);
	}
}
