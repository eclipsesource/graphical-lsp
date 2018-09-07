package com.eclipsesource.glsp.example.workflow;

import java.util.Optional;
import java.util.function.Function;

import com.eclipsesource.glsp.api.action.ExecuteOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.operations.CreateNodeOperationHandler;
import com.eclipsesource.glsp.api.utils.SModelIndex;
import com.eclipsesource.glsp.example.workflow.schema.ActivityNode;

import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;

public class CreateMergeNodeHandler extends CreateNodeOperationHandler {

	@Override
	public boolean handles(ExecuteOperationAction execAction) {
		if (execAction instanceof CreateNodeOperationAction) {
			CreateNodeOperationAction action = (CreateNodeOperationAction) execAction;
			return WorkflowOperationConfiguration.MERGE_NODE_ID.equals(action.getElementId());
		}
		return false;
	}

	@Override
	protected SModelElement createNode(Optional<Point> point, SModelIndex index) {
		ActivityNode result = new ActivityNode();
		result.setNodeType("mergeNode");
		point.ifPresent(result::setPosition);

		Function<Integer, String> idProvider = i -> "activityNode" + i;
		int i = getCounter(index, result.getType(), idProvider);
		result.setId(idProvider.apply(i));

		return result;
	}

}
