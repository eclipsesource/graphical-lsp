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

import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.COMP_HEADER;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.LABEL_HEADING;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.LABEL_ICON;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sprotty.LayoutOptions;
import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.schema.Icon;
import com.eclipsesource.glsp.example.workflow.schema.TaskNode;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;
import com.eclipsesource.glsp.server.util.SModelUtil;

public abstract class CreateTaskHandler extends CreateNodeOperationHandler {

	private String taskType;
	private Function<Integer, String> labelProvider;

	public CreateTaskHandler(String elementTypeId, String taskType, Function<Integer, String> labelProvider) {
		super(elementTypeId);
		this.taskType = taskType;
		this.labelProvider = labelProvider;
	}

	@Override
	protected SNode createNode(Optional<Point> point, GraphicalModelState modelState) {
		TaskNode taskNode = new TaskNode();
		taskNode.setType(elementTypeId);
		int nodeCounter = SModelUtil.generateId(taskNode, "task", modelState);
		taskNode.setName(labelProvider.apply(nodeCounter));
		taskNode.setDuration(0);

		taskNode.setTaskType(taskType);
		if (point.isPresent()) {
			taskNode.setPosition(point.get());
		}

		taskNode.setLayout("vbox");
		taskNode.setChildren(new ArrayList<SModelElement>());

		SCompartment compHeader = new SCompartment();
		compHeader.setId(taskNode.getId() + "_header");
		compHeader.setType(COMP_HEADER);
		compHeader.setLayout("hbox");
		compHeader.setChildren(new ArrayList<SModelElement>());
		Icon icon = new Icon();
		icon.setId(taskNode.getId() + "_icon");
		icon.setLayout("stack");
		icon.setCommandId(SimulateCommandHandler.SIMULATE_COMMAND_ID);
		LayoutOptions layoutOptions = new LayoutOptions();
		layoutOptions.setHAlign("center");
		layoutOptions.setResizeContainer(false);
		icon.setLayoutOptions(layoutOptions);
		icon.setChildren(new ArrayList<SModelElement>());
		SLabel iconLabel = new SLabel();
		iconLabel.setType(LABEL_ICON);
		iconLabel.setId(taskNode.getId() + "_ticon");
		iconLabel.setText("" + taskNode.getTaskType().toUpperCase().charAt(0));
		icon.getChildren().add(iconLabel);

		SLabel heading = new SLabel();
		heading.setId("task" + nodeCounter + "_classname");
		heading.setType(LABEL_HEADING);
		heading.setText(labelProvider.apply(nodeCounter));
		compHeader.getChildren().add(icon);
		compHeader.getChildren().add(heading);
		taskNode.getChildren().add(compHeader);

		return taskNode;
	}

}
