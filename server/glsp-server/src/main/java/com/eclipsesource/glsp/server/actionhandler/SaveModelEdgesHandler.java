package com.eclipsesource.glsp.server.actionhandler;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.SaveModelEdgesAction;
import com.eclipsesource.glsp.api.model.ElementAndRoutingPoints;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GPoint;
import com.google.inject.Inject;

public class SaveModelEdgesHandler extends AbstractActionHandler{

	private static final Logger LOG = Logger.getLogger(SaveModelActionHandler.class);
	
	@Inject
	private ModelSubmissionHandler modelSubmissionHandler;
	
	@Override
	public boolean handles(Action action) {
		return action instanceof SaveModelEdgesAction;
	}
	@Override
	protected Optional<Action> execute(Action action, GraphicalModelState modelState) {
		
		SaveModelEdgesAction saveModelEdgesAction = (SaveModelEdgesAction)action;
		List<ElementAndRoutingPoints> elementAndRoutingPointsList = saveModelEdgesAction.getNewRoutingPoints();
		for(ElementAndRoutingPoints elementAndRoutingPoints : elementAndRoutingPointsList) {
			Optional<GModelElement> modelElement = modelState.getIndex().get(elementAndRoutingPoints.getElementId());
			if(modelElement.isPresent() && modelElement.get() instanceof GEdge) {
				GEdge edge = (GEdge) modelElement.get();
				EList<GPoint> routingPoints = edge.getRoutingPoints();
				routingPoints.clear();
				routingPoints.addAll(elementAndRoutingPoints.getRoutingPoints());
				
			}
			
		}
		return modelSubmissionHandler.doSubmitModel(true, modelState);
	}
}
