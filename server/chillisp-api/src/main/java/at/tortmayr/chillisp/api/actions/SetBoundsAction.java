package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;
import io.typefox.sprotty.api.ElementAndBounds;

public class SetBoundsAction extends Action {
	private ElementAndBounds[] bounds;

	public SetBoundsAction() {
		super(ActionRegistry.Kind.SET_BOUNDS);
	}

	public SetBoundsAction(ElementAndBounds[] bounds) {
		this();
		this.bounds = bounds;
	}

	public ElementAndBounds[] getBounds() {
		return bounds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(bounds);
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
		SetBoundsAction other = (SetBoundsAction) obj;
		if (!Arrays.equals(bounds, other.bounds))
			return false;
		return true;
	}

}
