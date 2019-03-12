package com.eclipsesource.glsp.api.action.kind;

import com.eclipsesource.glsp.api.action.Action;

/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
public class ReconnectOperationAction extends Action{

	private String edgeId;
	private String newEdgeEndId;
	private boolean source;

	public ReconnectOperationAction() {
		super(Action.Kind.RECONNECT_OPERATION_ACTION); 
	};

	public String getEdgeId() {
		return edgeId;
	}

	public void setEdgeId(String edgeId) {
		this.edgeId = edgeId;
	}

	public String getNewEdgeEndId() {
		return newEdgeEndId;
	}

	public void setNewEdgeEndId(String newEdgeEndId) {
		this.newEdgeEndId = newEdgeEndId;
	}

	public boolean isSource() {
		return source;
	}

	public void setSource(boolean source) {
		this.source = source;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edgeId == null) ? 0 : edgeId.hashCode());
		result = prime * result + ((newEdgeEndId == null) ? 0 : newEdgeEndId.hashCode());
		result = prime * result + (source ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReconnectOperationAction other = (ReconnectOperationAction) obj;
		if (edgeId == null) {
			if (other.edgeId != null)
				return false;
		} else if (!edgeId.equals(other.edgeId))
			return false;
		if (newEdgeEndId == null) {
			if (other.newEdgeEndId != null)
				return false;
		} else if (!newEdgeEndId.equals(other.newEdgeEndId))
			return false;
		if (source != other.source)
			return false;
		return true;
	}

}
