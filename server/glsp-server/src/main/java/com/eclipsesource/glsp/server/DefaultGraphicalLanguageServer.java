/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.action.ActionRegistry;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageClient;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageServer;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.server.model.ModelStateImpl;

import io.typefox.sprotty.api.ServerStatus;

public class DefaultGraphicalLanguageServer implements GraphicalLanguageServer {

	static Logger log = Logger.getLogger(DefaultGraphicalLanguageServer.class);

	private ServerStatus status;
	private ActionRegistry actionRegistry;
	private GraphicalLanguageClient clientProxy;
	private Map<String, ModelState> clientModelStates;

	@Inject
	public DefaultGraphicalLanguageServer(ActionRegistry actionRegistry) {
		this.actionRegistry = actionRegistry;
		clientModelStates = new ConcurrentHashMap<>();
	}

	@Override
	public void initialize() {
	}

	@Override
	public void connect(GraphicalLanguageClient clientProxy) {
		this.clientProxy = clientProxy;
	}

	@Override
	public void process(ActionMessage message) {
		log.debug("process " + message);
		String clientId = message.getClientId();
		ModelState modelState = getModelState(clientId);

		Action requestAction = message.getAction();
		if (actionRegistry.hasHandler(requestAction)) {
			Optional<Action> responseOpt = actionRegistry.delegateToHandler(requestAction, modelState);
			if (responseOpt.isPresent()) {
				ActionMessage response = new ActionMessage(clientId, responseOpt.get());
				clientProxy.process(response);
			}
		} else {
			log.warn("No action handler registered for action kind: \"" + message.getAction().getKind() + "\"");
		}

	}

	@Override
	public void setStatus(ServerStatus status) {
		this.status = status;

	}

	@Override
	public CompletableFuture<Object> shutdown() {
		return new CompletableFuture<Object>();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized ModelState getModelState(String clientId) {
		ModelState modelState = clientModelStates.get(clientId);
		if (modelState == null) {
			modelState = new ModelStateImpl();
			clientModelStates.put(clientId, modelState);
		}
		return modelState;
	}

}
