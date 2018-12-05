/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow;

import com.eclipsesource.glsp.api.action.kind.RequestOperationsAction;
import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;

public class WorkflowOperationConfiguration implements OperationConfiguration {
	public static final String AUTOMATED_TASK_ID = "wf-automated-task";
	public static final String MANUAL_TASK_ID = "wf-manual-task";
	public static final String DECISION_NODE_ID = "wf-decision-node";
	public static final String MERGE_NODE_ID = "wf-merge-node";
	public static final String WEIGHTED_EDGE_ID = "wf-weighted-edge";
	public static final String EDGE_ID = "wf-edge";

	@Override
	public Operation[] getOperations(RequestOperationsAction action) {

		Operation createAutomatedTask = new Operation("Automated Task", AUTOMATED_TASK_ID, Operation.Kind.CREATE_NODE);
		Operation createManualTask = new Operation("Manual Task", MANUAL_TASK_ID, Operation.Kind.CREATE_NODE);
		Operation createDecisionNode = new Operation("Decision Node", DECISION_NODE_ID, Operation.Kind.CREATE_NODE);
		Operation createMergeNode = new Operation("Merge Node", MERGE_NODE_ID, Operation.Kind.CREATE_NODE);
		Operation createWeightedEdge = new Operation("Weighted Edge", WEIGHTED_EDGE_ID,
				Operation.Kind.CREATE_CONNECTION);
		Operation createEdge = new Operation("Edge", EDGE_ID, Operation.Kind.CREATE_CONNECTION);

		Operation[] operations = { createAutomatedTask, createManualTask, createDecisionNode, createMergeNode,
				createWeightedEdge, createEdge };
		return operations;
	}

}
