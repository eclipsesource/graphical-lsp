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
package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.ActionKind;
import io.typefox.sprotty.api.Point;

public class ExecuteToolAction extends Action {
	private String toolId;
	private Point location;
	private String elementId;

	public ExecuteToolAction() {
		super(ActionKind.EXECUTE_TOOL);
	}

	public ExecuteToolAction(String toolId, Point location, String elementId) {
		this();
		this.toolId = toolId;
		this.location = location;
		this.elementId = elementId;
	}

	public String getToolId() {
		return toolId;
	}

	public Point getLocation() {
		return location;
	}

	public String getElementId() {
		return elementId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((toolId == null) ? 0 : toolId.hashCode());
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
		ExecuteToolAction other = (ExecuteToolAction) obj;
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
		if (toolId == null) {
			if (other.toolId != null)
				return false;
		} else if (!toolId.equals(other.toolId))
			return false;
		return true;
	}

}
