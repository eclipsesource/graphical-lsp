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
package at.tortmayr.chillisp.api;

import java.util.function.Consumer;

import at.tortmayr.chillisp.api.actions.Action;
import io.typefox.sprotty.api.ILayoutEngine;
import io.typefox.sprotty.api.ServerStatus;

public interface IGraphicalLanguageServer extends Consumer<ActionMessage> {

	void initialize();

	Consumer<ActionMessage> getRemoteEndpoint();

	void setRemoteEndpoint(Consumer<ActionMessage> consumer);

	void setClientId(String clientId);

	String getClientId();

	void setStatus(ServerStatus serverStatus);

	ILayoutEngine getLayoutEngine();

	void dispatch(Action action);

	IModelFactory getModelFactory();

	IPopupModelFactory getPopupModelFactory();

	public interface Provider {
		IGraphicalLanguageServer getGraphicalLanguageServer(String clientId);

	}

}
