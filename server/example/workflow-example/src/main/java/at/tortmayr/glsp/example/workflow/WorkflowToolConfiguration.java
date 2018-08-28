/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.example.workflow;

import java.util.ArrayList;

import com.google.common.base.Optional;

import at.tortmayr.glsp.api.action.kind.RequestToolsAction;
import at.tortmayr.glsp.api.tool.ExecutableTool;
import at.tortmayr.glsp.api.tool.NodeCreationTool;
import at.tortmayr.glsp.api.tool.ToolConfiguration;
import at.tortmayr.glsp.api.utils.SModelIndex;
import at.tortmayr.glsp.example.workflow.schema.Icon;
import at.tortmayr.glsp.example.workflow.schema.TaskNode;
import io.typefox.sprotty.api.LayoutOptions;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SCompartment;
import io.typefox.sprotty.api.SLabel;
import io.typefox.sprotty.api.SModelElement;

public class WorkflowToolConfiguration implements ToolConfiguration {
	public static final String AUTOMATED_TASK_TOOL_ID = "wf-automated-task-tool";
	public static final String MANUAL_TASK_TOOL_ID = "wf-manual-task-tool";
	public static final String WEIGHTED_EDGE_TOOL_ID = "wf-weighted-edge-tool";

	private ExecutableTool automatedTaskTool = new NodeCreationTool(AUTOMATED_TASK_TOOL_ID, "Automated Task") {

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

	};

	@Override
	public ExecutableTool[] getTools(RequestToolsAction action) {
		ExecutableTool[] tools = { automatedTaskTool };
		return tools;

	}

}
