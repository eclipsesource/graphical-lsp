package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.ActionRegistry;

public class OpenAction extends Action {
	private String elementId;

	public OpenAction() {
		super(ActionRegistry.Kind.OPEN);
	}

	public OpenAction(String elementId) {
		super(ActionRegistry.Kind.OPEN);
		this.elementId = elementId;
	}

	public String getElementId() {
		return elementId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
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
		OpenAction other = (OpenAction) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		return true;
	}
}
