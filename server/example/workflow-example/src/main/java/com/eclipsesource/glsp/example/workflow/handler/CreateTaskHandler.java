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
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.Optional;
import java.util.function.Function;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.utils.ModelTypes;
import com.eclipsesource.glsp.example.workflow.wfgraph.Icon;
import com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode;
import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphFactory;
import com.eclipsesource.glsp.graph.GCompartment;
import com.eclipsesource.glsp.graph.GLabel;
import com.eclipsesource.glsp.graph.GLayoutOptions;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GraphFactory;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;
import com.eclipsesource.glsp.server.util.GModelUtil;

public abstract class CreateTaskHandler extends CreateNodeOperationHandler {

	private String taskType;
	private Function<Integer, String> labelProvider;

	public CreateTaskHandler(String elementTypeId, String taskType, Function<Integer, String> labelProvider) {
		super(elementTypeId);
		this.taskType = taskType;
		this.labelProvider = labelProvider;
	}

	@Override
	protected GNode createNode(Optional<GPoint> point, GraphicalModelState modelState) {
		TaskNode taskNode = WfgraphFactory.eINSTANCE.createTaskNode();
		taskNode.setType(elementTypeId);
		int nodeCounter = GModelUtil.generateId(taskNode, "task", modelState);
		taskNode.setName(labelProvider.apply(nodeCounter));
		taskNode.setDuration(0);

		taskNode.setTaskType(taskType);
		if (point.isPresent()) {
			taskNode.setPosition(point.get());
		}

		taskNode.setLayout("vbox");

		GCompartment compHeader = GraphFactory.eINSTANCE.createGCompartment();
		compHeader.setType(ModelTypes.COMP_HEADER);
		compHeader.setId(taskNode.getId() + "_header");
		compHeader.setLayout("hbox");
		Icon icon = WfgraphFactory.eINSTANCE.createIcon();
		icon.setType(ModelTypes.ICON);
		icon.setId(taskNode.getId() + "_icon");
		icon.setLayout("stack");
		icon.setCommandId(SimulateCommandHandler.SIMULATE_COMMAND_ID);
		GLayoutOptions layoutOptions = GraphFactory.eINSTANCE.createGLayoutOptions();
		layoutOptions.setHAlign("center");
		layoutOptions.setResizeContainer(false);
		icon.setLayoutOptions(layoutOptions);
		GLabel iconLabel = GraphFactory.eINSTANCE.createGLabel();
		iconLabel.setType(ModelTypes.LABEL_ICON);
		iconLabel.setId(taskNode.getId() + "_ticon");
		iconLabel.setText("" + taskNode.getTaskType().toUpperCase().charAt(0));
		icon.getChildren().add(iconLabel);

		GLabel heading = GraphFactory.eINSTANCE.createGLabel();
		heading.setType(ModelTypes.LABEL_HEADING);
		heading.setId("task" + nodeCounter + "_classname");
		heading.setText(labelProvider.apply(nodeCounter));
		compHeader.getChildren().add(icon);
		compHeader.getChildren().add(heading);
		taskNode.getChildren().add(compHeader);

		return taskNode;
	}
	
	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Create task";
	}

}
