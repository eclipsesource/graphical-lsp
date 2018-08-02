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
package at.tortmayr.glsp.server;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import at.tortmayr.glsp.api.ActionHandler;
import at.tortmayr.glsp.api.ActionMessage;
import at.tortmayr.glsp.api.ActionRegistry;
import at.tortmayr.glsp.api.GraphicalLanguageClient;
import at.tortmayr.glsp.api.GraphicalLanguageServer;
import io.typefox.sprotty.api.ServerStatus;

public class DefaultGraphicalLanguageServer implements GraphicalLanguageServer {
	static Logger log = Logger.getLogger(DefaultGraphicalLanguageServer.class);

	private ServerStatus status;
	private ActionHandler actionHandler;

	private String clientId;

	@Inject
	public DefaultGraphicalLanguageServer(ActionHandler actionHandler) {
		try {
			this.actionHandler = actionHandler;
			ActionRegistry.getInstance().initialize(actionHandler);
		} catch (InstantiationException | IllegalAccessException e) {
			log.warn("Error during registration of the action handler. Some action messages might not be processable");
			e.printStackTrace();
		}

	}

	@Override
	public void initialize() {
	}

	@Override
	public void connect(GraphicalLanguageClient client) {
		actionHandler.setGraphicalLanguageClient(client);
	}

	@Override
	public void process(ActionMessage message) {
		if (this.clientId == null) {
			clientId = message.getClientId();
			actionHandler.setClientId(clientId);
		}
		if ( clientId.equals(message.getClientId())) {
			ActionRegistry.getInstance().handleAction(message.getAction());
		}

	}

	@Override
	public void setStatus(ServerStatus status) {
		this.status = status;

	}

}
