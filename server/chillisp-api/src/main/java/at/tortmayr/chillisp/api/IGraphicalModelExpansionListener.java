package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.Action;

public interface IGraphicalModelExpansionListener {
	
	void expansionChanged(Action action, IGraphicalLanguageServer server);
	
	public static class NullImpl implements IGraphicalModelExpansionListener{

		@Override
		public void expansionChanged(Action action, IGraphicalLanguageServer server) {
		}
		
	}
}
