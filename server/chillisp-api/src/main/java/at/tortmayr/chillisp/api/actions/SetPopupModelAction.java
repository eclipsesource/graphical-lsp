package at.tortmayr.chillisp.api.actions;

import io.typefox.sprotty.api.SModelRoot;

public class SetPopupModelAction extends Action {
	private SModelRoot newRoot;

	public SetPopupModelAction() {
		super(Action.Kind.SET_POPUP_MODEL);
	}

	public SetPopupModelAction(SModelRoot newRoot) {
		this();
		this.newRoot = newRoot;
	}

	public SModelRoot getNewRoot() {
		return newRoot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((newRoot == null) ? 0 : newRoot.hashCode());
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
		SetPopupModelAction other = (SetPopupModelAction) obj;
		if (newRoot == null) {
			if (other.newRoot != null)
				return false;
		} else if (!newRoot.equals(other.newRoot))
			return false;
		return true;
	}

}
