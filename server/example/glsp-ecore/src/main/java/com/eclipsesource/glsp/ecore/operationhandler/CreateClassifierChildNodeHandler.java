package com.eclipsesource.glsp.ecore.operationhandler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.ecore.emf.EMFCommandService;
import com.eclipsesource.glsp.ecore.emf.EcoreModelState;
import com.eclipsesource.glsp.ecore.emf.EcoreModelStateProvider;
import com.eclipsesource.glsp.ecore.model.ClassNode;
import com.eclipsesource.glsp.ecore.model.EcoreSModelConverter;
import com.eclipsesource.glsp.ecore.model.ModelTypes;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;
import com.google.inject.Inject;

public class CreateClassifierChildNodeHandler extends CreateNodeOperationHandler {

	private List<String> handledElementTypeIds = Arrays.asList(ModelTypes.ATTRIBUTE, ModelTypes.OPERATION, ModelTypes.ENUMLITERAL);
	private String elementTypeId;
	@Inject
	private EcoreModelStateProvider modelStateProvider;
	@Inject
	private EMFCommandService commandService;
	@Inject
	private EcoreSModelConverter smodelConverter;

	public CreateClassifierChildNodeHandler() {
		super(false);
	}

	@Override
	public boolean handles(Action execAction) {
		if (execAction instanceof CreateNodeOperationAction) {
			CreateNodeOperationAction action = (CreateNodeOperationAction) execAction;
			return handledElementTypeIds.contains(action.getElementTypeId());
		}
		return false;
	}

	@Override
	protected Optional<SModelElement> retrieveContainer(Optional<String> containerId, IModelState modelState) {
		Optional<SModelElement> baseContainer = super.retrieveContainer(containerId, modelState);
		if (baseContainer.isPresent() && baseContainer.get() instanceof ClassNode) {
			return Optional.ofNullable(baseContainer.get().getChildren().get(1));
		}
		return super.retrieveContainer(containerId, modelState);
	}

	@Override
	public Optional<SModelRoot> execute(Action action, IModelState modelState) {
		elementTypeId = ((CreateNodeOperationAction) action).getElementTypeId();
		return super.execute(action, modelState);
	}

	@Override
	protected SModelElement createNode(Optional<Point> point, SModelElement container, IModelState modelState) {
		String clientId = modelState.getClientId();
		Optional<EcoreModelState> ecoreModelState = modelStateProvider.getModelState(clientId);
		if (!ecoreModelState.isPresent()) {
			return null;
		}
		Optional<EClassifier> ecoreContainer = ecoreModelState.get().getIndex()
				.get(modelState.getIndex().getParent(container).get().getId(), EClassifier.class);
		if (!ecoreContainer.isPresent()) {
			return null;
		}
		if (elementTypeId.equals(ModelTypes.ATTRIBUTE)) {
			EAttribute attribute= createEAttribute(modelState);
			commandService.add(ecoreContainer.get(), EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(), attribute);
			ecoreModelState.get().getIndex().addToIndex(attribute);
			return smodelConverter.createAttributeLabel(attribute);
		}else if(elementTypeId.contentEquals(ModelTypes.ENUMLITERAL)) {
			EEnumLiteral literal= createEEnumLiteral(modelState);
			commandService.add(ecoreContainer.get(), EcorePackage.eINSTANCE.getEEnum_ELiterals(), literal);
			ecoreModelState.get().getIndex().addToIndex(literal);
			return smodelConverter.createEnumLiteralLabel(literal);
		}
		ecoreModelState.get().setDirty(true);
		return null;
	}
	
	protected EEnumLiteral createEEnumLiteral(IModelState modelState) {
		EEnumLiteral literal= EcoreFactory.eINSTANCE.createEEnumLiteral();
		Function<Integer, String> idProvider = i -> "literal" + i;
		int i = getCounter(modelState.getIndex(), elementTypeId, idProvider);
		literal.setValue(i);
		literal.setName(idProvider.apply(i));
		return literal;
		
	}
	protected EAttribute createEAttribute(IModelState modelState) {
		EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		Function<Integer, String> idProvider = i -> "attribute" + i;
		int nodeCounter = getCounter(modelState.getIndex(), elementTypeId, idProvider);
		attribute.setName(idProvider.apply(nodeCounter));
		attribute.setEType(EcorePackage.eINSTANCE.getEString());
		return attribute;
	}

}
