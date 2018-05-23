package at.tortmayr.chillisp.api.actions;

public class OpenAction extends Action {
	private String elementId;

	public OpenAction() {
		super(Action.Kind.OPEN);
	}

	public OpenAction(String elementId) {
		super(Action.Kind.OPEN);
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
