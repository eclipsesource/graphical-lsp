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

import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

public abstract class CreateNodeOperationHandler implements OperationHandler {

	@Override
	public boolean handles(AbstractOperationAction action) {
		return action instanceof CreateNodeOperationAction;
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
		SModelElement element = createNode(point, index);
		if (container.get().getChildren() == null) {
			container.get().setChildren(new ArrayList<SModelElement>());
		}
		container.get().getChildren().add(element);
		index.addToIndex(element, container.get());
		return Optional.of(modelState.getRoot());
	}

	protected String generateID(SModelElement element, SModelIndex index) {
		int i = index.getTypeCount(element.getType());
		String id = element.getType().replace(":", "").toLowerCase();

		while (index.get(id + i) != null) {
			i++;
		}
		return id + i;
	}

	protected abstract SModelElement createNode(Optional<Point> point, SModelIndex index);

	protected int getCounter(SModelIndex index, String type, Function<Integer, String> idProvider) {
		int i = index.getTypeCount(type);
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
