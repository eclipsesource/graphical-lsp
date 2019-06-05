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

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.ChangeBoundsOperationAction;
import com.eclipsesource.glsp.api.action.kind.ElementAndBounds;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.LayoutUtil;
import com.eclipsesource.glsp.graph.GBounds;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.eclipsesource.glsp.graph.GNode;

/**
 * Generic handler implementation for {@link ChangeBoundsOperationAction}
 */
public class ChangeBoundsOperationHandler implements OperationHandler {

	private static Logger log = Logger.getLogger(ChangeBoundsOperationHandler.class);

	@Override
	public Class<? extends Action> handlesActionType() {
		return ChangeBoundsOperationAction.class;
	}

	@Override
	public Optional<GModelRoot> execute(AbstractOperationAction action, GraphicalModelState modelState) {
		ChangeBoundsOperationAction changeBoundsAction = (ChangeBoundsOperationAction) action;
		for (ElementAndBounds element : changeBoundsAction.getNewBounds()) {
			changeElementBounds(element.getElementId(), element.getNewBounds(), modelState);
		}
		return Optional.of(modelState.getRoot());
	}

	private Optional<GNode> changeElementBounds(String elementId, GBounds newBounds, GraphicalModelState modelState) {
		if (elementId == null || newBounds == null) {
			log.warn("Invalid ChangeBounds Action; missing mandatory arguments");
			return Optional.empty();
		}

		Optional<GNode> nodeToUpdate = findMovableNode(modelState, elementId);
		nodeToUpdate.ifPresent(node -> setBounds(node, newBounds));

		return nodeToUpdate;
	}

	private static Optional<GNode> findMovableNode(GraphicalModelState modelState, String elementId) {
		GModelIndex index = modelState.getIndex();
		Optional<GModelElement> element = index.get(elementId);
		if (!element.isPresent()) {
			log.warn("Element with id " + elementId + " not found");
			return Optional.empty();
		}

		if (!(element.get() instanceof GNode)) {
			log.warn("Element " + elementId + " is not moveable");
			return Optional.empty();
		}

		return Optional.of((GNode) element.get());
	}

	private static void setBounds(GNode node, GBounds bounds) {
		node.setPosition(LayoutUtil.asPoint(bounds));
		node.setSize(LayoutUtil.asDimension(bounds));
	}
}
