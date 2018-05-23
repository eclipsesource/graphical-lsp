package at.tortmayr.chillisp.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.IAction;
import at.tortmayr.chillisp.api.IActionHandler;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.actions.Action;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.actions.SetModelAction;
import io.typefox.sprotty.api.Dimension;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

public class DefaultGLServer implements IGraphicalLanguageServer {

	private String clientId;
	private SModelRoot currentRoot;
	private Consumer<ActionMessage> remoteEndpoint;
	private IActionHandler actionHandler;
	private int revision;
	private Object modelLock;

	public DefaultGLServer() {

		currentRoot = new SModelRoot();
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
		setActionHandler(new DefaultActionHandler());
	}

	public DefaultGLServer(String clientId) {
		this();
		this.clientId = clientId;
	}

	@Override
	public void accept(ActionMessage message) {
		String clientId = getClientId();
		if (clientId != null && clientId.equals(message.getClientId())) {
			IAction action = message.getAction();
			switch (action.getKind()) {
			case Action.Kind.REQUEST_MODEL:
				actionHandler.handle((RequestModelAction) action);
			}

		}
	}

	@Override
	public void dispatch(IAction action) {
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
	public SModelRoot getModel() {
		return currentRoot;
	}

	@Override
	public void setModel(SModelRoot newRoot) {
		assert (newRoot != null);
		synchronized (modelLock) {
			newRoot.setRevision(++revision);
			currentRoot = newRoot;
		}
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
	public void setActionHandler(IActionHandler actionHandler) {
		this.actionHandler = actionHandler;

	}

	@Override
	public IActionHandler getActionHandler() {
		return actionHandler;
	}

	class DefaultActionHandler implements IActionHandler {

		@Override
		public void handle(RequestModelAction request) {
			SModelRoot model = getModel();
			if (model != null) {
				dispatch(new SetModelAction(model));
			}
		}

	}

}
