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
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.Optional;
import java.util.function.Function;

import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.action.kind.ExecuteOperationAction;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.WorkflowOperationConfiguration;
import com.eclipsesource.glsp.example.workflow.schema.ActivityNode;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;

import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;

public class CreateMergeNodeHandler extends CreateNodeOperationHandler {

	@Override
	public boolean handles(ExecuteOperationAction execAction) {
		if (execAction instanceof CreateNodeOperationAction) {
			CreateNodeOperationAction action = (CreateNodeOperationAction) execAction;
			return WorkflowOperationConfiguration.MERGE_NODE_ID.equals(action.getElementId());
		}
		return false;
	}

	@Override
	protected SModelElement createNode(Optional<Point> point, SModelIndex index) {
		ActivityNode result = new ActivityNode();
		result.setNodeType("mergeNode");
		point.ifPresent(result::setPosition);

		Function<Integer, String> idProvider = i -> "activityNode" + i;
		int i = getCounter(index, result.getType(), idProvider);
		result.setId(idProvider.apply(i));

		return result;
	}

}
