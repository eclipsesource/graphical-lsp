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
	public static final String AUTOMATED_TASK_ID = "wf-automated-task";
	public static final String MANUAL_TASK_ID = "wf-manual-task";
	public static final String WEIGHTED_EDGE_ID = "wf-weighted-edge";
	public static final String EDGE_ID = "wf-edge";

	@Override
	public Operation[] getOperations(RequestOperationsAction action) {
		
		Operation createAutomatedTask= new Operation("Automated Task", AUTOMATED_TASK_ID, OperationKind.CREATE_NODE);
		Operation createManualTask= new Operation("Manual Task", MANUAL_TASK_ID, OperationKind.CREATE_NODE);
		Operation createWeightedEdge= new Operation("Weighted Edge", WEIGHTED_EDGE_ID, OperationKind.CREATE_CONNECTION);
		Operation createEdge = new Operation("Edge", EDGE_ID, OperationKind.CREATE_CONNECTION);
		Operation deleteElement= new Operation("Delete element", null, OperationKind.DELETE_ELEMENT);
		
		Operation[] operations = { createAutomatedTask,createManualTask,createWeightedEdge,deleteElement,createEdge};
		return operations;
	}

}
