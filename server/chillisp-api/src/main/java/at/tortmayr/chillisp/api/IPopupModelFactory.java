package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.RequestPopupModelAction;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;

public interface IPopupModelFactory {
	SModelRoot createPopuModel(SModelElement element, RequestPopupModelAction action, IGraphicalLanguageServer server);

}
