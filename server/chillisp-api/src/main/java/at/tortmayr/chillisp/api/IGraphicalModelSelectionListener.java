package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.Action;

public interface IGraphicalModelSelectionListener {

	void selectionChanged(Action action, IGraphicalLanguageServer server);

	public static class NullImpl implements IGraphicalModelSelectionListener {

		@Override
		public void selectionChanged(Action action, IGraphicalLanguageServer server) {
		}

	}

}
