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

import org.eclipse.sprotty.Point;

import com.eclipsesource.glsp.api.action.Action;

public class CreateNodeOperationAction extends Action {
	
	private String elementTypeId;

	private Point location;

	private String containerId;

	public CreateNodeOperationAction() {
		super(Action.Kind.CREATE_NODE_OPERATION);
	}

	public CreateNodeOperationAction(String elementTypeId) {
		this();
		this.elementTypeId = elementTypeId;
	}

	public CreateNodeOperationAction(String elementTypeId, Point location) {
		this(elementTypeId);
		this.location = location;
	}

	public CreateNodeOperationAction(String elementTypeId, Point location, String containerId) {
		this(elementTypeId, location);
		this.containerId = containerId;
	}

	public CreateNodeOperationAction(String elementTypeId, String containerId) {
		this(elementTypeId);
		this.containerId = containerId;
	}

	public String getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(String elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((containerId == null) ? 0 : containerId.hashCode());
		result = prime * result + ((elementTypeId == null) ? 0 : elementTypeId.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		CreateNodeOperationAction other = (CreateNodeOperationAction) obj;
		if (containerId == null) {
			if (other.containerId != null)
				return false;
		} else if (!containerId.equals(other.containerId))
			return false;
		if (elementTypeId == null) {
			if (other.elementTypeId != null)
				return false;
		} else if (!elementTypeId.equals(other.elementTypeId))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

}
