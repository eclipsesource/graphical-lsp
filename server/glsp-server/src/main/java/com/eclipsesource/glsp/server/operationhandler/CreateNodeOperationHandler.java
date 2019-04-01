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
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

public abstract class CreateNodeOperationHandler implements IOperationHandler {
	Logger log = Logger.getLogger(CreateNodeOperationAction.class);
	private boolean canCreateOnRoot;

	public CreateNodeOperationHandler(boolean canCreateOnRoot) {
		this.canCreateOnRoot = canCreateOnRoot;
	}

	@Override
	public boolean handles(Action action) {
		return action instanceof CreateNodeOperationAction;
	}

	protected Optional<SModelElement> retrieveContainer(Optional<String> containerId, IModelState modelState) {
		Optional<SModelElement> container = containerId.isPresent() ? modelState.getIndex().get(containerId.get())
				: null;
		if (!container.isPresent()) {
			if (canCreateOnRoot) {
				container = Optional.of(modelState.getCurrentModel());
			}
		}
		return container;

	}

	@Override
	public Optional<SModelRoot> execute(Action action, IModelState modelState) {
		CreateNodeOperationAction executeAction = (CreateNodeOperationAction) action;

		SModelIndex index = modelState.getIndex();

		Optional<Point> point = Optional.of(executeAction.getLocation());		
		Optional<SModelElement> container= retrieveContainer(Optional.ofNullable(executeAction.getContainerId()), modelState);
		if (!container.isPresent()) {
			log.error("No valid container found for node creation");
			return Optional.empty();
		}
		SModelElement element = createNode(point, container.get(), modelState);
		if (element != null) {
			if (container.get().getChildren() == null) {
				container.get().setChildren(new ArrayList<SModelElement>());
			}
			container.get().getChildren().add(element);
			index.addToIndex(element, container.get());
		}

		return Optional.of(modelState.getCurrentModel());
	}

	protected String generateID(SModelElement element, SModelIndex index) {
		int i = index.getTypeCount(element.getType());
		String id = element.getType().replace(":", "").toLowerCase();

		while (index.get(id + i) != null) {
			i++;
		}
		return id + i;
	}

	protected abstract SModelElement createNode(Optional<Point> point, SModelElement container, IModelState modelState);

	protected int getCounter(SModelIndex index, String type, Function<Integer, String> idProvider) {
		int i = index.getTypeCount(type)+1;
		while (true) {
			String id = idProvider.apply(i);
			if (!index.get(id).isPresent()) {
				break;
			}
			i++;
		}
		return i;
	}

}
