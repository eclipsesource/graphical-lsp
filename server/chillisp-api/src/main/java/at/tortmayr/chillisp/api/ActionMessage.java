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

import at.tortmayr.chillisp.api.actions.Action;

public class ActionMessage {

	private Action action;
	private String clientId;

	public ActionMessage(String clientId, Action action) {
		this.clientId = clientId;
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public String getClientId() {
		return clientId;
	}

	@Override
	public String toString() {
		return "ActionMessage [action=" + action.getKind() + ", clientId=" + clientId + "]";
	}
	
	

}
