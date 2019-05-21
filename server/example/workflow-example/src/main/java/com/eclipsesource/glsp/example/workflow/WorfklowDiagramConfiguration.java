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

import static com.eclipsesource.glsp.api.utils.DefaultModelTypes.EDGE;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.AUTOMATED_TASK;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.COMP_HEADER;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.DECISION_NODE;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.ICON;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.LABEL_HEADING;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.LABEL_ICON;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.LABEL_TEXT;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.MANUAL_TASK;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.MERGE_NODE;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.WEIGHTED_EDGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.diagram.DiagramConfiguration;
import com.eclipsesource.glsp.api.operations.Group;
import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;
import com.eclipsesource.glsp.api.utils.DefaultModelTypes;
import com.eclipsesource.glsp.example.workflow.schema.ActivityNode;
import com.eclipsesource.glsp.example.workflow.schema.Icon;
import com.eclipsesource.glsp.example.workflow.schema.TaskNode;
import com.eclipsesource.glsp.example.workflow.schema.WeightedEdge;

public class WorfklowDiagramConfiguration implements DiagramConfiguration {

	@Override
	public String getDiagramType() {
		return "workflow-diagram";
	}

	@Override
	public Map<String, Class<? extends SModelElement>> getTypeMappings() {
		Map<String, Class<? extends SModelElement>> mappings = DefaultModelTypes.getDefaultTypeMappings();

		mappings.put(LABEL_HEADING, SLabel.class);
		mappings.put(LABEL_TEXT, SLabel.class);
		mappings.put(COMP_HEADER, SCompartment.class);
		mappings.put(LABEL_ICON, SLabel.class);
		mappings.put(WEIGHTED_EDGE, WeightedEdge.class);
		mappings.put(ICON, Icon.class);
		mappings.put(MERGE_NODE, ActivityNode.class);
		mappings.put(DECISION_NODE, ActivityNode.class);
		mappings.put(MANUAL_TASK, TaskNode.class);
		mappings.put(AUTOMATED_TASK, TaskNode.class);
		return mappings;
	}

	@Override
	public List<NodeTypeHint> getNodeTypeHints() {
		List<NodeTypeHint> nodeHints = new ArrayList<>();
		nodeHints.add(createDefaultNodeTypeHint(DECISION_NODE));
		nodeHints.add(createDefaultNodeTypeHint(MERGE_NODE));
		nodeHints.add(createDefaultNodeTypeHint(MANUAL_TASK));
		nodeHints.add(createDefaultNodeTypeHint(AUTOMATED_TASK));
		return nodeHints;
	}

	@Override
	public List<EdgeTypeHint> getEdgeTypeHints() {
		List<EdgeTypeHint> edgeHints = new ArrayList<EdgeTypeHint>();
		edgeHints.add(createDefaultEdgeTypeHint(WEIGHTED_EDGE));
		edgeHints.add(createDefaultEdgeTypeHint(EDGE));
		return edgeHints;
	}

	@Override
	public EdgeTypeHint createDefaultEdgeTypeHint(String elementId) {
		EdgeTypeHint hint = DiagramConfiguration.super.createDefaultEdgeTypeHint(elementId);
		hint.setSourceElementTypeIds(Arrays.asList(MANUAL_TASK, AUTOMATED_TASK, DECISION_NODE, MERGE_NODE));
		hint.setTargetElementTypeIds(Arrays.asList(MANUAL_TASK, AUTOMATED_TASK, DECISION_NODE, MERGE_NODE));
		return hint;
	}

	@Override
	public List<Operation> getOperations() {
		Group nodeGroup = new Group("workflow.nodes", "Nodes");
		Group edgeGroup = new Group("workflow.edges", "Edges");
		Operation createAutomatedTask = new Operation("Automated Task", AUTOMATED_TASK, Operation.Kind.CREATE_NODE,
				nodeGroup);
		Operation createManualTask = new Operation("Manual Task", MANUAL_TASK, Operation.Kind.CREATE_NODE, nodeGroup);
		Operation createDecisionNode = new Operation("Decision Node", DECISION_NODE, Operation.Kind.CREATE_NODE,
				nodeGroup);
		Operation createMergeNode = new Operation("Merge Node", MERGE_NODE, Operation.Kind.CREATE_NODE, nodeGroup);
		Operation createWeightedEdge = new Operation("Weighted Edge", WEIGHTED_EDGE, Operation.Kind.CREATE_CONNECTION,
				edgeGroup);
		Operation createEdge = new Operation("Edge", EDGE, Operation.Kind.CREATE_CONNECTION, edgeGroup);

		return Arrays.asList(createAutomatedTask, createManualTask, createDecisionNode, createMergeNode,
				createWeightedEdge, createEdge);
	}

}
