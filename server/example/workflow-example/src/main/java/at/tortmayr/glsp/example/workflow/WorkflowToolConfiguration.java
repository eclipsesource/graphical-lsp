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

import com.google.common.base.Optional;

import at.tortmayr.glsp.api.action.kind.RequestToolsAction;
import at.tortmayr.glsp.api.tool.ExecutableTool;
import at.tortmayr.glsp.api.tool.NodeCreationTool;
import at.tortmayr.glsp.api.tool.ToolConfiguration;
import at.tortmayr.glsp.example.workflow.schema.TaskNode;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;

public class WorkflowToolConfiguration implements ToolConfiguration {
	public static final String AUTOMATED_TASK_TOOL_ID = "wf-automated-task-tool";
	public static final String MANUAL_TASK_TOOL_ID = "wf-manual-task-tool";
	public static final String WEIGHTED_EDGE_TOOL_ID = "wf-weighted-edge-tool";

	private ExecutableTool automatedTaskTool = new NodeCreationTool(AUTOMATED_TASK_TOOL_ID, "Automated Task") {

		@Override
		protected SModelElement createNode(Optional<Point> point) {
			TaskNode node = new TaskNode();
			node.setName("NewAutomatedTask");
			if (point.isPresent()) {
				node.setPosition(point.get());
			}
			// TODO: change after server-side rendering is implemented
			node.setLayout("hbox");

			return node;
		}

	};

	@Override
	public ExecutableTool[] getTools(RequestToolsAction action) {
		ExecutableTool[] tools = { automatedTaskTool };
		return tools;

	}

}
