package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

public class CenterAction extends Action {

	private String[] elementIDs;
	private boolean animate = true;

	public CenterAction() {
		super(Action.Kind.CENTER);
	}

	public CenterAction(String[] elementIDs, boolean animate) {
		this();
		this.elementIDs = elementIDs;
		this.animate = animate;
	}

	public String[] getElementIDs() {
		return elementIDs;
	}

	public boolean isAnimate() {
		return animate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (animate ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(elementIDs);
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
		CenterAction other = (CenterAction) obj;
		if (animate != other.animate)
			return false;
		if (!Arrays.equals(elementIDs, other.elementIDs))
			return false;
		return true;
	}

}
