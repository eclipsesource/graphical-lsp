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
package at.tortmayr.glsp.example.workflow;

import java.util.ArrayList;
import java.util.List;

import at.tortmayr.glsp.api.operations.OperationHandler;
import at.tortmayr.glsp.api.operations.OperationHandlerProvider;
import at.tortmayr.glsp.server.handlers.DeleteHandler;
import at.tortmayr.glsp.server.handlers.MoveNodeHandler;

public class WorkflowOperationHandlerProvider implements OperationHandlerProvider {

	List<OperationHandler> operationHandlers = new ArrayList<>();

	@Override
	public List<OperationHandler> getOperationHandlers() {
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
