package at.tortmayr.chillisp.api;

import io.typefox.sprotty.api.SModelRoot;

public interface IModelFactory {

	SModelRoot loadModel(IGraphicalLanguageServer server);

	public static class NullImpl implements IModelFactory {

		@Override
		public SModelRoot loadModel(IGraphicalLanguageServer server) {
			return null;
		}

	}

}
