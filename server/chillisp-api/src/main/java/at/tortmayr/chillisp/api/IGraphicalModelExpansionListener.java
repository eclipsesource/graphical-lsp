package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.Action;
import at.tortmayr.chillisp.api.actions.CollapseExpandAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAllAction;

public interface IGraphicalModelExpansionListener {
	void expansionChanged(CollapseExpandAction action, IGraphicalLanguageServer server);
	void expansionChanged(CollapseExpandAllAction action, IGraphicalLanguageServer server);
	
	public static class NullImpl implements IGraphicalModelExpansionListener{

		@Override
		public void expansionChanged(CollapseExpandAction action, IGraphicalLanguageServer server) {
		}

		@Override
		public void expansionChanged(CollapseExpandAllAction action, IGraphicalLanguageServer server) {	
		}

		
	}
}
