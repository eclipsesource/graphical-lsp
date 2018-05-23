package at.tortmayr.chillisp.api.actions;

public class CollapseExpandAllAction extends Action {
	public boolean expand = true;

	public CollapseExpandAllAction() {
		super(Action.Kind.COLLAPSE_EXPAND_ALL);

	}

	public CollapseExpandAllAction(boolean expand) {
		this();
		this.expand = expand;
	}

	public boolean isExpand() {
		return expand;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (expand ? 1231 : 1237);
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
		CollapseExpandAllAction other = (CollapseExpandAllAction) obj;
		if (expand != other.expand)
			return false;
		return true;
	}

}
