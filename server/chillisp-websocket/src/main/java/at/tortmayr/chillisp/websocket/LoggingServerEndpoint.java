package at.tortmayr.chillisp.websocket;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import at.tortmayr.chillisp.api.ActionMessage;

public class LoggingServerEndpoint extends GraphicalLanguageServerEndpoint {
	public void onOpen(Session session, EndpointConfig config) {
		System.out.println("Opened connection [" + session.getId() + "]");
		super.onOpen(session, config);
	}

	public void onClose(Session session, CloseReason closeReason) {
		System.out.println("Closed connection [" + session.getId() + "]");
		super.onClose(session, closeReason);
	}

	public void accept(ActionMessage message) {
		System.out.println("SERVER: " + message);
		super.accept(message);
	}

	protected void fireMessageReceived(ActionMessage message) {
		System.out.println("CLIENT:" + message);
		super.fireMessageReceived(message);
	}

}
