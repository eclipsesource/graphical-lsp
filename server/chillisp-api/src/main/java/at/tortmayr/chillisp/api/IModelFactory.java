package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.RequestModelAction;
import io.typefox.sprotty.api.SModelRoot;

public interface IModelFactory {

	SModelRoot loadModel(IGraphicalLanguageServer server, RequestModelAction action);

}
