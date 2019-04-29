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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.action.kind.DeleteOperationAction;
import com.eclipsesource.glsp.api.provider.CommandPaletteActionProvider;
import com.eclipsesource.glsp.api.types.LabeledAction;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.schema.ModelTypes;
import com.eclipsesource.glsp.example.workflow.schema.TaskNode;
import com.google.common.collect.Sets;

public class WorkflowCommandPaletteActionProvider implements CommandPaletteActionProvider {
	private static final LabeledAction CREATE_MERGE_NODE = new LabeledAction("Create Merge Node",
			new CreateNodeOperationAction(ModelTypes.MERGE_NODE, new Point(0, 0), null));
	private static final LabeledAction CREATE_DECISION_NODE = new LabeledAction("Create Decision Node",
			new CreateNodeOperationAction(ModelTypes.DECISION_NODE, new Point(0, 0), null));
	private static final LabeledAction CREATE_MANUAL_TASK = new LabeledAction("Create Manual Task",
			new CreateNodeOperationAction(ModelTypes.MANUAL_TASK, new Point(0, 0), null));
	private static final LabeledAction CREATE_AUTOMATED_TASK = new LabeledAction("Create Automated Task",
			new CreateNodeOperationAction(ModelTypes.AUTOMATED_TASK, new Point(0, 0), null));

	private static final Set<LabeledAction> CREATE_NODE_ACTIONS = Sets.newHashSet(CREATE_AUTOMATED_TASK,
			CREATE_MANUAL_TASK, CREATE_MERGE_NODE, CREATE_DECISION_NODE);

	@Override
	public Set<LabeledAction> getActions(SModelRoot root, List<String> selectedElementsIDs) {
		Set<LabeledAction> actions = Sets.newLinkedHashSet();

		SModelIndex index = new SModelIndex(root);
		Set<SModelElement> selectedElements = index.getAll(selectedElementsIDs);

		// Create node actions are always possible
		actions.addAll(CREATE_NODE_ACTIONS);

		// Create edge actions between two nodes
		if (selectedElements.size() == 1) {
			SModelElement element = selectedElements.iterator().next();
			if (element instanceof SNode) {
				actions.addAll(createEdgeActions((SNode) element, index.getAllByClass(TaskNode.class)));
			}
		} else if (selectedElements.size() == 2) {
			Iterator<SModelElement> iterator = selectedElements.iterator();
			SModelElement firstElement = iterator.next();
			SModelElement secondElement = iterator.next();
			if (firstElement instanceof TaskNode && secondElement instanceof TaskNode) {
				actions.add(createEdgeAction("Connect with Edge", (SNode) firstElement, (SNode) secondElement));
				actions.add(createWeightedEdgeAction("Connect with Weighted Edge", (SNode) firstElement,
						(SNode) secondElement));
			}
		}

		// Delete action
		if (selectedElements.size() == 1) {
			actions.add(new LabeledAction("Delete", new DeleteOperationAction(selectedElementsIDs)));
		} else if (selectedElements.size() > 1) {
			actions.add(new LabeledAction("Delete All", new DeleteOperationAction(selectedElementsIDs)));
		}

		return actions;
	}

	private Set<LabeledAction> createEdgeActions(SNode source, Set<? extends SNode> targets) {
		Set<LabeledAction> actions = Sets.newLinkedHashSet();
		// add first all edge, then all weighted edge actions to keep a nice order
		targets.forEach(node -> actions.add(createEdgeAction("Create Edge to " + getLabel(node), source, node)));
		targets.forEach(node -> actions
				.add(createWeightedEdgeAction("Create Weighted Edge to " + getLabel(node), source, node)));
		return actions;
	}

	private LabeledAction createWeightedEdgeAction(String label, SNode source, SNode node) {
		return new LabeledAction(label,
				new CreateConnectionOperationAction(ModelTypes.WEIGHTED_EDGE, source.getId(), node.getId()));
	}

	private LabeledAction createEdgeAction(String label, SNode source, SNode node) {
		return new LabeledAction(label, new CreateConnectionOperationAction(EDGE, source.getId(), node.getId()));
	}

	private String getLabel(SNode node) {
		if (node instanceof TaskNode) {
			return ((TaskNode) node).getName();
		}
		return node.getId();
	}

}
