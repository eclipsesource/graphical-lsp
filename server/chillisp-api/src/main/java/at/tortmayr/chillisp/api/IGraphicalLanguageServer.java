package at.tortmayr.chillisp.api;

import java.util.function.Consumer;

import io.typefox.sprotty.api.SModelRoot;

public interface IGraphicalLanguageServer extends Consumer<ActionMessage> {
	Consumer<ActionMessage> getRemoteEndpoint();

	void setRemoteEndpoint(Consumer<ActionMessage> remoteEndpoint);

	SModelRoot getModel();

	void setModel(SModelRoot root);

	void setClientId(String clientId);

	String getClientId();

	void setActionHandler(IActionHandler actionHandler);

	IActionHandler getActionHandler();

	public interface Provider {
		IGraphicalLanguageServer getGraphicalLanguageServer(String clientId);
	}

	void dispatch(Action action);

}
