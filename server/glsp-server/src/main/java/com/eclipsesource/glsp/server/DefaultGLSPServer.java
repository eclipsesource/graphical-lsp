/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.server;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.ServerStatus;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.action.ActionRegistry;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.server.model.ModelStateImpl;

public class DefaultGLSPServer implements GLSPServer {

	static Logger log = Logger.getLogger(DefaultGLSPServer.class);

	private ServerStatus status;
	private ActionRegistry actionRegistry;
	private GLSPClient clientProxy;
	private Map<String, ModelState> clientModelStates;

	@Inject
	public DefaultGLSPServer(ActionRegistry actionRegistry) {
		this.actionRegistry = actionRegistry;
		clientModelStates = new ConcurrentHashMap<>();
	}

	@Override
	public void initialize() {
	}

	@Override
	public void connect(GLSPClient clientProxy) {
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
			modelState.setClientId(clientId);
			clientModelStates.put(clientId, modelState);
		}
		return modelState;
	}

}
