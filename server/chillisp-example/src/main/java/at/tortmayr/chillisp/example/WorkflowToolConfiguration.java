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

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IToolConfiguration;
import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.type.Tool;
import at.tortmayr.chillisp.example.tools.AutomatedTaskTool;
import at.tortmayr.chillisp.example.tools.ManualTaskTool;
import at.tortmayr.chillisp.example.tools.WeightedEdgeTool;

public class WorkflowToolConfiguration implements IToolConfiguration {

	@Override
	public Tool[] getTools(RequestToolsAction action, IGraphicalLanguageServer server) {
		Tool[] tools = { new AutomatedTaskTool(), new ManualTaskTool(), new WeightedEdgeTool() };
		return tools;

	}

}
