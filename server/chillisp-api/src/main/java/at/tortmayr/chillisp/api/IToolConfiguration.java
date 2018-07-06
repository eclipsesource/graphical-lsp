package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.type.Tool;

public interface IToolConfiguration {

	 Tool[] getTools(RequestToolsAction action, IGraphicalLanguageServer server);
}
