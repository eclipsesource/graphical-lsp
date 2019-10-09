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

import static com.eclipsesource.glsp.api.jsonrpc.GLSPServerException.getOrThrow;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.ChangeBoundsOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.types.ElementAndBounds;
import com.eclipsesource.glsp.graph.GDimension;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.GPoint;

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
	public void execute(AbstractOperationAction action, GraphicalModelState modelState) {
		ChangeBoundsOperationAction changeBoundsAction = (ChangeBoundsOperationAction) action;
		for (ElementAndBounds element : changeBoundsAction.getNewBounds()) {
			changeElementBounds(element.getElementId(), element.getNewPosition(), element.getNewSize(), modelState);
		}
	}

	private void changeElementBounds(String elementId, GPoint newPosition, GDimension newSize,
			GraphicalModelState modelState) {
		if (elementId == null) {
			log.warn("Invalid ChangeBounds Action; missing mandatory arguments");
			return;
		}

		GModelIndex index = modelState.getIndex();
		GNode nodeToUpdate = getOrThrow(index.findElementByClass(elementId, GNode.class),
				"GNode with id " + elementId + " not found");

		nodeToUpdate.setPosition(newPosition);
		nodeToUpdate.setSize(newSize);
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Change bounds";
	}
}
