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
package com.eclipsesource.glsp.server.jsonrpc;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.ServerStatus;
import org.eclipse.sprotty.ServerStatus.Severity;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.ActionDispatcher;
import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.action.kind.IdentifiableRequestAction;
import com.eclipsesource.glsp.api.action.kind.IdentifiableResponseAction;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.google.inject.Inject;

public class DefaultGLSPServer implements GLSPServer {

	@Inject
	private ModelStateProvider modelStateProvider;
	@Inject
	private ActionDispatcher actionDispatcher;
	static Logger log = Logger.getLogger(DefaultGLSPServer.class);

	private ServerStatus status;

	private GLSPClient clientProxy;

	public DefaultGLSPServer() {
	}

	@Override
	public void initialize() {
	}

	@Override
	public void connect(GLSPClient clientProxy) {
		this.clientProxy = clientProxy;
		status = new ServerStatus(Severity.OK, "Connection successfull");
	}

	@Override
	public void process(ActionMessage message) {
		log.debug("process " + message);
		String clientId = message.getClientId();

		Action requestAction = message.getAction();
		Optional<String> requestId = Optional.empty();
		if (requestAction instanceof IdentifiableRequestAction) {
			// unwrap identifiable request
			requestId = Optional.of(((IdentifiableRequestAction) requestAction).getId());
			requestAction = ((IdentifiableRequestAction) requestAction).getAction();
		}

		Optional<Action> responseOpt = actionDispatcher.dispatch(clientId, requestAction);

		if (responseOpt.isPresent()) {
			// wrap identifiable response if necessary
			Action response = requestId.<Action>map(id -> new IdentifiableResponseAction(id, responseOpt.get()))
					.orElse(responseOpt.get());
			ActionMessage responseMessage = new ActionMessage(clientId, response);
			clientProxy.process(responseMessage);
		}
	}

	public ServerStatus getStatus() {
		return status;
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		return new CompletableFuture<Object>();
	}

	@Override
	public void exit(String clientId) {
		modelStateProvider.remove(clientId);
	}
}
