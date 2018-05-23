package at.tortmayr.chillisp.api.type;

public class BoundsChangeHint {

	private String elementId;
	private boolean resizable;
	private boolean repositionable;

	public BoundsChangeHint() {
	};

	public BoundsChangeHint(String elementId, boolean resizable, boolean repositionable) {
		super();
		this.elementId = elementId;
		this.resizable = resizable;
		this.repositionable = repositionable;
	}

	public String getElementId() {
		return elementId;
	}

	public boolean isResizable() {
		return resizable;
	}

	public boolean isRepositionable() {
		return repositionable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
		result = prime * result + (repositionable ? 1231 : 1237);
		result = prime * result + (resizable ? 1231 : 1237);
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
		BoundsChangeHint other = (BoundsChangeHint) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		if (repositionable != other.repositionable)
			return false;
		if (resizable != other.resizable)
			return false;
		return true;
	}

}
