package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.type.Tool;

public interface IToolConfiguration {

	default Tool[] getTools(RequestToolsAction action, IGraphicalLanguageServer server) {
		return new Tool[0];
	}
}
