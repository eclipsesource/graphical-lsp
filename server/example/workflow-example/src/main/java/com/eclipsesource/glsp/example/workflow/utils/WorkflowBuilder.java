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

import com.eclipsesource.glsp.example.workflow.handler.SimulateCommandHandler;
import com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode;
import com.eclipsesource.glsp.example.workflow.wfgraph.Icon;
import com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode;
import com.eclipsesource.glsp.example.workflow.wfgraph.WeightedEdge;
import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphFactory;
import com.eclipsesource.glsp.graph.GCompartment;
import com.eclipsesource.glsp.graph.GLabel;
import com.eclipsesource.glsp.graph.builder.AbstractGCompartmentBuilder;
import com.eclipsesource.glsp.graph.builder.AbstractGEdgeBuilder;
import com.eclipsesource.glsp.graph.builder.AbstractGNodeBuilder;
import com.eclipsesource.glsp.graph.builder.impl.GCompartmentBuilder;
import com.eclipsesource.glsp.graph.builder.impl.GLabelBuilder;
import com.eclipsesource.glsp.graph.builder.impl.GLayoutOptionsBuilder;

public final class WorkflowBuilder {

	public static class WeightedEdgeBuilder extends AbstractGEdgeBuilder<WeightedEdge, WeightedEdgeBuilder> {

		private String probability;

		public WeightedEdgeBuilder() {
			super(ModelTypes.WEIGHTED_EDGE);
		}

		public WeightedEdgeBuilder probability(String probability) {
			this.probability = probability;
			return self();
		}

		@Override
		protected void setProperties(WeightedEdge edge) {
			super.setProperties(edge);
			edge.setProbability(probability);
		}

		@Override
		protected WeightedEdge instantiate() {
			return WfgraphFactory.eINSTANCE.createWeightedEdge();
		}

		@Override
		protected WeightedEdgeBuilder self() {
			return this;
		}

	}

	public static class ActivityNodeBuilder extends AbstractGNodeBuilder<ActivityNode, ActivityNodeBuilder> {
		protected String nodeType;

		public ActivityNodeBuilder(String type, String nodeType) {
			super(type);
			this.nodeType = nodeType;
		}

		@Override
		protected void setProperties(ActivityNode node) {
			super.setProperties(node);
			node.setNodeType(nodeType);
		}

		@Override
		protected ActivityNode instantiate() {
			return WfgraphFactory.eINSTANCE.createActivityNode();
		}

		@Override
		protected ActivityNodeBuilder self() {
			return this;
		}
	}

	public static class TaskNodeBuilder extends AbstractGNodeBuilder<TaskNode, TaskNodeBuilder> {
		private String name;
		private String taskType;
		private int duration;

		public TaskNodeBuilder(String type, String name, String taskType, int duration) {
			super(type);
			this.name = name;
			this.taskType = taskType;
			this.duration = duration;

		}

		@Override
		protected TaskNode instantiate() {
			return WfgraphFactory.eINSTANCE.createTaskNode();
		}

		@Override
		protected TaskNodeBuilder self() {
			return this;
		}

		@Override
		public void setProperties(TaskNode taskNode) {
			super.setProperties(taskNode);
			taskNode.setName(name);
			taskNode.setTaskType(taskType);
			taskNode.setDuration(duration);
			taskNode.setLayout("vbox");
			taskNode.getChildren().add(createCompartment(taskNode));
		}

		private GCompartment createCompartment(TaskNode taskNode) {
			return new GCompartmentBuilder(ModelTypes.COMP_HEADER) //
					.id(taskNode.getId() + "_header") //
					.layout("hbox") //
					.add(createCompartmentIcon(taskNode)) //
					.add(createCompartmentHeader(taskNode)) //
					.build();
		}

		private GLabel createCompartmentHeader(TaskNode taskNode) {
			return new GLabelBuilder(ModelTypes.LABEL_HEADING) //
					.id(taskNode.getId() + "_classname") //
					.text(taskNode.getName()) //
					.build();
		}

		private Icon createCompartmentIcon(TaskNode taskNode) {
			return new IconBuilder() //
					.id(taskNode.getId() + "_icon") //
					.layout("stack") //
					.commandId(SimulateCommandHandler.SIMULATE_COMMAND_ID) //
					.layoutOptions(new GLayoutOptionsBuilder() //
							.hAlign("center") //
							.resizeContainer(false) //
							.build()) //
					.add(createCompartmentIconLabel(taskNode)).build();
		}

		private GLabel createCompartmentIconLabel(TaskNode taskNode) {
			return new GLabelBuilder(ModelTypes.LABEL_ICON) //
					.id(taskNode.getId() + "_ticon") //
					.text("" + taskNode.getTaskType().toUpperCase().charAt(0)) //
					.build();
		}

	}

	public static class IconBuilder extends AbstractGCompartmentBuilder<Icon, IconBuilder> {
		private String commandId;

		public IconBuilder() {
			super(ModelTypes.ICON);
		}

		public IconBuilder commandId(String commandId) {
			this.commandId = commandId;
			return self();
		}

		@Override
		protected Icon instantiate() {
			return WfgraphFactory.eINSTANCE.createIcon();
		}

		@Override
		protected void setProperties(Icon comp) {
			super.setProperties(comp);
			comp.setCommandId(commandId);
		}

		@Override
		protected IconBuilder self() {
			return this;
		}

	}

	private WorkflowBuilder() {
	}
}
