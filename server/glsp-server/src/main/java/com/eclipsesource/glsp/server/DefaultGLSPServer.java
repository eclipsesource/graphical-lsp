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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.sprotty.ServerStatus;
import org.eclipse.sprotty.ServerStatus.Severity;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.action.kind.IdentifiableRequestAction;
import com.eclipsesource.glsp.api.action.kind.IdentifiableResponseAction;
import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.action.kind.ServerStatusAction;
import com.eclipsesource.glsp.api.diagram.DiagramManager;
import com.eclipsesource.glsp.api.diagram.DiagramHandlerProvider;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.eclipsesource.glsp.api.utils.ClientOptions;
import com.eclipsesource.glsp.api.utils.ClientOptions.ParsedClientOptions;

public class DefaultGLSPServer implements GLSPServer {

	@Inject
	private DiagramHandlerProvider diagramHandlerProvider;
	@Inject
	private ModelStateProvider modelStateProvider;
	static Logger log = Logger.getLogger(DefaultGLSPServer.class);

	private ServerStatus status;

	private GLSPClient clientProxy;

	private Map<String, DiagramManager> clientIdtoDiagramServer;

	public DefaultGLSPServer() {
		clientIdtoDiagramServer = new HashMap<>();
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

		if (requestAction instanceof RequestModelAction) {
			ParsedClientOptions options = ClientOptions.parse(((RequestModelAction) requestAction).getOptions());
			Optional<String> diagramType = options.getDiagramType();
			Optional<DiagramManager> diagramServer = getOrCreateDiagramServer(clientId, diagramType);
			if (!diagramServer.isPresent()) {
				ServerStatus status = new ServerStatus(Severity.ERROR,
						String.format("Could not retrieve diagram server of type '%s' for client '%s'",
								diagramType.orElse("undefined"), clientId));
				clientProxy.process(new ActionMessage(clientId, new ServerStatusAction(status)));
				return;
			} else {
				clientIdtoDiagramServer.put(clientId, diagramServer.get());
			}

		}

		DiagramManager diagramServer = clientIdtoDiagramServer.get(clientId);
		Optional<Action> responseOpt = diagramServer.execute(clientId, requestAction);

		if (responseOpt.isPresent()) {
			// wrap identifiable response if necessary
			Action response = requestId.<Action>map(id -> new IdentifiableResponseAction(id, responseOpt.get()))
					.orElse(responseOpt.get());
			ActionMessage responseMessage = new ActionMessage(clientId, response);
			clientProxy.process(responseMessage);
		}
	}

	private Optional<DiagramManager> getOrCreateDiagramServer(String clientId, Optional<String> diagramType) {
		if (diagramType.isPresent()) {
			Optional<DiagramManager> existingServer = getDiagramServer(clientId, diagramType.get());
			return existingServer.isPresent() ? existingServer : diagramHandlerProvider.get(diagramType.get());
		} else {
			if (clientIdtoDiagramServer.get(clientId) != null) {
				return Optional.of(clientIdtoDiagramServer.get(clientId));
			} else {
				return diagramHandlerProvider.createDefault();
			}
		}
	}

	private Optional<DiagramManager> getDiagramServer(String clientId, String diagramType) {
		DiagramManager server = clientIdtoDiagramServer.get(clientId);
		return server != null && server.getDiagramType().equals(diagramType) ? Optional.of(server) : Optional.empty();
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
