package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;

public class SelectAction extends Action {
	private String[] selectedElementsIDs;
	private String[] deselectedElementsIDs;

	public SelectAction() {
		super(ActionRegistry.Kind.SELECT);
	}

	public SelectAction(String[] selectedElementsIDs, String[] deselectedElementsIDs) {
		this();
		this.selectedElementsIDs = selectedElementsIDs;
		this.deselectedElementsIDs = deselectedElementsIDs;
	}

	public String[] getSelectedElementsIDs() {
		return selectedElementsIDs;
	}

	public String[] getDeselectedElementsIDs() {
		return deselectedElementsIDs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(deselectedElementsIDs);
		result = prime * result + Arrays.hashCode(selectedElementsIDs);
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
		SelectAction other = (SelectAction) obj;
		if (!Arrays.equals(deselectedElementsIDs, other.deselectedElementsIDs))
			return false;
		if (!Arrays.equals(selectedElementsIDs, other.selectedElementsIDs))
			return false;
		return true;
	}

}
