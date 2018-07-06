package at.tortmayr.chillisp.api;

import java.util.function.Consumer;

import at.tortmayr.chillisp.api.actions.Action;
import io.typefox.sprotty.api.ILayoutEngine;
import io.typefox.sprotty.api.ServerStatus;

public interface IGraphicalLanguageServer extends Consumer<ActionMessage> {

	void initialize();

	Consumer<ActionMessage> getRemoteEndpoint();

	void setRemoteEndpoint(Consumer<ActionMessage> consumer);

	void setClientId(String clientId);

	String getClientId();

	void setStatus(ServerStatus serverStatus);

	ILayoutEngine getLayoutEngine();

	void dispatch(Action action);

	IModelFactory getModelFactory();

	IPopupModelFactory getPopupModelFactory();

	public interface Provider {
		IGraphicalLanguageServer getGraphicalLanguageServer(String clientId);

	}

}
