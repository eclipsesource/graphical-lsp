/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.chillisp.api.impl;

import java.util.HashMap;
import java.util.function.Consumer;

import javax.inject.Inject;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.ActionRegistry.Kind;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IActionHandler;
import at.tortmayr.chillisp.api.actions.Action;
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
import at.tortmayr.chillisp.api.actions.RequestBoundsChangeHintsAction;
import at.tortmayr.chillisp.api.actions.RequestExportSvgAction;
import at.tortmayr.chillisp.api.actions.RequestLayersAction;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.actions.RequestMoveHintsAction;
import at.tortmayr.chillisp.api.actions.RequestPopupModelAction;
import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.actions.SaveModelAction;
import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;
import at.tortmayr.chillisp.api.actions.ServerStatusAction;
import at.tortmayr.chillisp.api.actions.SetBoundsAction;
import at.tortmayr.chillisp.api.actions.ToogleLayerAction;
import io.typefox.sprotty.api.ServerStatus;

public class DefaultGraphicalLanguageServer implements IGraphicalLanguageServer {

	private HashMap<String, Consumer<Action>> actionConsumers;

	private IActionHandler actionHandler;

	private String clientId;
	private ServerStatus status;
	private Consumer<ActionMessage> remoteEndpoint;

	@Inject
	public DefaultGraphicalLanguageServer(IActionHandler actionHandler) {
		this.actionHandler = actionHandler;
		actionHandler.setLanguageServer(this);
		initialize();
	}

	@Override
	public void initialize() {
		actionConsumers = new HashMap<String, Consumer<Action>>() {
			private static final long serialVersionUID = 1L;
			{
				put(Kind.REQUEST_MODEL, (Action a) -> actionHandler.handle((RequestModelAction) a));
				put(Kind.CENTER, (Action a) -> actionHandler.handle((CenterAction) a));
				put(Kind.COLLAPSE_EXPAND, (Action a) -> actionHandler.handle((CollapseExpandAction) a));
				put(Kind.COLLAPSE_EXPAND_ALL, (Action a) -> actionHandler.handle((CollapseExpandAllAction) a));
				put(Kind.COMPUTED_BOUNDS, (Action a) -> actionHandler.handle((ComputedBoundsAction) a));
				put(Kind.EXECUTE_NODE_CREATION_TOOL,
						(Action a) -> actionHandler.handle((ExecuteNodeCreationToolAction) a));
				put(Kind.COMPUTED_BOUNDS, (Action a) -> actionHandler.handle((ExecuteToolAction) a));
				put(Kind.REQUEST_BOUNDS_CHANGE_HINTS,
						(Action a) -> actionHandler.handle((RequestBoundsChangeHintsAction) a));
				put(Kind.CHANGE_BOUNDS, (Action a) -> actionHandler.handle((ChangeBoundsAction) a));
				put(Kind.REQUEST_MOVE_HINTS, (Action a) -> actionHandler.handle((RequestMoveHintsAction) a));
				put(Kind.MOVE, (Action a) -> actionHandler.handle((MoveAction) a));
				put(Kind.FIT_TO_SCREEN, (Action a) -> actionHandler.handle((FitToScreenAction) a));
				put(Kind.OPEN, (Action a) -> actionHandler.handle((OpenAction) a));
				put(Kind.REQUEST_EXPORT_SVG, (Action a) -> actionHandler.handle((RequestExportSvgAction) a));
				put(Kind.REQUEST_LAYERS, (Action a) -> actionHandler.handle((RequestLayersAction) a));
				put(Kind.REQUEST_POPUP_MODEL, (Action a) -> actionHandler.handle((RequestPopupModelAction) a));
				put(Kind.REQUEST_TOOLS, (Action a) -> actionHandler.handle((RequestToolsAction) a));
				put(Kind.SELECT, (Action a) -> actionHandler.handle((SelectAction) a));
				put(Kind.SELECT_ALL, (Action a) -> actionHandler.handle((SelectAllAction) a));
				put(Kind.TOOGLE_LAYER, (Action a) -> actionHandler.handle((ToogleLayerAction) a));
				put(Kind.SET_BOUNDS, (Action a) -> actionHandler.handle((SetBoundsAction) a));
				put(Kind.SAVE_MODEL, (Action a) -> actionHandler.handle((SaveModelAction) a));
			}
		};

	}

	@Override
	public void accept(ActionMessage message) {
		String clientId = getClientId();
		if (clientId != null && clientId.equals(message.getClientId())) {
			Action action = message.getAction();
			Consumer<Action> actionConsumer = actionConsumers.get(action.getKind());
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
	public void setStatus(ServerStatus serverStatus) {
		this.status = serverStatus;
		dispatch(new ServerStatusAction(serverStatus));
	}

	public ServerStatus getStatus() {
		return status;
	}

}
