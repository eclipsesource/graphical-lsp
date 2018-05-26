package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.ActionRegistry;
import io.typefox.sprotty.api.SModelRoot;

public class SetModelAction extends Action {

	public SetModelAction() {
		super(ActionRegistry.Kind.SET_MODEL);

	}

	public SetModelAction(SModelRoot newRoot) {
		this();
		this.newRoot = newRoot;
	}

	private SModelRoot newRoot;

	public SModelRoot getNewRoot() {
		return newRoot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newRoot == null) ? 0 : newRoot.hashCode());
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
		SetModelAction other = (SetModelAction) obj;
		if (newRoot == null) {
			if (other.newRoot != null)
				return false;
		} else if (!newRoot.equals(other.newRoot))
			return false;
		return true;
	}

}
