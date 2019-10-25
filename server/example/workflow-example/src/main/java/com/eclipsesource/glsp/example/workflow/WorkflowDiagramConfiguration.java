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

import static com.eclipsesource.glsp.api.operations.Operation.Kind.CREATE_CONNECTION;
import static com.eclipsesource.glsp.api.operations.Operation.Kind.CREATE_NODE;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.AUTOMATED_TASK;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.COMP_HEADER;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.DECISION_NODE;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.FORK_NODE;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.ICON;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.JOIN_NODE;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.LABEL_HEADING;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.LABEL_ICON;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.LABEL_TEXT;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.MANUAL_TASK;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.MERGE_NODE;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.WEIGHTED_EDGE;
import static com.eclipsesource.glsp.graph.DefaultTypes.EDGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;

import com.eclipsesource.glsp.api.diagram.DiagramConfiguration;
import com.eclipsesource.glsp.api.operations.Group;
import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.ShapeTypeHint;
import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphPackage;
import com.eclipsesource.glsp.graph.DefaultTypes;
import com.eclipsesource.glsp.graph.GraphPackage;

public class WorkflowDiagramConfiguration implements DiagramConfiguration {

	@Override
	public String getDiagramType() {
		return "workflow-diagram";
	}

	@Override
	public List<Operation> getOperations() {
		Group nodeGroup = new Group("workflow.nodes", "Nodes");
		Group edgeGroup = new Group("workflow.edges", "Edges");
		Operation createAutomatedTask = new Operation("Automated Task", AUTOMATED_TASK, CREATE_NODE, nodeGroup);
		Operation createManualTask = new Operation("Manual Task", MANUAL_TASK, CREATE_NODE, nodeGroup);
		Operation createDecisionNode = new Operation("Decision Node", DECISION_NODE, CREATE_NODE, nodeGroup);
		Operation createMergeNode = new Operation("Merge Node", MERGE_NODE, CREATE_NODE, nodeGroup);
		Operation createForkNode = new Operation("Fork Node", FORK_NODE, CREATE_NODE, nodeGroup);
		Operation createJoinNode = new Operation("Join Node", JOIN_NODE, CREATE_NODE, nodeGroup);
		Operation createWeightedEdge = new Operation("Weighted Edge", WEIGHTED_EDGE, CREATE_CONNECTION, edgeGroup);
		Operation createEdge = new Operation("Edge", EDGE, CREATE_CONNECTION, edgeGroup);
		return Arrays.asList(createAutomatedTask, createManualTask, createDecisionNode, createMergeNode, createForkNode,
				createJoinNode, createWeightedEdge, createEdge);
	}

	@Override
	public Map<String, EClass> getTypeMappings() {
		Map<String, EClass> mappings = DefaultTypes.getDefaultTypeMappings();
		mappings.put(LABEL_HEADING, GraphPackage.Literals.GLABEL);
		mappings.put(LABEL_TEXT, GraphPackage.Literals.GLABEL);
		mappings.put(COMP_HEADER, GraphPackage.Literals.GCOMPARTMENT);
		mappings.put(LABEL_ICON, GraphPackage.Literals.GLABEL);
		mappings.put(WEIGHTED_EDGE, GraphPackage.Literals.GEDGE);
		mappings.put(ICON, WfgraphPackage.Literals.ICON);
		mappings.put(MERGE_NODE, WfgraphPackage.Literals.ACTIVITY_NODE);
		mappings.put(DECISION_NODE, WfgraphPackage.Literals.ACTIVITY_NODE);
		mappings.put(FORK_NODE, WfgraphPackage.Literals.ACTIVITY_NODE);
		mappings.put(JOIN_NODE, WfgraphPackage.Literals.ACTIVITY_NODE);
		mappings.put(MANUAL_TASK, WfgraphPackage.Literals.TASK_NODE);
		mappings.put(AUTOMATED_TASK, WfgraphPackage.Literals.TASK_NODE);
		return mappings;
	}

	@Override
	public List<ShapeTypeHint> getNodeTypeHints() {
		List<ShapeTypeHint> nodeHints = new ArrayList<>();
		nodeHints.add(new ShapeTypeHint(MANUAL_TASK, true, true, false, false));
		nodeHints.add(new ShapeTypeHint(AUTOMATED_TASK, true, true, false, false));
		nodeHints.add(new ShapeTypeHint(FORK_NODE, true, true, false, false));
		nodeHints.add(createDefaultNodeTypeHint(JOIN_NODE));
		nodeHints.add(createDefaultNodeTypeHint(DECISION_NODE));
		nodeHints.add(createDefaultNodeTypeHint(MERGE_NODE));
		return nodeHints;
	}

	@Override
	public List<EdgeTypeHint> getEdgeTypeHints() {
		List<EdgeTypeHint> edgeHints = new ArrayList<EdgeTypeHint>();
		edgeHints.add(createDefaultEdgeTypeHint(EDGE));
		EdgeTypeHint weightedEdgeHint = DiagramConfiguration.super.createDefaultEdgeTypeHint(WEIGHTED_EDGE);
		weightedEdgeHint.setSourceElementTypeIds(Arrays.asList(DECISION_NODE));
		weightedEdgeHint.setTargetElementTypeIds(Arrays.asList(MANUAL_TASK, AUTOMATED_TASK, FORK_NODE, JOIN_NODE));
		edgeHints.add(weightedEdgeHint);
		return edgeHints;
	}

	@Override
	public EdgeTypeHint createDefaultEdgeTypeHint(String elementId) {
		EdgeTypeHint hint = DiagramConfiguration.super.createDefaultEdgeTypeHint(elementId);
		hint.setSourceElementTypeIds(
				Arrays.asList(MANUAL_TASK, AUTOMATED_TASK, DECISION_NODE, MERGE_NODE, FORK_NODE, JOIN_NODE));
		hint.setTargetElementTypeIds(
				Arrays.asList(MANUAL_TASK, AUTOMATED_TASK, DECISION_NODE, MERGE_NODE, FORK_NODE, JOIN_NODE));
		return hint;
	}

}
