package com.eclipsesource.glsp.example.workflow;

import java.util.Optional;

import com.eclipsesource.glsp.api.action.ExecuteOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.factory.GraphicalModelState;
import com.eclipsesource.glsp.api.operations.OperationHandler;
import com.eclipsesource.glsp.api.utils.SModelIndex;

import io.typefox.sprotty.api.SEdge;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

public class CreateEdgeHandler implements OperationHandler {

	@Override
	public boolean handles(ExecuteOperationAction execAction) {
		if (execAction instanceof CreateConnectionOperationAction) {
			CreateConnectionOperationAction action = (CreateConnectionOperationAction) execAction;
			return WorkflowOperationConfiguration.EDGE_ID.equals(action.getElementTypeId());
		}
		return false;
	}

	@Override
	public Optional<SModelRoot> execute(ExecuteOperationAction operationAction, GraphicalModelState modelState) {
		CreateConnectionOperationAction action = (CreateConnectionOperationAction) operationAction;
		if (action.getSourceElementId() == null || action.getTargetElementId() == null) {
			System.out.println("Incomplete create connection action");
			return Optional.empty();
		}

		SModelIndex index = modelState.getCurrentModelIndex();

		SModelElement source = index.get(action.getSourceElementId());
		SModelElement target = index.get(action.getTargetElementId());

		if (source == null || target == null) {
			System.out.println("NULL source or target for source ID " + action.getSourceElementId() + " and target ID "
					+ action.getTargetElementId());
			return Optional.empty();
		}

		if (false == source instanceof SNode) {
			source = findNode(source, index);
		}
		if (false == target instanceof SNode) {
			target = findNode(target, index);
		}

		SModelRoot currentModel = modelState.getCurrentModel();
		if (source == currentModel || target == currentModel) {
			System.out.println("Can't create a link to the root node");
			return Optional.empty();
		}

		SEdge edge = new SEdge();
		edge.setSourceId(source.getId());
		edge.setTargetId(target.getId());
		String type = "edge";
		edge.setType(type);
		int newID = index.getTypeCount(type);
		while (index.get(type + newID) != null) {
			newID++;
		}
		edge.setId(type + newID);

		currentModel.getChildren().add(edge);
		index.addToIndex(edge, currentModel);

		return Optional.of(currentModel);
	}

	private SModelElement findNode(SModelElement element, com.eclipsesource.glsp.api.utils.SModelIndex index) {
		if (element instanceof SNode) {
			return element;
		}

		SModelElement parent = index.getParent(element);
		if (parent == null) {
			return element;
		}
		return findNode(parent, index);
	}

}
