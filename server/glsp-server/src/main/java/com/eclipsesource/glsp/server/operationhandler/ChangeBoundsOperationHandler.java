/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Martin Fleck - initial API and implementation
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

import com.eclipsesource.glsp.api.action.kind.ChangeBoundsOperationAction;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

/**
 * Generic handler implementation for {@link ChangeBoundsOperationAction}
 */
public class ChangeBoundsOperationHandler implements OperationHandler {

	private static Logger log = Logger.getLogger(ChangeBoundsOperationHandler.class);

	@Override
	public Class<?> handlesActionType() {
		return ChangeBoundsOperationAction.class;
	}

	@Override
	public Optional<SModelRoot> execute(AbstractOperationAction action, ModelState modelState) {
		ChangeBoundsOperationAction changeBoundsAction = (ChangeBoundsOperationAction) action;
		for (ElementAndBounds element : changeBoundsAction.getNewBounds()) {
			changeElementBounds(element.getElementId(), element.getNewBounds(), modelState);
		}
		SModelRoot currentModel = modelState.getCurrentModel();
		return Optional.of(currentModel);
	}

	private Optional<SNode> changeElementBounds(String elementId, Bounds newBounds, ModelState modelState) {
		if (elementId == null || newBounds == null) {
			log.warn("Invalid ChangeBounds Action; missing mandatory arguments");
			return Optional.empty();
		}

		Optional<SNode> nodeToUpdate = findMovableNode(modelState, elementId);
		nodeToUpdate.ifPresent(node -> setBounds(node, newBounds));

		return nodeToUpdate;
	}

	private static Optional<SNode> findMovableNode(ModelState modelState, String elementId) {
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
