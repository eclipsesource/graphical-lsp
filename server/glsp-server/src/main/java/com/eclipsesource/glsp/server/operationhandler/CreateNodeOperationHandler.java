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

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.GPoint;

public abstract class CreateNodeOperationHandler implements OperationHandler {

	protected String elementTypeId;

	@Override
	public Class<? extends Action> handlesActionType() {
		return CreateNodeOperationAction.class;
	}

	public CreateNodeOperationHandler(String elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	@Override
	public boolean handles(AbstractOperationAction action) {
		return OperationHandler.super.handles(action)
				? ((CreateNodeOperationAction) action).getElementTypeId().equals(elementTypeId)
				: false;
	}

	@Override
	public Optional<GModelRoot> execute(AbstractOperationAction action, GraphicalModelState modelState) {
		CreateNodeOperationAction executeAction = (CreateNodeOperationAction) action;

		GModelIndex index = modelState.getIndex();

		Optional<GModelElement> container = index.get(executeAction.getContainerId());
		if (!container.isPresent()) {
			container = Optional.of(modelState.getRoot());
		}

		Optional<GPoint> point = Optional.of(executeAction.getLocation());
		GModelElement element = createNode(point, modelState);
		container.get().getChildren().add(element);
		return Optional.of(modelState.getRoot());
	}

	protected abstract GNode createNode(Optional<GPoint> point, GraphicalModelState modelState);
}
