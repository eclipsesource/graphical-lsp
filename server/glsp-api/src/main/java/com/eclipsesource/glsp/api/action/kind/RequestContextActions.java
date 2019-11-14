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
package com.eclipsesource.glsp.api.action.kind;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.RequestAction;
import com.eclipsesource.glsp.graph.GPoint;

public class RequestContextActions  extends RequestAction<SetContextActions> {

	private List<String> selectedElementIds;
	private Map<String, String> args;
	private GPoint lastMousePosition = null;

	public RequestContextActions() {
		super(Action.Kind.REQUEST_CONTEXT_ACTIONS);
	}
	
	public RequestContextActions(List<String> selectedElementIds, Map<String, String> args) {
		this(selectedElementIds, null, args);
	}

	public RequestContextActions(List<String> selectedElementIds, GPoint lastMousePosition, Map<String, String> args) {
		this();
		this.selectedElementIds = selectedElementIds;
		this.args = args;
		this.lastMousePosition = lastMousePosition;
	}

	public List<String> getSelectedElementIds() {
		return selectedElementIds;
	}

	public void setSelectedElementsIds(List<String> selectedElementsIDs) {
		this.selectedElementIds = selectedElementsIDs;
	}
	
	public Map<String, String> getArgs() {
		return args;
	}

	public void setArgs(Map<String, String> args) {
		this.args = args;
	}

	public Optional<GPoint> getLastMousePosition() {
		return Optional.ofNullable(lastMousePosition);
	}
	
	public void setLastMousePosition(GPoint lastMousePosition) {
		this.lastMousePosition = lastMousePosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		result = prime * result + ((lastMousePosition == null) ? 0 : lastMousePosition.hashCode());
		result = prime * result + ((selectedElementIds == null) ? 0 : selectedElementIds.hashCode());
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
		RequestContextActions other = (RequestContextActions) obj;
		if (args == null) {
			if (other.args != null)
				return false;
		} else if (!args.equals(other.args))
			return false;
		if (lastMousePosition == null) {
			if (other.lastMousePosition != null)
				return false;
		} else if (!lastMousePosition.equals(other.lastMousePosition))
			return false;
		if (selectedElementIds == null) {
			if (other.selectedElementIds != null)
				return false;
		} else if (!selectedElementIds.equals(other.selectedElementIds))
			return false;
		return true;
	}

}
