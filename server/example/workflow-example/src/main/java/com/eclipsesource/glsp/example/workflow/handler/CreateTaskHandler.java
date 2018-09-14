/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.schema.Icon;
import com.eclipsesource.glsp.example.workflow.schema.TaskNode;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;

import io.typefox.sprotty.api.LayoutOptions;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SCompartment;
import io.typefox.sprotty.api.SLabel;
import io.typefox.sprotty.api.SModelElement;

public abstract class CreateTaskHandler extends CreateNodeOperationHandler {

	private String taskType;
	private Function<Integer, String> labelProvider;

	public CreateTaskHandler(String taskType, Function<Integer, String> labelProvider) {
		this.taskType = taskType;
		this.labelProvider = labelProvider;
	}

	@Override
	protected SModelElement createNode(Optional<Point> point, SModelIndex index) {
		TaskNode taskNode = new TaskNode();
		String type = taskNode.getType();
		Function<Integer, String> idProvider = i -> "task" + i;
		int nodeCounter = getCounter(index, type, idProvider);
		taskNode.setId(idProvider.apply(nodeCounter));
		taskNode.setName(labelProvider.apply(nodeCounter));
		taskNode.setDuration(0);

		taskNode.setTaskType(taskType);
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
		heading.setText(labelProvider.apply(nodeCounter));
		compHeader.getChildren().add(icon);
		compHeader.getChildren().add(heading);
		taskNode.getChildren().add(compHeader);

		return taskNode;
	}

}
