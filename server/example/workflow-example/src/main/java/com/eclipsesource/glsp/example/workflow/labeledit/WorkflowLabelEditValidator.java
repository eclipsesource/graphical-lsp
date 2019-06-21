package com.eclipsesource.glsp.example.workflow.labeledit;

import java.util.Set;

import com.eclipsesource.glsp.api.labeledit.EditLabelValidationResult;
import com.eclipsesource.glsp.api.labeledit.LabelEditValidator;
import com.eclipsesource.glsp.api.labeledit.SeverityKind;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode;
import com.eclipsesource.glsp.graph.GModelElement;

public class WorkflowLabelEditValidator implements LabelEditValidator {

	@Override
	public EditLabelValidationResult validate(GraphicalModelState modelState, String label, GModelElement element) {
		if (label.length() < 1) {
			return new EditLabelValidationResult(SeverityKind.ERROR, "Name must not be empty");
		}
		
		Set<TaskNode> taskNodes = modelState.getIndex().getAllByClass(TaskNode.class);		
		boolean hasDuplicate = taskNodes.stream()
			.filter(e -> !e.getId().equals(element.getId()))
			.map(TaskNode::getName).anyMatch(name -> name.equals(label));
		if (hasDuplicate) {
			return new EditLabelValidationResult(SeverityKind.WARNING, "Name should be unique");
		}
		
		return EditLabelValidationResult.OK_RESULT;
	}

}
