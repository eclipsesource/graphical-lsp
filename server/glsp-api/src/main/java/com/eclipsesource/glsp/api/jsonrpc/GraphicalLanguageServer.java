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
package com.eclipsesource.glsp.api.jsonrpc;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.model.ModelStateProvider;

import io.typefox.sprotty.api.ServerStatus;

public interface GraphicalLanguageServer extends GraphicalLanguageClientAware,ModelStateProvider {

	public interface Provider {
		GraphicalLanguageServer getGraphicalLanguageServer(String clientId);
	}

	void initialize();

	@JsonNotification("process")
	void process(ActionMessage message);
	
	@JsonRequest("shutdown")
	CompletableFuture<Object> shutdown();
	
	@JsonNotification("exit")
	void exit();
	
	void setStatus(ServerStatus serverStatus);
}
