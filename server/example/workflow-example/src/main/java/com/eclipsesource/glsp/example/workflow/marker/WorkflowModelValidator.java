/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package com.eclipsesource.glsp.example.workflow.marker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.markers.Marker;
import com.eclipsesource.glsp.api.markers.MarkerKind;
import com.eclipsesource.glsp.api.markers.ModelValidator;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.example.workflow.schema.ActivityNode;
import com.eclipsesource.glsp.example.workflow.schema.TaskNode;

public class WorkflowModelValidator implements ModelValidator {

	@Override
	public List<Marker> validate(IModelState modelState, SModelElement... elements) {
		List<Marker> markers = new ArrayList<Marker>();

		for (SModelElement element : elements) {
			if (element instanceof TaskNode) {
				markers.addAll(validateTaskNode(modelState, element));
			} else if (element instanceof ActivityNode) {
				ActivityNode activityNode = (ActivityNode) element;
				if ("decisionNode".equals(activityNode.getNodeType())) {
					markers.addAll(validateDecisionNode(modelState, element));
				} else if ("mergeNode".equals(activityNode.getNodeType())) {
					markers.addAll(validateMergeNode(modelState, element));
				}
			}
			if (element.getChildren() != null) {
				markers.addAll(validate(modelState,
						element.getChildren().toArray(new SModelElement[element.getChildren().size()])));
			}
		}
		return markers;
	}

	private static List<Marker> validateTaskNode(IModelState modelState, SModelElement taskNode) {
		List<Marker> markers = new ArrayList<Marker>();
		validateTaskNode_isAutomated(modelState, taskNode).ifPresent(m -> markers.add(m));
		validateTaskNode_nameStartsUpperCase(modelState, taskNode).ifPresent(m -> markers.add(m));
		return markers;
	}

	private static Optional<Marker> validateTaskNode_isAutomated(IModelState modelState, SModelElement element) {
		TaskNode taskNode = (TaskNode) element;
		if ("automated".equals(taskNode.getTaskType())) {
			return Optional
					.of(new Marker("Automated task", "This is an automated taks", element.getId(), MarkerKind.INFO));
		}
		return Optional.empty();
	}

	private static Optional<Marker> validateTaskNode_nameStartsUpperCase(IModelState modelState,
			SModelElement element) {
		TaskNode taskNode = (TaskNode) element;
		if (!Character.isUpperCase(taskNode.getName().charAt(0))) {
			return Optional.of(new Marker("Task node name in upper case",
					"Task node names should start with upper case letters", element.getId(), MarkerKind.WARNING));
		}
		return Optional.empty();
	}

	private static List<Marker> validateDecisionNode(IModelState modelState, SModelElement decisionNode) {
		List<Marker> markers = new ArrayList<Marker>();
		validateDecisionNode_hasOneIncomingEdge(modelState, decisionNode).ifPresent(m -> markers.add(m));
		return markers;
	}

	private static Optional<Marker> validateDecisionNode_hasOneIncomingEdge(IModelState modelState,
			SModelElement decisionNode) {
		Collection<SEdge> incomingEdges = modelState.getIndex().getIncomingEdges(decisionNode);
		if (incomingEdges.size() > 1) {
			return Optional.of(new Marker("Too many incoming edges", "Decision node may only have one incoming edge.",
					decisionNode.getId(), MarkerKind.ERROR));
		} else if (incomingEdges.size() == 0) {
			return Optional.of(new Marker("Missing incoming edge", "Decision node must have one incoming edge.",
					decisionNode.getId(), MarkerKind.ERROR));
		}
		return Optional.empty();
	}

	private static List<Marker> validateMergeNode(IModelState modelState, SModelElement mergeNode) {
		List<Marker> markers = new ArrayList<Marker>();
		validateMergeNode_hasOneOutgoingEdge(modelState, mergeNode).ifPresent(m -> markers.add(m));
		return markers;
	}

	private static Optional<Marker> validateMergeNode_hasOneOutgoingEdge(IModelState modelState,
			SModelElement mergeNode) {
		Collection<SEdge> outgoingEdges = modelState.getIndex().getOutgoingEdges(mergeNode);
		if (outgoingEdges.size() > 1) {
			return Optional.of(new Marker("Too many outgoing edges", "Merge node may only have one outgoing edge.",
					mergeNode.getId(), MarkerKind.ERROR));
		} else if (outgoingEdges.size() == 0) {
			return Optional.of(new Marker("Missing outgoing edge", "Merge node must have one incoming edge.",
					mergeNode.getId(), MarkerKind.ERROR));
		}
		return Optional.empty();
	}
}
