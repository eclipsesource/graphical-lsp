package at.tortmayr.chillisp.api;

import java.util.function.Consumer;

import at.tortmayr.chillisp.api.actions.Action;
import io.typefox.sprotty.api.ILayoutEngine;
import io.typefox.sprotty.api.ServerStatus;

public interface IGraphicalLanguageServer extends Consumer<ActionMessage> {
	Consumer<ActionMessage> getRemoteEndpoint();

	void setRemoteEndpoint(Consumer<ActionMessage> remoteEndpoint);

	void setClientId(String clientId);

	String getClientId();

	void setStatus(ServerStatus serverStatus);

	void setRequestActionHandler(IRequestActionHandler actionHandler);

	IRequestActionHandler getRequestActionHandler();

	ILayoutEngine getLayoutEngine();

	IGraphicalModelState getModelState();
	
	void dispatch(Action action);

	void setModelFactory(IModelFactory modelFactory);

	void setPopupModelFactory(IPopupModelFactory popupModelFactory);

	IModelFactory getModelFactory();

	IPopupModelFactory getPopupModelFactory();
	
	public interface Provider {
		IGraphicalLanguageServer getGraphicalLanguageServer(String clientId);
	}

}
