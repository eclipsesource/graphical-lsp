package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.OpenAction;

public interface IModelElementOpenListener {
	
	void elementOpened(OpenAction action, IGraphicalLanguageServer server);
	
	public static class NullImpl implements IModelElementOpenListener{

		@Override
		public void elementOpened(OpenAction action, IGraphicalLanguageServer server) {
		}
		
	}
}
