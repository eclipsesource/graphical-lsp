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
package at.tortmayr.glsp.api;

import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;

import io.typefox.sprotty.api.ServerStatus;

public interface GraphicalLanguageServer extends GraphicalLanguageClientAware {

	public interface Provider {
		GraphicalLanguageServer getGraphicalLanguageServer(String clientId);
	}

	void initialize();

	@JsonNotification("process")
	void process(ActionMessage message);

	void setStatus(ServerStatus serverStatus);
}
