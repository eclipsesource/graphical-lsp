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
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.schema.ActivityNode;
import com.eclipsesource.glsp.example.workflow.schema.ModelTypes;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;

public class CreateMergeNodeHandler extends CreateNodeOperationHandler {

	@Override
	public boolean handles(AbstractOperationAction execAction) {
		if (execAction instanceof CreateNodeOperationAction) {
			CreateNodeOperationAction action = (CreateNodeOperationAction) execAction;
			return ModelTypes.MERGE_NODE.equals(action.getElementTypeId());
		}
		return false;
	}

	@Override
	protected SModelElement createNode(Optional<Point> point, SModelIndex index) {
		ActivityNode result = new ActivityNode();
		result.setNodeType("mergeNode");
		result.setType(ModelTypes.DECISION_NODE);
		point.ifPresent(result::setPosition);

		Function<Integer, String> idProvider = i -> "activityNode" + i;
		int i = getCounter(index, result.getType(), idProvider);
		result.setId(idProvider.apply(i));

		return result;
	}

}
