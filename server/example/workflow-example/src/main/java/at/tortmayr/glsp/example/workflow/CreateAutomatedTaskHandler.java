/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.example.workflow;

import java.util.ArrayList;
import java.util.Optional;

import at.tortmayr.glsp.api.action.kind.CreateNodeOperationAction;
import at.tortmayr.glsp.api.action.kind.ExecuteOperationAction;
import at.tortmayr.glsp.api.operations.CreateNodeOperationHandler;
import at.tortmayr.glsp.api.utils.SModelIndex;
import at.tortmayr.glsp.example.workflow.schema.Icon;
import at.tortmayr.glsp.example.workflow.schema.TaskNode;
import io.typefox.sprotty.api.LayoutOptions;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SCompartment;
import io.typefox.sprotty.api.SLabel;
import io.typefox.sprotty.api.SModelElement;

public class CreateAutomatedTaskHandler extends CreateNodeOperationHandler {

	@Override
	public boolean handles(ExecuteOperationAction action) {
		if (action instanceof CreateNodeOperationAction) {
			CreateNodeOperationAction createNodeOperationAction = (CreateNodeOperationAction) action;
			return WorkflowOperationConfiguration.AUTOMATED_TASK_ID
					.contentEquals(createNodeOperationAction.getElementId());
		}
		return false;
	}

	@Override
	protected SModelElement createNode(Optional<Point> point, SModelIndex index) {

		TaskNode taskNode = new TaskNode();
		int nodeCounter = getCounter(index, taskNode.getType());
		taskNode.setId("task" + nodeCounter);
		taskNode.setName("AutomatedTask" + nodeCounter);
		taskNode.setDuration(0);

		taskNode.setTaskType("automated");
		if (point.isPresent()) {
			taskNode.setPosition(point.get());
		}

		taskNode.setLayout("vbox");
		taskNode.setChildren(new ArrayList<SModelElement>());

		SCompartment compHeader = new SCompartment();
		compHeader.setId("task" + nodeCounter + "_header");
		compHeader.setType("comp:header");
		compHeader.setLayout("hbox");
		compHeader.setChildren(new ArrayList<SModelElement>());
		Icon icon = new Icon();
		icon.setId("task" + nodeCounter + "_icon");
		icon.setLayout("stack");
		LayoutOptions layoutOptions = new LayoutOptions();
		layoutOptions.setHAlign("center");
		layoutOptions.setResizeContainer(false);
		icon.setLayoutOptions(layoutOptions);
		icon.setChildren(new ArrayList<SModelElement>());
		SLabel iconLabel = new SLabel();
		iconLabel.setType("label:icon");
		iconLabel.setId("task" + nodeCounter + "_ticon");
		iconLabel.setText("" + taskNode.getTaskType().toUpperCase().charAt(0));
		icon.getChildren().add(iconLabel);

		SLabel heading = new SLabel();
		heading.setId("task" + nodeCounter + "_classname");
		heading.setType("label:heading");
		heading.setText("AutomatedTask" + nodeCounter);
		compHeader.getChildren().add(icon);
		compHeader.getChildren().add(heading);
		taskNode.getChildren().add(compHeader);

		return taskNode;
	}

	private int getCounter(SModelIndex index, String type) {
		int i = index.getTypeCount(type);
		return i + 1;
	}

}
