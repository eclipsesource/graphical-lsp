package at.tortmayr.glsp.server.handlers;

import java.util.Optional;

import at.tortmayr.glsp.api.action.ExecuteOperationAction;
import at.tortmayr.glsp.api.action.kind.MoveOperationAction;
import at.tortmayr.glsp.api.factory.GraphicalModelState;
import at.tortmayr.glsp.api.operations.OperationHandler;
import at.tortmayr.glsp.api.utils.SModelIndex;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

/**
 * Generic handler implementation for {@link MoveOperationAction}
 */
public class MoveNodeHandler implements OperationHandler {

	@Override
	public boolean handles(ExecuteOperationAction action) {
		return action instanceof MoveOperationAction;
	}

	@Override
	public Optional<SModelRoot> execute(ExecuteOperationAction action, GraphicalModelState modelState) {
		MoveOperationAction moveAction = (MoveOperationAction)action;
		String elementId = moveAction.getElementId();
		Point location = moveAction.getLocation();
		
		if (elementId == null || location == null) {
			System.out.println("Invalid Move Action; missing mandatory arguments");
			return Optional.empty();
		}
		
		SModelIndex index = modelState.getCurrentModelIndex();
		SModelRoot currentModel = modelState.getCurrentModel();
		
		SModelElement element = index.get(elementId);
		if (element == null) {
			System.out.println("Element with id "+elementId+" not found");
			return Optional.empty();
		}
		
		if (false == element instanceof SNode) {
			System.out.println("Element "+elementId+" is not moveable");
			return Optional.empty();
		}
		
		SNode nodeToMove = (SNode)element;
		nodeToMove.setPosition(location);
		
		return Optional.of(currentModel);
	}

}
