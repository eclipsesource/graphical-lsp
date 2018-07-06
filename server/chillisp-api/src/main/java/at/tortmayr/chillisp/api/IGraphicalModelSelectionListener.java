package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;

public interface IGraphicalModelSelectionListener {
	
	default void selectionChanged(SelectAction acion, IGraphicalLanguageServer server) {

	}

	default void selectionChanged(SelectAllAction action, IGraphicalLanguageServer server) {

	}

}
