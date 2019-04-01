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

import org.apache.log4j.Logger;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.ChangeContainerOperationAction;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

public class ChangeContainerOperationHandler implements IOperationHandler {
	private static Logger log = Logger.getLogger(ChangeContainerOperationHandler.class);

	@Override
	public Class<?> handlesActionType() {
		return ChangeContainerOperationAction.class;
	}
	
	protected Optional<SModelElement> retrieveContainer(String containerId, IModelState modelState) {
		Optional<SModelElement> container =modelState.getIndex().get(containerId);
		return container;
	}

	@Override
	public Optional<SModelRoot> execute(Action operationAction, IModelState modelState) {
		ChangeContainerOperationAction action = (ChangeContainerOperationAction) operationAction;
		SModelIndex index = modelState.getIndex();
		Optional<SModelElement> element = index.get(action.getElementId());
		Optional<SModelElement> container= retrieveContainer(action.getTargetContainerId(),modelState);
		if (!element.isPresent() || !container.isPresent()) {
			log.warn("Incomplete change container action");
			return Optional.empty();
		}
		if (!changeContainer(element.get(), container.get(),modelState)) {
			log.warn("Container change operation not successfull");
			return Optional.empty();
		}
		SModelRoot currentModel = modelState.getCurrentModel();
		return Optional.of(currentModel);
	}
	
	protected boolean changeContainer(SModelElement newChild, SModelElement container, IModelState modelState) {
		Optional<SModelElement> oldParent= modelState.getIndex().getParent(newChild);
		if (!oldParent.isPresent()) {
			return false;
		}
		oldParent.get().getChildren().remove(newChild);
		modelState.getIndex().removeFromIndex(newChild);
		if (container.getChildren()==null) {
			container.setChildren(new ArrayList<>());
		}
		container.getChildren().add(newChild);
		modelState.getIndex().addToIndex(newChild, container);
		return true;
	}
}
