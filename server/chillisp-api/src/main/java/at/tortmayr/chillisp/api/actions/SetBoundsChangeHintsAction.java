package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.type.BoundsChangeHint;

public class SetBoundsChangeHintsAction extends Action {

	private BoundsChangeHint[] hints;

	public SetBoundsChangeHintsAction() {
		super(Action.Kind.SET_BOUNDS_CHANGE_HINTS);
	}

	public SetBoundsChangeHintsAction(BoundsChangeHint[] hints) {
		this();
		this.hints = hints;
	}

	public BoundsChangeHint[] getHints() {
		return hints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(hints);
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
		SetBoundsChangeHintsAction other = (SetBoundsChangeHintsAction) obj;
		if (!Arrays.equals(hints, other.hints))
			return false;
		return true;
	}

}
