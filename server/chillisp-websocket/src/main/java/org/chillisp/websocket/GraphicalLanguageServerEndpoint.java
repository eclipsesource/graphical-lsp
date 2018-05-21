package org.chillisp.websocket;

import java.util.function.Consumer;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import chillisp.websocket.json.ActionTypeAdapter;


public class GraphicalLanguageServerEndpoint extends Endpoint implements Consumer<ActionMessage> {
	private Session session;
	private Consumer<Exception> exceptionHandler;

	private IGraphicalLanguageServer.Provider glServerProvider;
	private Gson gson;

	public GraphicalLanguageServerEndpoint() {
		initializeGson();
	}

	protected void initializeGson() {
		if (gson == null) {
			GsonBuilder builder = new GsonBuilder();
			ActionTypeAdapter.configureGson(builder);
			gson = builder.create();
			assert (gson != null);
		}
	}

	protected Session getSession() {
		return session;
	}

	public void setGson(Gson gson) {
		assert (gson != null);
		this.gson = gson;
	}

	public void setGraphicalLanguageServerProvider(IGraphicalLanguageServer.Provider glServerProvider) {
		this.glServerProvider = glServerProvider;
	}

	public void setExceptionHandler(Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	protected void fireError(Exception message) {
		exceptionHandler.accept(message);
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		session.addMessageHandler(new ActionMessageHandler());

	}

	@Override
	public void accept(ActionMessage message) {
		String json = gson.toJson(message, ActionMessage.class);
		session.getAsyncRemote().sendText(json);

	}

	protected void fireMessageReceived(ActionMessage message) {
		IGraphicalLanguageServer glServer = glServerProvider.getGraphicalLanguageServer(message.getClientId());
		if (glServer != null) {
			if (!this.equals(glServer.getRemoteEndpoint())) {
				glServer.setRemoteEndpoint(this);
			}
			glServer.accept(message);
		}
	}

	class ActionMessageHandler implements MessageHandler.Whole<String> {

		@Override
		public void onMessage(String message) {
			try {
				ActionMessage actionMessage = gson.fromJson(message, ActionMessage.class);
				if (actionMessage.getAction() == null) {
					throw new IllegalArgumentException("Property 'action' must be set.");
				} else {
					fireMessageReceived(actionMessage);
				}
			} catch (JsonSyntaxException | IllegalArgumentException ex) {
				fireError(ex);
			}

		}

	}

}
