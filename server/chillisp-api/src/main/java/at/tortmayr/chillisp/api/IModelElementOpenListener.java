package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.OpenAction;

public interface IModelElementOpenListener {

	 void elementOpened(OpenAction action, IGraphicalLanguageServer server);

}
