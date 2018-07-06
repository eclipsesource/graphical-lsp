package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.OpenAction;

public interface IModelElementOpenListener {

	default void elementOpened(OpenAction action, IGraphicalLanguageServer server) {
	};

}
