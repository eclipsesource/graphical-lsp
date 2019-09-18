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
