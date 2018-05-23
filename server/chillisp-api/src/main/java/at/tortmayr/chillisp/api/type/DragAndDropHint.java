package at.tortmayr.chillisp.api.type;

import java.util.Arrays;

public class DragAndDropHint {

	private String dragElementClass;
	private String[] dropElementClasses;

	public DragAndDropHint() {
	};

	public DragAndDropHint(String dragElementClass, String[] dropElementClasses) {
		super();
		this.dragElementClass = dragElementClass;
		this.dropElementClasses = dropElementClasses;
	}

	public String getDragElementClass() {
		return dragElementClass;
	}

	public String[] getDropElementClasses() {
		return dropElementClasses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dragElementClass == null) ? 0 : dragElementClass.hashCode());
		result = prime * result + Arrays.hashCode(dropElementClasses);
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
		DragAndDropHint other = (DragAndDropHint) obj;
		if (dragElementClass == null) {
			if (other.dragElementClass != null)
				return false;
		} else if (!dragElementClass.equals(other.dragElementClass))
			return false;
		if (!Arrays.equals(dropElementClasses, other.dropElementClasses))
			return false;
		return true;
	}

}
