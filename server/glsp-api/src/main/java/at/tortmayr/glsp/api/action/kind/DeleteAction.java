package at.tortmayr.glsp.api.action.kind;

import at.tortmayr.glsp.api.action.Action;

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
