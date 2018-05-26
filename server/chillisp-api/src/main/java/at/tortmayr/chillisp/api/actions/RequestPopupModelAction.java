package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;
import io.typefox.sprotty.api.Bounds;

public class RequestPopupModelAction extends Action {

	private String[] elementIds;
	private Bounds bounds;

	public RequestPopupModelAction() {
		super(ActionRegistry.Kind.REQUEST_POPUP_MODEL);
	}

	public RequestPopupModelAction(String[] elementIds, Bounds bounds) {
		this();
		this.elementIds = elementIds;
		this.bounds = bounds;
	}

	public String[] getElementIds() {
		return elementIds;
	}

	public Bounds getBounds() {
		return bounds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
		result = prime * result + Arrays.hashCode(elementIds);
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
		if (!Arrays.equals(elementIds, other.elementIds))
			return false;
		return true;
	}

}
