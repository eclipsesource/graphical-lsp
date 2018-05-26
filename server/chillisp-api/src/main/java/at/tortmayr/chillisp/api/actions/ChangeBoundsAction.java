package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;
import io.typefox.sprotty.api.ElementAndBounds;

public class ChangeBoundsAction extends Action {
	private ElementAndBounds[] newBounds;

	public ChangeBoundsAction() {
		super(ActionRegistry.Kind.CHANGE_BOUNDS_ACTION);

	}

	public ChangeBoundsAction(ElementAndBounds[] newBounds) {
		this();
		this.newBounds = newBounds;
	}

	public ElementAndBounds[] getNewBounds() {
		return newBounds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(newBounds);
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
		ChangeBoundsAction other = (ChangeBoundsAction) obj;
		if (!Arrays.equals(newBounds, other.newBounds))
			return false;
		return true;
	}

}
