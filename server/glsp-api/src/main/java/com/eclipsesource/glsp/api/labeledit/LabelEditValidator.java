package com.eclipsesource.glsp.api.labeledit;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GModelElement;

public interface LabelEditValidator {

	public EditLabelValidationResult validate(GraphicalModelState modelState, String label, GModelElement element);

	final static class NullImpl implements LabelEditValidator {

		@Override
		public EditLabelValidationResult validate(GraphicalModelState modelState, String label, GModelElement element) {
			return new EditLabelValidationResult(SeverityKind.OK, null);
		}
	}

}
