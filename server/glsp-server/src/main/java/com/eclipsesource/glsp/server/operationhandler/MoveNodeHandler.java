/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server.operationhandler;

import java.util.Optional;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.kind.ExecuteOperationAction;
import com.eclipsesource.glsp.api.action.kind.MoveOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

/**
 * Generic handler implementation for {@link MoveOperationAction}
 */
public class MoveNodeHandler implements OperationHandler {

	private static Logger log = Logger.getLogger(MoveNodeHandler.class);

	@Override
	public Class<?> handlesActionType() {
		return MoveOperationAction.class;
	}

	@Override
	public Optional<SModelRoot> execute(ExecuteOperationAction action, ModelState modelState) {
		MoveOperationAction moveAction = (MoveOperationAction) action;
		String elementId = moveAction.getElementId();
		Point location = moveAction.getLocation();
		if (elementId == null || location == null) {
			log.warn("Invalid Move Action; missing mandatory arguments");
			return Optional.empty();
		}

		SModelIndex index = modelState.getCurrentModelIndex();
		SModelElement element = index.get(elementId);
		if (element == null) {
			log.warn("Element with id " + elementId + " not found");
			return Optional.empty();
		}

		if (isNotMoveable(element)) {
			log.warn("Element " + elementId + " is not moveable");
			return Optional.empty();
		}

		SNode nodeToMove = (SNode) element;
		nodeToMove.setPosition(location);
		
		return Optional.of(modelState.getCurrentModel());
	}

	private boolean isNotMoveable(SModelElement element) {
		return !(element instanceof SNode);
	}

}
