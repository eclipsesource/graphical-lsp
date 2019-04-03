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

import java.util.ArrayList;
import java.util.Optional;

import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

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
	public Optional<SModelRoot> execute(AbstractOperationAction action, GraphicalModelState modelState) {
		CreateNodeOperationAction executeAction = (CreateNodeOperationAction) action;

		SModelIndex index = modelState.getIndex();

		Optional<SModelElement> container = index.get(executeAction.getContainerId());
		if (!container.isPresent()) {
			container = Optional.of(modelState.getRoot());
		}

		Optional<Point> point = Optional.of(executeAction.getLocation());
		SModelElement element = createNode(point, modelState);
		if (container.get().getChildren() == null) {
			container.get().setChildren(new ArrayList<SModelElement>());
		}
		container.get().getChildren().add(element);
		index.addToIndex(element, container.get());
		return Optional.of(modelState.getRoot());
	}

	protected abstract SNode createNode(Optional<Point> point, GraphicalModelState modelState);
}
