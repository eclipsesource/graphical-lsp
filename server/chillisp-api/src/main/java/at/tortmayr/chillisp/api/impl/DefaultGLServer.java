package at.tortmayr.chillisp.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.ActionRegistry;
import at.tortmayr.chillisp.api.IAction;
import at.tortmayr.chillisp.api.IActionHandler;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.actions.CenterAction;
import at.tortmayr.chillisp.api.actions.ChangeBoundsAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAllAction;
import at.tortmayr.chillisp.api.actions.ComputedBoundsAction;
import at.tortmayr.chillisp.api.actions.ExecuteNodeCreationToolAction;
import at.tortmayr.chillisp.api.actions.ExecuteToolAction;
import at.tortmayr.chillisp.api.actions.FitToScreenAction;
import at.tortmayr.chillisp.api.actions.MoveAction;
import at.tortmayr.chillisp.api.actions.OpenAction;
import at.tortmayr.chillisp.api.actions.RequestBoundsAction;
import at.tortmayr.chillisp.api.actions.RequestBoundsChangeHintsAction;
import at.tortmayr.chillisp.api.actions.RequestExportSvgAction;
import at.tortmayr.chillisp.api.actions.RequestLayersAction;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.actions.RequestMoveHintsAction;
import at.tortmayr.chillisp.api.actions.RequestPopupModelAction;
import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;
import at.tortmayr.chillisp.api.actions.ServerStatusAction;
import at.tortmayr.chillisp.api.actions.SetModelAction;
import at.tortmayr.chillisp.api.actions.ToogleLayerAction;
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
			Consumer<IAction> actionConsumer = ActionRegistry.getActionConsumer(action.getKind());
			if (actionConsumer != null) {
				actionConsumer.accept(action);
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
		ActionRegistry.bindActionHandler(actionHandler);

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

		@Override
		public void handle(CenterAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(CollapseExpandAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(CollapseExpandAllAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(ComputedBoundsAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(ExecuteNodeCreationToolAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(ExecuteToolAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(RequestBoundsChangeHintsAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(ChangeBoundsAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(RequestMoveHintsAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(MoveAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(FitToScreenAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(OpenAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(RequestBoundsAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(RequestExportSvgAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(RequestLayersAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(RequestPopupModelAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(RequestToolsAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(SelectAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(SelectAllAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(ServerStatusAction action) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handle(ToogleLayerAction action) {
			// TODO Auto-generated method stub

		}

	}

}
