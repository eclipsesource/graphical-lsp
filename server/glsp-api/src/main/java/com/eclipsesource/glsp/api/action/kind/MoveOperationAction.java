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

import io.typefox.sprotty.api.Point;

public class MoveOperationAction extends ExecuteOperationAction {

	private String elementId;
	private String targetContainerId;
	private Point location;

	public MoveOperationAction() {
		super(ActionKind.MOVE_OPERATION);
	}

	public MoveOperationAction(String elementId, Point location) {
		this();
		this.elementId = elementId;
		this.location = location;
	}

	public MoveOperationAction(String elementId, Point location, String targetContainerId) {
		this(elementId, location);
		this.targetContainerId = targetContainerId;
	}

	public MoveOperationAction(String elementId, String targetContainerId) {
		this(elementId, (Point) null, targetContainerId);
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getTargetContainerId() {
		return targetContainerId;
	}

	public void setTargetContainerId(String targetContainerId) {
		this.targetContainerId = targetContainerId;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((targetContainerId == null) ? 0 : targetContainerId.hashCode());
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
		MoveOperationAction other = (MoveOperationAction) obj;
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
		if (targetContainerId == null) {
			if (other.targetContainerId != null)
				return false;
		} else if (!targetContainerId.equals(other.targetContainerId))
			return false;
		return true;
	}

}
