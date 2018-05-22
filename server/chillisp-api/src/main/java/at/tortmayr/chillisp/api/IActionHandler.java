package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.RequestModelAction;

public interface IActionHandler {

	void handle(RequestModelAction action);
}
