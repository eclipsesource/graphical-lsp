package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.CollapseExpandAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAllAction;

public interface IGraphicalModelExpansionListener {
	void expansionChanged(CollapseExpandAction action, IGraphicalLanguageServer server);

	void expansionChanged(CollapseExpandAllAction action, IGraphicalLanguageServer server);

}
