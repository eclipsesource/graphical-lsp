package com.eclipsesource.glsp.ecore.operationhandler;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.action.kind.ReconnectConnectionOperationAction;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.ecore.emf.EMFCommandService;
import com.eclipsesource.glsp.ecore.emf.EcoreModelState;
import com.eclipsesource.glsp.ecore.emf.EcoreModelStateProvider;
import com.eclipsesource.glsp.ecore.model.EcoreSModelConverter;
import com.eclipsesource.glsp.server.operationhandler.ReconnectEdgeHandler;
import com.google.inject.Inject;

public class EcoreReconnectEdgeHandler extends ReconnectEdgeHandler {
	@Inject
	private EcoreModelStateProvider modelStateProvider;
	@Inject
	private EMFCommandService commandService;
	@Inject
	private EcoreSModelConverter smodelConverter;
	@Override
	protected boolean reconnect(ReconnectConnectionOperationAction action, IModelState modelState) {
		boolean success= super.reconnect(action, modelState);
		if (success) {
			Optional<EcoreModelState> ecoreModelState=modelStateProvider.getModelState(modelState.getClientId());
			if (ecoreModelState.isPresent()){
				Optional<EClass> source= ecoreModelState.get().getIndex().get(action.getSourceElementId(),EClass.class);
				Optional<EClass> target= ecoreModelState.get().getIndex().get(action.getTargetElementId(),EClass.class);
				Optional<SEdge> sEdge = modelState.getCurrentModelIndex().findElement(action.getConnectionElementId(), SEdge.class);
				Optional<EReference> reference = ecoreModelState.get().getIndex().get(action.getConnectionElementId(),EReference.class);
				if ( !source.isPresent() || !target.isPresent() || (!sEdge.isPresent()&& !reference.isPresent())) {
					return false;
				}
				
				
				if (reference.isPresent()) {
					reconnectReference(reference.get(), source.get(),target.get());
				}
			
			
				
			}
			return true;
		}
		return false;
	}
	private void reconnectReference(EReference edge, EClass source, EClass target) {
		// TODO Auto-generated method stub
		
	}
	
	
}
