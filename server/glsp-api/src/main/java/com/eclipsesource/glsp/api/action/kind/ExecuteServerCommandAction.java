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
package com.eclipsesource.glsp.api.action.kind;

import java.util.HashMap;
import java.util.Map;

import com.eclipsesource.glsp.api.action.Action;

public class ExecuteServerCommandAction extends Action {

	private String commandId;
	private Map<String, String> options;

	public ExecuteServerCommandAction() {
		super(ActionKind.EXECUTE_SERVER_COMMAND);
		options= new HashMap<>();
	}

	public ExecuteServerCommandAction(String commandId, Map<String, String> options) {
		this();
		this.commandId = commandId;
		this.options = options;
	}

	public String getCommandId() {
		return commandId;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((commandId == null) ? 0 : commandId.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExecuteServerCommandAction other = (ExecuteServerCommandAction) obj;
		if (commandId == null) {
			if (other.commandId != null)
				return false;
		} else if (!commandId.equals(other.commandId))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		return true;
	}

}
