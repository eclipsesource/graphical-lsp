package at.tortmayr.glsp.example.workflow;

import java.util.Optional;
import java.util.function.Function;

import at.tortmayr.glsp.api.action.ExecuteOperationAction;
import at.tortmayr.glsp.api.action.kind.CreateNodeOperationAction;
import at.tortmayr.glsp.api.operations.CreateNodeOperationHandler;
import at.tortmayr.glsp.api.utils.SModelIndex;
import at.tortmayr.glsp.example.workflow.schema.ActivityNode;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;

public class CreateDecisionNodeHandler extends CreateNodeOperationHandler {

	@Override
	public boolean handles(ExecuteOperationAction execAction) {
		if (execAction instanceof CreateNodeOperationAction) {
			CreateNodeOperationAction action = (CreateNodeOperationAction) execAction;
			return WorkflowOperationConfiguration.DECISION_NODE_ID.equals(action.getElementId());
		}
		return false;
	}
	
	@Override
	protected SModelElement createNode(Optional<Point> point, SModelIndex index) {
    	ActivityNode result = new ActivityNode();
    	result.setNodeType("decisionNode");
    	point.ifPresent(result::setPosition);
    	
    	Function<Integer, String> idProvider = i -> "activityNode"+ i;
    	int i = getCounter(index, result.getType(), idProvider);
    	result.setId(idProvider.apply(i));
    	
    	return result;
	}

}
