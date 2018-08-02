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
import at.tortmayr.glsp.api.action.RequestToolsAction;
import at.tortmayr.glsp.api.utils.Tool;
import at.tortmayr.glsp.example.workflow.tools.AutomatedTaskTool;
import at.tortmayr.glsp.example.workflow.tools.ManualTaskTool;
import at.tortmayr.glsp.example.workflow.tools.WeightedEdgeTool;

public class WorkflowToolConfiguration implements ToolConfiguration {

	@Override
	public Tool[] getTools(RequestToolsAction action) {
		Tool[] tools = { new AutomatedTaskTool(), new ManualTaskTool(), new WeightedEdgeTool() };
		return tools;

	}

}
