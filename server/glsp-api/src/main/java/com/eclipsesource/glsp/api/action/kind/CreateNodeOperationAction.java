/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.action.kind;

import com.eclipsesource.glsp.api.operations.OperationKind;

import io.typefox.sprotty.api.Point;

public class CreateNodeOperationAction extends ExecuteOperationAction {
	
	private String elementId;
	
	private Point location;
	
	private String containerId;
	
	public CreateNodeOperationAction() {
		super(ActionKind.CREATE_NODE_OPERATION);
	}
	
	public CreateNodeOperationAction(String elementId) {
		this();
		this.elementId = elementId;
	}

	public CreateNodeOperationAction(String elementId, Point location) {
		this(elementId);
		this.location = location;
	}

	public CreateNodeOperationAction(String elementId, Point location, String containerId) {
		this(elementId, location);
		this.containerId = containerId;
	}
	
	public CreateNodeOperationAction(String elementId, String containerId) {
		this(elementId);
		this.containerId = containerId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
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
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
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
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

}
