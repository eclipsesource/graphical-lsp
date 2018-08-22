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

import at.tortmayr.glsp.api.ToolConfiguration;
import at.tortmayr.glsp.api.ToolType;
import at.tortmayr.glsp.api.action.RequestToolsAction;
import at.tortmayr.glsp.api.utils.Tool;

public class WorkflowToolConfiguration implements ToolConfiguration {
	public static final String AUTOMATED_TASK_TOOL_ID = "wf-automated-task-tool";
	public static final String MANUAL_TASK_TOOL_ID = "wf-manual-task-tool";
	public static final String WEIGHTED_EDGE_TOOL_ID = "wf-weighted-edge-tool";

	@Override
	public Tool[] getTools(RequestToolsAction action) {
		Tool[] tools = { new Tool("Autotmated Task", AUTOMATED_TASK_TOOL_ID, ToolType.CREATION),
				new Tool("Manual Task", MANUAL_TASK_TOOL_ID, ToolType.CREATION),
				new Tool("Weighted Edge", WEIGHTED_EDGE_TOOL_ID, ToolType.CONNECTION) };
		return tools;

	}

}
