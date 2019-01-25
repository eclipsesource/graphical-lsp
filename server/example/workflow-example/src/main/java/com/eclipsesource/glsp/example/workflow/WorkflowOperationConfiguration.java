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
package com.eclipsesource.glsp.example.workflow;

import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.AUTOMATED_TASK;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.DECISION_NODE;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.EDGE;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.MANUAL_TASK;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.MERGE_NODE;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.WEIGHTED_EDGE;

import com.eclipsesource.glsp.api.action.kind.RequestOperationsAction;
import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;

public class WorkflowOperationConfiguration implements OperationConfiguration {

	@Override
	public Operation[] getOperations(RequestOperationsAction action) {
		Operation createAutomatedTask = new Operation("Automated Task", AUTOMATED_TASK, Operation.Kind.CREATE_NODE);
		Operation createManualTask = new Operation("Manual Task", MANUAL_TASK, Operation.Kind.CREATE_NODE);
		Operation createDecisionNode = new Operation("Decision Node", DECISION_NODE, Operation.Kind.CREATE_NODE);
		Operation createMergeNode = new Operation("Merge Node", MERGE_NODE, Operation.Kind.CREATE_NODE);
		Operation createWeightedEdge = new Operation("Weighted Edge", WEIGHTED_EDGE, Operation.Kind.CREATE_CONNECTION);
		Operation createEdge = new Operation("Edge", EDGE, Operation.Kind.CREATE_CONNECTION);

		Operation[] operations = { createAutomatedTask, createManualTask, createDecisionNode, createMergeNode,
				createWeightedEdge, createEdge };
		return operations;
	}

}
