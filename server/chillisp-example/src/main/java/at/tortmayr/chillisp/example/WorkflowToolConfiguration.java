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
