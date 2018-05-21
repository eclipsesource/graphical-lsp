package chillisp.websocket.impl;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/glserver")
public class GraphicalLanguageServerSocket {
	private Session session;
	private RemoteEndpoint.Async remote;

	@OnClose
	public void onWebSocketClose(CloseReason close) {
		this.session = null;
		this.remote = null;
		System.out.println(String.format("WebSocket Close: %s - %s", close.getCloseCode(), close.getReasonPhrase()));
	}

	@OnOpen
	public void onWebSocketOpen(Session session) {
		this.session = session;
		this.remote = this.session.getAsyncRemote();
		System.out.println(String.format("WebSocket Connect: %s", session));
		this.remote.sendText("You are now connected to " + this.getClass().getName());
	}

	@OnError
	public void onWebSocketError(Throwable cause) {
		System.out.println("WebSocket Error :" + cause);
	}

	@OnMessage
	public String onWebSocketText(String message) {
		System.out.println(String.format("Echoing back text message [%s]", message));
		// Using shortcut approach to sending messages.
		// You could use a void method and use remote.sendText()
		return message;
	}

}
