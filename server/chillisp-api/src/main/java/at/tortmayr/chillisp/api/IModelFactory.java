package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.RequestModelAction;
import io.typefox.sprotty.api.SModelRoot;

public interface IModelFactory {

	default SModelRoot loadModel(IGraphicalLanguageServer server, RequestModelAction action) {
		SModelRoot root = new SModelRoot();
		root.setType("NONE");
		root.setId("ROOT");
		return root;
	}

}
