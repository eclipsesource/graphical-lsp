package com.eclipsesource.glsp.ecore.operationhandler;

import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.ecore.emf.EMFCommandService;
import com.eclipsesource.glsp.ecore.emf.EcoreModelState;
import com.eclipsesource.glsp.ecore.emf.EcoreModelStateProvider;
import com.eclipsesource.glsp.ecore.model.ClassNode;
import com.eclipsesource.glsp.server.operationhandler.ChangeContainerOperationHandler;
import com.google.inject.Inject;

public class EcoreChangeContainerHandler extends ChangeContainerOperationHandler {
	@Inject
	private EcoreModelStateProvider modelStateProvider;
	@Inject
	private EMFCommandService commandService;

	@Override
	protected Optional<SModelElement> retrieveContainer(String containerId, IModelState modelState) {
		Optional<SModelElement> baseContainer = super.retrieveContainer(containerId, modelState);
		if (baseContainer.isPresent() && baseContainer.get() instanceof ClassNode) {
			return Optional.ofNullable(baseContainer.get().getChildren().get(1));
		}
		return Optional.empty();
	}

	@Override
	protected boolean changeContainer(SModelElement smodelnewChild, SModelElement smodelContainer,
			IModelState modelState) {
		Optional<EcoreModelState> ecoreModelState = modelStateProvider.getModelState(modelState.getClientId());
		if (ecoreModelState.isPresent()) {
			Optional<EClassifier> container = ecoreModelState.get().getIndex()
					.get(modelState.getIndex().getParent(smodelContainer).get().getId(), EClassifier.class);
			Optional<EObject> newChild = ecoreModelState.get().getIndex().get(smodelnewChild.getId());
			if (!container.isPresent() || !newChild.isPresent()) {
				return false;
			}
			if (newChild.get() instanceof EAttribute) {
				reparent((EAttribute)newChild.get(),container.get());
			} else if (newChild.get() instanceof EEnumLiteral) {
				reparent((EEnumLiteral)newChild.get(),container.get());
			} else if (newChild.get() instanceof EOperation) {
				reparent((EOperation)newChild.get(),container.get());
			}
			ecoreModelState.get().setDirty(true);
		}

		return super.changeContainer(smodelnewChild, smodelContainer, modelState);
	}

	private void reparent(EOperation eOperation, EClassifier container) {
		// TODO Auto-generated method stub
		
	}

	private void reparent(EEnumLiteral eEnumLiteral, EClassifier container) {
		commandService.add(container, EcorePackage.eINSTANCE.getEEnum_ELiterals(), eEnumLiteral);
		
	}

	private void reparent(EAttribute eAttribute, EClassifier container) {
		commandService.add(container, EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(), eAttribute);
	}

}
