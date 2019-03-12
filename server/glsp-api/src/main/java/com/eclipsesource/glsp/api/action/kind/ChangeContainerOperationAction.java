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

public class ChangeContainerOperationAction extends Action {

	private String elementId;
	private String targetContainerId;
	private Point location;

	public ChangeContainerOperationAction() {
		super(Action.Kind.CHANGE_CONTAINER_OPERATION);
	}

	public ChangeContainerOperationAction(String elementId, Point location) {
		this();
		this.elementId = elementId;
		this.location = location;
	}

	public ChangeContainerOperationAction(String elementId, Point location, String targetContainerId) {
		this(elementId, location);
		this.targetContainerId = targetContainerId;
	}

	public ChangeContainerOperationAction(String elementId, String targetContainerId) {
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
		ChangeContainerOperationAction other = (ChangeContainerOperationAction) obj;
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
