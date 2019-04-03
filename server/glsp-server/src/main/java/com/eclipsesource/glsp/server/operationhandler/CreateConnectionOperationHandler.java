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
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

public abstract class CreateConnectionOperationHandler implements OperationHandler {
	private static Logger log = Logger.getLogger(CreateConnectionOperationHandler.class);
	protected final String elementTypeId;

	public CreateConnectionOperationHandler(String elementTypeId) {
		this.elementTypeId = elementTypeId;
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
	public Optional<SModelRoot> execute(AbstractOperationAction operationAction, GraphicalModelState modelState) {
		CreateConnectionOperationAction action = (CreateConnectionOperationAction) operationAction;
		if (action.getSourceElementId() == null || action.getTargetElementId() == null) {
			log.warn("Incomplete create connection action");
			return Optional.empty();
		}

		SModelIndex index = modelState.getIndex();

		Optional<SNode> source = index.findElement(action.getSourceElementId(), SNode.class);
		Optional<SNode> target = index.findElement(action.getTargetElementId(), SNode.class);
		if (!source.isPresent() || !target.isPresent()) {
			log.warn("Invalid source or target for source ID " + action.getSourceElementId() + " and target ID "
					+ action.getTargetElementId());
			return Optional.empty();
		}

		SEdge connection = createConnection(source.get(), target.get(), modelState);
		SModelRoot currentModel = modelState.getRoot();
		currentModel.getChildren().add(connection);
		index.addToIndex(connection, currentModel);
		return Optional.of(currentModel);
	}

	protected abstract SEdge createConnection(SModelElement source, SModelElement target,
			GraphicalModelState modelState);

}
