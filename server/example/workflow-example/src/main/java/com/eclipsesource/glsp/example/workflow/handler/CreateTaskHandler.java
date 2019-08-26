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

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.utils.ModelTypes;
import com.eclipsesource.glsp.example.workflow.utils.WorkflowBuilder.TaskNodeBuilder;
import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphPackage;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;
import com.eclipsesource.glsp.server.util.GModelUtil;

public abstract class CreateTaskHandler extends CreateNodeOperationHandler {

	private Function<Integer, String> labelProvider;

	public CreateTaskHandler(String elementTypeId, Function<Integer, String> labelProvider) {
		super(elementTypeId);
		this.labelProvider = labelProvider;
	}

	@Override
	protected GNode createNode(Optional<GPoint> point, GraphicalModelState modelState) {
		int nodeCounter = GModelUtil.generateId(WfgraphPackage.Literals.TASK_NODE, "task", modelState);
		String name = labelProvider.apply(nodeCounter);
		String taskType = ModelTypes.toNodeType(elementTypeId);
		TaskNodeBuilder taskNodeBuilder = new TaskNodeBuilder(elementTypeId, name, taskType, 0);
		if (point.isPresent()) {
			taskNodeBuilder.setPosition(point.get().getX(), point.get().getY());
		}
		return taskNodeBuilder.build();
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Create task";
	}

}
