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

import static com.eclipsesource.glsp.api.utils.ServerStatusUtil.error;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.ActionDispatcher;
import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.action.kind.IdentifiableRequestAction;
import com.eclipsesource.glsp.api.action.kind.IdentifiableResponseAction;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClientProvider;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.jsonrpc.InitializeParameters;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.eclipsesource.glsp.api.types.ServerStatus;
import com.eclipsesource.glsp.api.types.ServerStatus.Severity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.inject.Inject;

public class DefaultGLSPServer<T> implements GLSPServer {

	@Inject
	protected ModelStateProvider modelStateProvider;

	@Inject
	protected GLSPClientProvider clientProxyProvider;
	@Inject
	protected ActionDispatcher actionDispatcher;
	static Logger log = Logger.getLogger(DefaultGLSPServer.class);

	private ServerStatus status;

	private GLSPClient clientProxy;
	private Class<T> optionsClazz;

	public DefaultGLSPServer() {
		this(null);
	}

	public DefaultGLSPServer(Class<T> optionsClazz) {
		this.optionsClazz = optionsClazz;
	}

	@Override
	public CompletableFuture<Boolean> initialize(InitializeParameters params) {
		try {
			if (optionsClazz != null && params.getOptions() instanceof JsonElement) {
				T options = new Gson().fromJson((JsonElement) params.getOptions(), optionsClazz);
				return handleOptions(options);
			}
			return handleOptions(null);
		} catch (Throwable ex) {
			log.error("Could not initialize server due to corrupted options: " + params.getOptions(), ex);
			return CompletableFuture.completedFuture(false);
		}
	}

	protected CompletableFuture<Boolean> handleOptions(T options) {
		return CompletableFuture.completedFuture(true);
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
		try {
			// FIXME: It seems we don't get access to the clientId when the connection
			// is initialized. ClientId is only retrieved through messages; so this
			// is currently the earliest we can register the clientProxy
			this.clientProxyProvider.register(clientId, clientProxy);

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
				actionDispatcher.send(clientId, response);
			}
		} catch (RuntimeException e) {
			log.error(e);
			actionDispatcher.send(clientId, error(e));
		}
	}

	@Override
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
		clientProxyProvider.remove(clientId);
	}
}
