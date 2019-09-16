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

import static com.eclipsesource.glsp.server.util.GModelUtil.IS_CONNECTABLE;

import java.util.Optional;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.eclipsesource.glsp.graph.GModelRoot;

public abstract class CreateConnectionOperationHandler implements OperationHandler {

	protected final String elementTypeId;

	public CreateConnectionOperationHandler(String elementTypeId) {
		this.elementTypeId = elementTypeId;
	}
	
	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Create edge";
	}

	@Override
	public Class<?> handlesActionType() {
		return CreateConnectionOperationAction.class;
	}

	@Override
	public boolean handles(AbstractOperationAction action) {
		return OperationHandler.super.handles(action)
				? ((CreateConnectionOperationAction) action).getElementTypeId().equals(elementTypeId)
				: false;
	}

	@Override
	public void execute(AbstractOperationAction operationAction, GraphicalModelState modelState) {
		CreateConnectionOperationAction action = (CreateConnectionOperationAction) operationAction;
		if (action.getSourceElementId() == null || action.getTargetElementId() == null) {
			throw new IllegalArgumentException("Incomplete create connection action");
		}

		GModelIndex index = modelState.getIndex();

		Optional<GModelElement> source = index.findElement(action.getSourceElementId(), IS_CONNECTABLE);
		Optional<GModelElement> target = index.findElement(action.getTargetElementId(), IS_CONNECTABLE);
		if (!source.isPresent() || !target.isPresent()) {
			throw new IllegalArgumentException("Invalid source or target for source ID " + action.getSourceElementId()
					+ " and target ID " + action.getTargetElementId());
		}

		Optional<GEdge> connection = createConnection(source.get(), target.get(), modelState);
		if (!connection.isPresent()) {
			throw new IllegalArgumentException(
					String.format("Creation of connection failed for source: %s , target: %s", source.get().getId(),
							target.get().getId()));
		}
		GModelRoot currentModel = modelState.getRoot();
		currentModel.getChildren().add(connection.get());
	}

	protected abstract Optional<GEdge> createConnection(GModelElement source, GModelElement target,
			GraphicalModelState modelState);

}
