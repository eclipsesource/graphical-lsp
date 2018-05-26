package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;
import at.tortmayr.chillisp.api.type.DragAndDropHint;

public class SetMoveHintsAction extends Action {

	private DragAndDropHint[] hints;

	public SetMoveHintsAction() {
		super(ActionRegistry.Kind.SET_MOVE_HINTS);
	}

	public SetMoveHintsAction(DragAndDropHint[] hints) {
		this();
		this.hints = hints;
	}

	public DragAndDropHint[] getHints() {
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
		SetMoveHintsAction other = (SetMoveHintsAction) obj;
		if (!Arrays.equals(hints, other.hints))
			return false;
		return true;
	}

}
