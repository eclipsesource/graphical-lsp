/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow;

import java.util.HashSet;
import java.util.Set;

import com.eclipsesource.glsp.api.operations.OperationHandler;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.eclipsesource.glsp.example.workflow.handler.CreateAutomatedTaskHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateDecisionNodeHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateEdgeHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateManualTaskHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateMergeNodeHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateWeightedEdgeHandler;
import com.eclipsesource.glsp.example.workflow.handler.DeleteWorkflowElementHandler;
import com.eclipsesource.glsp.server.operationhandler.DeleteHandler;
import com.eclipsesource.glsp.server.operationhandler.MoveNodeHandler;

public class WorkflowOperationHandlerProvider implements OperationHandlerProvider {

	Set<OperationHandler> operationHandlers = new HashSet<>();

	@Override
	public Set<OperationHandler> getOperationHandlers() {
		operationHandlers.add(new CreateAutomatedTaskHandler());
		operationHandlers.add(new CreateManualTaskHandler());
		operationHandlers.add(new CreateDecisionNodeHandler());
		operationHandlers.add(new CreateMergeNodeHandler());
		operationHandlers.add(new CreateWeightedEdgeHandler());
		operationHandlers.add(new CreateEdgeHandler());
		operationHandlers.add(new DeleteWorkflowElementHandler());
		operationHandlers.add(new MoveNodeHandler());
		operationHandlers.add(new DeleteHandler());
		return operationHandlers;
	}

}
