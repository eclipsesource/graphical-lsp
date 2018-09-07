package com.eclipsesource.glsp.api.action.kind;

import com.eclipsesource.glsp.api.action.Action;

public class DeleteAction extends Action {
	
	private String elementId;
	
	public DeleteAction() {
		super(ActionKind.DELETE);
	}

	public DeleteAction(String sourceElement) {
		this();
		this.elementId = sourceElement;
	}

	public String getElementId() {
		return elementId;
	}
	
}
