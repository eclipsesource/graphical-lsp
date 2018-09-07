/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server.handlers;

import java.util.Optional;

import com.eclipsesource.glsp.api.action.ExecuteOperationAction;
import com.eclipsesource.glsp.api.action.kind.MoveOperationAction;
import com.eclipsesource.glsp.api.factory.GraphicalModelState;
import com.eclipsesource.glsp.api.operations.OperationHandler;
import com.eclipsesource.glsp.api.utils.SModelIndex;

import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

/**
 * Generic handler implementation for {@link MoveOperationAction}
 */
public class MoveNodeHandler implements OperationHandler {

	@Override
	public boolean handles(ExecuteOperationAction action) {
		return action instanceof MoveOperationAction;
	}

	@Override
	public Optional<SModelRoot> execute(ExecuteOperationAction action, GraphicalModelState modelState) {
		MoveOperationAction moveAction = (MoveOperationAction)action;
		String elementId = moveAction.getElementId();
		Point location = moveAction.getLocation();
		
		if (elementId == null || location == null) {
			System.out.println("Invalid Move Action; missing mandatory arguments");
			return Optional.empty();
		}
		
		SModelIndex index = modelState.getCurrentModelIndex();
		SModelRoot currentModel = modelState.getCurrentModel();
		
		SModelElement element = index.get(elementId);
		if (element == null) {
			System.out.println("Element with id "+elementId+" not found");
			return Optional.empty();
		}
		
		if (false == element instanceof SNode) {
			System.out.println("Element "+elementId+" is not moveable");
			return Optional.empty();
		}
		
		SNode nodeToMove = (SNode)element;
		nodeToMove.setPosition(location);
		
		return Optional.of(currentModel);
	}

}
