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
package com.eclipsesource.glsp.example.workflow.utils;

import java.util.List;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.handler.SimulateCommandHandler;
import com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode;
import com.eclipsesource.glsp.example.workflow.wfgraph.Icon;
import com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode;
import com.eclipsesource.glsp.example.workflow.wfgraph.WeightedEdge;
import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphFactory;
import com.eclipsesource.glsp.graph.DefaultTypes;
import com.eclipsesource.glsp.graph.GCompartment;
import com.eclipsesource.glsp.graph.GDimension;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GLabel;
import com.eclipsesource.glsp.graph.GLayoutOptions;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GraphFactory;
import com.eclipsesource.glsp.graph.util.GraphUtil;
import com.eclipsesource.glsp.server.util.GModelUtil;
import com.google.common.collect.Lists;

public final class WorkflowBuilder {

	public static abstract class GEdgeBuilder<T extends GEdge> {
		protected GraphicalModelState modelState;
		protected String id;
		protected String type;
		protected GModelElement source;
		protected GModelElement target;
		protected List<GPoint> routingPoints = Lists.newArrayList();

		public GEdgeBuilder(GraphicalModelState modelState, String type) {
			this.modelState = modelState;
			this.type = type;
		}

		public GEdgeBuilder<T> setId(String id) {
			this.id = id;
			return this;
		}

		public GEdgeBuilder<T> setSource(GModelElement source) {
			this.source = source;
			return this;
		}

		public GEdgeBuilder<T> setTarget(GModelElement target) {
			this.target = target;
			return this;
		}

		public GEdgeBuilder<T> addRoutingPoint(double x, double y) {
			this.routingPoints.add(GraphUtil.point(x, y));
			return this;
		}

		protected T fillData(T edge, String genId) {
			if (id == null) {
				GModelUtil.generateId(edge, genId, modelState);
			} else {
				edge.setId(id);
			}
			edge.setType(type);
			if (source != null) {
				edge.setSource(source);
				edge.setSourceId(source.getId());
			}
			if (target != null) {
				edge.setTarget(source);
				edge.setTargetId(target.getId());
			}
			this.routingPoints.forEach(edge.getRoutingPoints()::add);
			return edge;
		}

		abstract public T build();

	}

	public static class EdgeBuilder extends GEdgeBuilder<GEdge> {
		public EdgeBuilder(GraphicalModelState modelState) {
			super(modelState, DefaultTypes.EDGE);
		}

		@Override
		public GEdge build() {
			GEdge edge = GraphFactory.eINSTANCE.createGEdge();
			return fillData(edge, "edge");
		}
	}

	public static class WeightedEdgeBuilder extends GEdgeBuilder<WeightedEdge> {

		private String probability;

		public WeightedEdgeBuilder(GraphicalModelState modelState) {
			super(modelState, ModelTypes.WEIGHTED_EDGE);
		}

		public void setProbability(String probability) {
			this.probability = probability;
		}

		@Override
		public WeightedEdge build() {
			WeightedEdge edge = WfgraphFactory.eINSTANCE.createWeightedEdge();
			fillData(edge, "weightedEdge");
			edge.setProbability(probability);
			return edge;
		}

	}

	private static abstract class GNodeBuilder<T extends GNode> {
		protected GraphicalModelState modelState;
		protected String id;
		protected String type;
		protected GPoint position;
		protected GDimension size;

		public GNodeBuilder(GraphicalModelState modelState, String type) {
			this.modelState = modelState;
			this.type = type;
		}

		public GNodeBuilder<T> setId(String id) {
			this.id = id;
			return this;
		}

		public GNodeBuilder<T> setPosition(double x, double y) {
			this.position = GraphUtil.point(x, y);
			return this;
		}

		public GNodeBuilder<T> setSize(double width, double height) {
			this.size = GraphUtil.dimension(width, height);
			return this;
		}

		protected T fillData(T node, String genId) {
			if (id == null) {
				GModelUtil.generateId(node, genId, modelState);
			} else {
				node.setId(id);
			}
			node.setType(type);
			node.setPosition(position);
			node.setSize(size);
			return node;
		}

		abstract public T build();
	}

	public static class ActivityNodeBuilder extends GNodeBuilder<ActivityNode> {
		protected String nodeType;

		public ActivityNodeBuilder(GraphicalModelState modelState, String type, String nodeType) {
			super(modelState, type);
			this.nodeType = nodeType;
		}

		public ActivityNode build() {
			ActivityNode activityNode = WfgraphFactory.eINSTANCE.createActivityNode();
			fillData(activityNode, "activityNode");
			activityNode.setNodeType(nodeType);
			return activityNode;
		}
	}

	public static class TaskNodeBuilder extends GNodeBuilder<TaskNode> {
		private String name;
		private String taskType;
		private int duration;

		public TaskNodeBuilder(GraphicalModelState modelState, String type, String name, String taskType,
				int duration) {
			super(modelState, type);
			this.name = name;
			this.taskType = taskType;
			this.duration = duration;
		}

		public TaskNode build() {
			TaskNode taskNode = WfgraphFactory.eINSTANCE.createTaskNode();
			fillData(taskNode, "task");
			taskNode.setName(name);
			taskNode.setTaskType(taskType);
			taskNode.setDuration(duration);
			taskNode.setLayout("vbox");
			taskNode.getChildren().add(createCompartment(taskNode));
			return taskNode;
		}

		private GCompartment createCompartment(TaskNode taskNode) {
			GCompartment compHeader = GraphFactory.eINSTANCE.createGCompartment();
			compHeader.setType(ModelTypes.COMP_HEADER);
			compHeader.setId(taskNode.getId() + "_header");
			compHeader.setLayout("hbox");

			compHeader.getChildren().add(createCompartmentIcon(taskNode));
			compHeader.getChildren().add(createCompartmentHeader(taskNode));
			return compHeader;
		}

		private GLabel createCompartmentHeader(TaskNode taskNode) {
			GLabel heading = GraphFactory.eINSTANCE.createGLabel();
			heading.setType(ModelTypes.LABEL_HEADING);
			int nodeCounter = GModelUtil.generateId(taskNode.eClass(), "task", modelState);
			heading.setId("task" + nodeCounter + "_classname");
			heading.setText(taskNode.getName());
			return heading;
		}

		private Icon createCompartmentIcon(TaskNode taskNode) {
			Icon icon = WfgraphFactory.eINSTANCE.createIcon();
			icon.setType(ModelTypes.ICON);
			icon.setId(taskNode.getId() + "_icon");
			icon.setLayout("stack");
			icon.setCommandId(SimulateCommandHandler.SIMULATE_COMMAND_ID);

			GLayoutOptions layoutOptions = GraphFactory.eINSTANCE.createGLayoutOptions();
			layoutOptions.setHAlign("center");
			layoutOptions.setResizeContainer(false);
			icon.setLayoutOptions(layoutOptions);

			icon.getChildren().add(createCompartmentIconLabel(taskNode));
			return icon;
		}

		private GLabel createCompartmentIconLabel(TaskNode taskNode) {
			GLabel iconLabel = GraphFactory.eINSTANCE.createGLabel();
			iconLabel.setType(ModelTypes.LABEL_ICON);
			iconLabel.setId(taskNode.getId() + "_ticon");
			iconLabel.setText("" + taskNode.getTaskType().toUpperCase().charAt(0));
			return iconLabel;
		}
	}

	private WorkflowBuilder() {
	}
}
