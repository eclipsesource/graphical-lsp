package com.eclipsesource.glsp.example.workflow;

import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.AUTOMATED_TASK;
import static com.eclipsesource.glsp.example.workflow.utils.ModelTypes.MANUAL_TASK;
import static com.eclipsesource.glsp.graph.util.GraphUtil.point;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.provider.ContextMenuItemProvider;
import com.eclipsesource.glsp.api.types.MenuItem;
import com.eclipsesource.glsp.graph.GPoint;
import com.google.common.collect.Sets;

public class WorkflowContextMenuItemProvider implements ContextMenuItemProvider {

	@Override
	public Set<MenuItem> getItems(GraphicalModelState modelState, List<String> selectedElementIds,
			Optional<GPoint> position, Map<String, String> args) {
		MenuItem newAutTask = new MenuItem("newAutoTask", "Automated Task",
				Arrays.asList(new CreateNodeOperationAction(AUTOMATED_TASK, position.orElse(point(0, 0)))), true);
		MenuItem newManTask = new MenuItem("newManualTask", "Manual Task",
				Arrays.asList(new CreateNodeOperationAction(MANUAL_TASK, position.orElse(point(0, 0)))), true);
		MenuItem newChildMenu = new MenuItem("new", "New", Arrays.asList(newAutTask, newManTask), "add", "0_new");
		return Sets.newHashSet(newChildMenu);
	}

}
