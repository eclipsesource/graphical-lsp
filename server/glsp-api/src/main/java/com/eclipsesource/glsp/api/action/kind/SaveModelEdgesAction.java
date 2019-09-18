package com.eclipsesource.glsp.api.action.kind;

import java.util.List;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.model.ElementAndRoutingPoints;

public class SaveModelEdgesAction extends Action{
	
	private List<ElementAndRoutingPoints> newRoutingPoints;

	public SaveModelEdgesAction() {
		super(Action.Kind.SAVE_MODEL_EDGES);
	}
	
	public SaveModelEdgesAction(List<ElementAndRoutingPoints> elementAndRoutingPoints) {
		this();
		this.newRoutingPoints = elementAndRoutingPoints;
	}

	public List<ElementAndRoutingPoints> getNewRoutingPoints() {
		return newRoutingPoints;
	}

	public void setNewRoutingPoints(List<ElementAndRoutingPoints> newRoutingPoints) {
		this.newRoutingPoints = newRoutingPoints;
	}

	@Override
	public String toString() {
		return "SaveModelEdgesAction [newRoutingPoints=" + newRoutingPoints + "]";
	}
	
	
	
	

}
