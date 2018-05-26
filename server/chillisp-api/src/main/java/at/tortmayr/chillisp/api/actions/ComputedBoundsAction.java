package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;
import io.typefox.sprotty.api.ElementAndAlignment;
import io.typefox.sprotty.api.ElementAndBounds;

public class ComputedBoundsAction extends Action {

	private ElementAndBounds[] bounds;
	private ElementAndAlignment[] alignments;

	public ComputedBoundsAction() {
		super(ActionRegistry.Kind.COMPUTED_BOUNDS);
	}

	public ComputedBoundsAction(ElementAndBounds[] bounds, ElementAndAlignment[] alignments) {
		this();
		this.bounds = bounds;
		this.alignments = alignments;
	}

	public void setBounds(ElementAndBounds[] bounds) {
		this.bounds = bounds;
	}

	public void setAlignments(ElementAndAlignment[] alignments) {
		this.alignments = alignments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(alignments);
		result = prime * result + Arrays.hashCode(bounds);
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
		ComputedBoundsAction other = (ComputedBoundsAction) obj;
		if (!Arrays.equals(alignments, other.alignments))
			return false;
		if (!Arrays.equals(bounds, other.bounds))
			return false;
		return true;
	}

}
