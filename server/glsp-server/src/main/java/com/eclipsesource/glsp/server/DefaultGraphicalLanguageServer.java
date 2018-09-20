/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.action.ActionRegistry;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageClient;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageServer;
import com.eclipsesource.glsp.api.model.ModelState;

import io.typefox.sprotty.api.ServerStatus;

public class DefaultGraphicalLanguageServer implements GraphicalLanguageServer {

	static Logger log = Logger.getLogger(DefaultGraphicalLanguageServer.class);

	private ServerStatus status;
	private String clientId;
	private ActionRegistry actionRegistry;
	private ModelState modelState;

	private GraphicalLanguageClient clientProxy;

	@Inject
	public DefaultGraphicalLanguageServer(ActionRegistry actionRegistry, ModelState modelState) {
		this.actionRegistry = actionRegistry;
		this.modelState = modelState;
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
		if (this.clientId == null) {
			clientId = message.getClientId();
		}
		if (clientId.equals(message.getClientId())) {
			Action requestAction = message.getAction();
			if (actionRegistry.hasHandler(requestAction)) {
				Optional<Action> responseOpt = actionRegistry.delegateToHandler(requestAction, this);
				if (responseOpt.isPresent()) {
					ActionMessage response = new ActionMessage(clientId, responseOpt.get());
					clientProxy.process(response);
				}
			} else {
				log.warn("No action handler registered for action kind: \"" + message.getAction().getKind() + "\"");
			}
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
	public ModelState getModelState() {
		return modelState;
	}

}
