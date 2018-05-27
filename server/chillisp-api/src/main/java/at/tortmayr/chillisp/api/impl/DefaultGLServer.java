package at.tortmayr.chillisp.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.ActionRegistry;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IGraphicalModelState;
import at.tortmayr.chillisp.api.IRequestActionHandler;
import at.tortmayr.chillisp.api.actions.Action;
import at.tortmayr.chillisp.api.actions.ServerStatusAction;
import io.typefox.sprotty.api.Dimension;
import io.typefox.sprotty.api.ILayoutEngine;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;
import io.typefox.sprotty.api.ServerStatus;

public class DefaultGLServer implements IGraphicalLanguageServer {

	private String clientId;

	private Consumer<ActionMessage> remoteEndpoint;
	private IRequestActionHandler actionHandler;
	private IGraphicalModelState modelState;
	private ServerStatus status;

	public DefaultGLServer() {
		modelState = new ModelState();
		setRequestActionHandler(new RequestActionHandler(this));
	}

	public DefaultGLServer(String clientId) {
		this();
		this.clientId = clientId;
	}

	@Override
	public void accept(ActionMessage message) {
		String clientId = getClientId();
		if (clientId != null && clientId.equals(message.getClientId())) {
			Action action = message.getAction();
			Consumer<Action> actionConsumer = ActionRegistry.getActionConsumer(action.getKind());
			if (actionConsumer != null) {
				actionConsumer.accept(action);
			}
		}
	}

	@Override
	public void dispatch(Action action) {
		Consumer<ActionMessage> remoteEndpoint = getRemoteEndpoint();
		if (remoteEndpoint != null) {
			remoteEndpoint.accept(new ActionMessage(getClientId(), action));
		}
	}

	@Override
	public Consumer<ActionMessage> getRemoteEndpoint() {
		return remoteEndpoint;
	}

	@Override
	public void setRemoteEndpoint(Consumer<ActionMessage> remoteEndpoint) {
		this.remoteEndpoint = remoteEndpoint;

	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public void setRequestActionHandler(IRequestActionHandler actionHandler) {
		this.actionHandler = actionHandler;
		ActionRegistry.bindActionHandler(actionHandler);

	}

	@Override
	public IRequestActionHandler getRequestActionHandler() {
		return actionHandler;
	}

	@Override
	public void setStatus(ServerStatus serverStatus) {
		this.status = serverStatus;
		dispatch(new ServerStatusAction(serverStatus));
	}

	public ServerStatus getStatus() {
		return status;
	}

	@Override
	public ILayoutEngine getLayoutEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGraphicalModelState getModelState() {
		return modelState;
	}

	@Override
	public SModelRoot loadModel() {
		SModelRoot currentRoot = new SModelRoot();
		currentRoot.setType("graph");
		currentRoot.setId("sprotty");
		SNode node = new SNode();
		node.setId("first");
		node.setType("node");
		node.setLayout("vbox");
		node.setPosition(new Point(100, 100));
		node.setSize(new Dimension(25, 25));
		List<SModelElement> children = new ArrayList<>();
		children.add(node);
		currentRoot.setChildren(children);
		return currentRoot;
	}

}
