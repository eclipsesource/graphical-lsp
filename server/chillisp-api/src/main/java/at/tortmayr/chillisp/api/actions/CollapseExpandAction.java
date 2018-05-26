package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;

public class CollapseExpandAction extends Action {

	private String[] expandIds;
	private String[] collapseIds;
	private boolean collapse = true;

	public CollapseExpandAction() {
		super(ActionRegistry.Kind.COLLAPSE_EXPAND);
	}

	public CollapseExpandAction(String[] expandIds, String[] collapseIds, boolean collapse) {
		this();
		this.expandIds = expandIds;
		this.collapseIds = collapseIds;
		this.collapse = collapse;
	}

	public String[] getExpandIds() {
		return expandIds;
	}

	public String[] getCollapseIds() {
		return collapseIds;
	}

	public boolean isCollapse() {
		return collapse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (collapse ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(collapseIds);
		result = prime * result + Arrays.hashCode(expandIds);
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
		CollapseExpandAction other = (CollapseExpandAction) obj;
		if (collapse != other.collapse)
			return false;
		if (!Arrays.equals(collapseIds, other.collapseIds))
			return false;
		if (!Arrays.equals(expandIds, other.expandIds))
			return false;
		return true;
	}

}
