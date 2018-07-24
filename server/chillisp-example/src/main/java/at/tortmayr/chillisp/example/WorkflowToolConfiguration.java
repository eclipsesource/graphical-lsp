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
package at.tortmayr.chillisp.example;

import at.tortmayr.chillisp.api.ToolConfiguration;
import at.tortmayr.chillisp.api.action.RequestToolsAction;
import at.tortmayr.chillisp.api.utils.Tool;
import at.tortmayr.chillisp.example.tools.AutomatedTaskTool;
import at.tortmayr.chillisp.example.tools.ManualTaskTool;
import at.tortmayr.chillisp.example.tools.WeightedEdgeTool;

public class WorkflowToolConfiguration implements ToolConfiguration {

	@Override
	public Tool[] getTools(RequestToolsAction action) {
		Tool[] tools = { new AutomatedTaskTool(), new ManualTaskTool(), new WeightedEdgeTool() };
		return tools;

	}

}
