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
package com.eclipsesource.glsp.example.modelserver.workflow;

import static com.eclipsesource.glsp.api.operations.Operation.Kind.CREATE_CONNECTION;
import static com.eclipsesource.glsp.api.operations.Operation.Kind.CREATE_NODE;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.AUTOMATED_TASK;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.DECISION_NODE;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.MANUAL_TASK;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.MERGE_NODE;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.WEIGHTED_EDGE;
import static com.eclipsesource.glsp.graph.DefaultTypes.EDGE;

import java.util.Arrays;
import java.util.List;

import com.eclipsesource.glsp.api.operations.Group;
import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.example.workflow.WorkflowDiagramConfiguration;

public class WorfklowDiagramNotationConfiguration extends WorkflowDiagramConfiguration {

	@Override
	public String getDiagramType() {
		return "workflow-diagram-notation";
	}

	@Override
	public List<Operation> getOperations() {
		Group nodeGroup = new Group("workflow.nodes", "Nodes");
		Group edgeGroup = new Group("workflow.edges", "Edges");
		Operation createAutomatedTask = new Operation("Automated Task", AUTOMATED_TASK, CREATE_NODE, nodeGroup);
		Operation createManualTask = new Operation("Manual Task", MANUAL_TASK, CREATE_NODE, nodeGroup);
		Operation createDecisionNode = new Operation("Decision Node", DECISION_NODE, CREATE_NODE, nodeGroup);
		Operation createMergeNode = new Operation("Merge Node", MERGE_NODE, CREATE_NODE, nodeGroup);
		Operation createWeightedEdge = new Operation("Weighted Edge", WEIGHTED_EDGE, CREATE_CONNECTION, edgeGroup);
		Operation createEdge = new Operation("Edge", EDGE, CREATE_CONNECTION, edgeGroup);
		return Arrays.asList(createAutomatedTask, createManualTask, createDecisionNode, createMergeNode,
				createWeightedEdge, createEdge);
	}

}
