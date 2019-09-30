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
package com.eclipsesource.glsp.api.model;

import java.util.List;

import com.eclipsesource.glsp.graph.GPoint;

public class ElementAndRoutingPoints {
	
	private String elementId;
	private List<GPoint> routingPoints;
	
	public String getElementId() {
		return elementId;	
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public List<GPoint> getRoutingPoints() {
		return routingPoints;
	}
	public void setRoutingPoints(List<GPoint> routingPoints) {
		this.routingPoints = routingPoints;
	}
	@Override
	public String toString() {
		return "ElementAndRoutingPoints [elementId=" + elementId + ", routingPoints=" + routingPoints + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
		result = prime * result + ((routingPoints == null) ? 0 : routingPoints.hashCode());
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
		ElementAndRoutingPoints other = (ElementAndRoutingPoints) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		if (routingPoints == null) {
			if (other.routingPoints != null)
				return false;
		} else if (!routingPoints.equals(other.routingPoints))
			return false;
		return true;
	}
}
