/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.example.workflow;

import at.tortmayr.glsp.api.action.kind.RequestOperationsAction;
import at.tortmayr.glsp.api.operations.Operation;
import at.tortmayr.glsp.api.operations.OperationConfiguration;
import at.tortmayr.glsp.api.operations.OperationKind;

public class WorkflowOperationConfiguration implements OperationConfiguration {

	public static final String AUTOMATED_TASK_ID = "wf-automated-task-tool";
	public static final String MANUAL_TASK_ID = "wf-manual-task-tool";
	public static final String WEIGHTED_EDGE_ID = "wf-weighted-edge-tool";

	@Override
	public Operation[] getOperations(RequestOperationsAction action) {
		Operation[] operations = { new Operation("New automated task", AUTOMATED_TASK_ID, OperationKind.CREATE_NODE) };
		return operations;
	}

}
