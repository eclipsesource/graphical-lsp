package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.ActionRegistry;
import io.typefox.sprotty.api.Bounds;

public class RequestPopupModelAction extends Action {

	private String elementId;
	private Bounds bounds;

	public RequestPopupModelAction() {
		super(ActionRegistry.Kind.REQUEST_POPUP_MODEL);
	}

	public RequestPopupModelAction(String elementId, Bounds bounds) {
		this();
		this.elementId = elementId;
		this.bounds = bounds;
	}

	public String getElementId() {
		return elementId;
	}

	public Bounds getBounds() {
		return bounds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
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
		RequestPopupModelAction other = (RequestPopupModelAction) obj;
		if (bounds == null) {
			if (other.bounds != null)
				return false;
		} else if (!bounds.equals(other.bounds))
			return false;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		return true;
	}

}
