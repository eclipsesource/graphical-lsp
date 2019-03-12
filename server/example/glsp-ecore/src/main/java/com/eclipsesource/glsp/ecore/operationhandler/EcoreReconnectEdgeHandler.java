package com.eclipsesource.glsp.ecore.operationhandler;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sprotty.SEdge;

import com.eclipsesource.glsp.api.action.kind.ReconnectConnectionOperationAction;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.ecore.emf.EMFCommandService;
import com.eclipsesource.glsp.ecore.emf.EcoreModelState;
import com.eclipsesource.glsp.ecore.emf.EcoreModelStateProvider;
import com.eclipsesource.glsp.ecore.model.ModelTypes;
import com.eclipsesource.glsp.server.operationhandler.ReconnectEdgeHandler;
import com.google.inject.Inject;

public class EcoreReconnectEdgeHandler extends ReconnectEdgeHandler {
	private static Logger log= Logger.getLogger(EcoreReconnectEdgeHandler.class);
	@Inject
	private EcoreModelStateProvider modelStateProvider;
	@Inject
	private EMFCommandService commandService;

	@Override
	protected boolean reconnect(ReconnectConnectionOperationAction action, IModelState modelState) {
		Optional<EcoreModelState> ecoreModelState = modelStateProvider.getModelState(modelState.getClientId());
		if (ecoreModelState.isPresent()) {
			Optional<EClass> source = ecoreModelState.get().getIndex().get(action.getSourceElementId(), EClass.class);
			Optional<EClass> target = ecoreModelState.get().getIndex().get(action.getTargetElementId(), EClass.class);
			Optional<SEdge> sEdge = modelState.getCurrentModelIndex().findElement(action.getConnectionElementId(),
					SEdge.class);
			Optional<EReference> reference = ecoreModelState.get().getIndex().get(action.getConnectionElementId(),
					EReference.class);
			if (!source.isPresent() || !target.isPresent() || (!sEdge.isPresent() && !reference.isPresent())) {
				log.warn("Invalid edge, source or target ID: edge ID " + action.getConnectionElementId() + ", source ID " 
						+ action.getSourceElementId() + " and target ID " + action.getTargetElementId());
				return false;
			}

			if (reference.isPresent()) {
				reconnectReference(reference.get(), source.get(), target.get());
			} else if (sEdge.isPresent() && sEdge.get().getType().equals(ModelTypes.INHERITANCE)) {
				Optional<EClass> oldSource = ecoreModelState.get().getIndex().get(sEdge.get().getSourceId(),
						EClass.class);
				Optional<EClass> oldTarget = ecoreModelState.get().getIndex().get(sEdge.get().getTargetId(),
						EClass.class);
				if (!oldSource.isPresent() || !oldTarget.isPresent()) {
					return false;
				}
				reconnectInheritance(oldSource.get(), oldTarget.get(), source.get(), target.get());
			}

		}
		return super.reconnect(action, modelState);
	}

	private void reconnectInheritance(EClass oldSource, EClass oldTarget, EClass source, EClass target) {
		this.commandService.remove(oldSource, EcorePackage.eINSTANCE.getEClass_ESuperTypes(), oldTarget);
		this.commandService.add(source, EcorePackage.eINSTANCE.getEClass_ESuperTypes(), target);

	}

	private void reconnectReference(EReference reference, EClass source, EClass target) {
		this.commandService.remove(reference.eContainer(), EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(),
				reference);
		reference.setEType(target);
		reference.setName(target.getName().toLowerCase() + "s");
		this.commandService.add(source, EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(), reference);
	}

}
