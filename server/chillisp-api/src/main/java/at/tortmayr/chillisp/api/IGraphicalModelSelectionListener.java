package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;

public interface IGraphicalModelSelectionListener {

	void selectionChanged(SelectAction acion, IGraphicalLanguageServer server);

	void selectionChanged(SelectAllAction action, IGraphicalLanguageServer server);

}
