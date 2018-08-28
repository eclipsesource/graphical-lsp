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

public class WorkflowOperationHandlerProvider implements OperationHandlerProvider {

	List<OperationHandler> operationHandlers = new ArrayList<>();

	@Override
	public List<OperationHandler> getOperationHandlers() {
		operationHandlers.add(new CreateAutomatedTaskHandler());
		operationHandlers.add(new CreateWeightedEdgeHandler());
		operationHandlers.add(new DeleteWorkflowElementHandler());
		return operationHandlers;
	}

}
