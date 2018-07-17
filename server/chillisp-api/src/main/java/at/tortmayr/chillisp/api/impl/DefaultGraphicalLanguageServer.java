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

import java.util.function.Consumer;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.ActionRegistry;
import at.tortmayr.chillisp.api.IActionHandler;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.actions.Action;
import at.tortmayr.chillisp.api.actions.ServerStatusAction;
import io.typefox.sprotty.api.ServerStatus;

public class DefaultGraphicalLanguageServer implements IGraphicalLanguageServer {
	static Logger log = Logger.getLogger(DefaultGraphicalLanguageServer.class);

	private String clientId;
	private ServerStatus status;
	private Consumer<ActionMessage> remoteEndpoint;

	@Inject
	public DefaultGraphicalLanguageServer(IActionHandler actionHandler) {
		try {
			ActionRegistry.getInstance().initialize(actionHandler);
		} catch (InstantiationException | IllegalAccessException e) {
			log.warn("Error during registration of the action handler. Some action messages might not be processable");
			e.printStackTrace();
		}
		actionHandler.setLanguageServer(this);

	}

	@Override
	public void initialize() {
	}

	@Override
	public void accept(ActionMessage message) {
		String clientId = getClientId();
		if (clientId != null && clientId.equals(message.getClientId())) {
			ActionRegistry.getInstance().handleAction(message.getAction());
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
