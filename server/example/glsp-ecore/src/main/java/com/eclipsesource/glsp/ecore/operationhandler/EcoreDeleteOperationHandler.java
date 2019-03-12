/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.ecore.operationhandler;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.ecore.emf.EMFCommandService;
import com.eclipsesource.glsp.ecore.emf.EcoreModelState;
import com.eclipsesource.glsp.ecore.emf.EcoreModelStateProvider;
import com.eclipsesource.glsp.ecore.model.ModelTypes;
import com.eclipsesource.glsp.server.operationhandler.DeleteElementOperationHandler;
import com.google.inject.Inject;

public class EcoreDeleteOperationHandler extends DeleteElementOperationHandler {
	@Inject
	private EcoreModelStateProvider modelStateProvider;
	@Inject
	private EMFCommandService commandService;

	@Override
	protected boolean deleteDependents(Collection<SModelElement> dependents, IModelState modelState) {
		boolean success = super.deleteDependents(dependents, modelState);
		if (success) {
			Optional<EcoreModelState> ecoreModelState = modelStateProvider.getModelState(modelState.getClientId());
			if (ecoreModelState.isPresent()) {
				EObject root = ecoreModelState.get().getCurrentModel();
				// Aggregate dependents by type and retrieve corresponding EObject
				List<EClassifier> classifiersToDelete = dependents.stream().filter(SNode.class::isInstance)
						.map(element -> ecoreModelState.get().getIndex().get(element.getId(), EClassifier.class))
						.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

				List<EReference> referencesToDelete = dependents.stream().filter(SEdge.class::isInstance)
						.map(element -> ecoreModelState.get().getIndex().get(element.getId(), EReference.class))
						.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

				// Inheritances have to be treated specially because they do not have a direct
				// mapping in ecore.
				// We only have to consider super class targets that are not in
				// `classifiersToDelete'
				List<EClass> inheritancesToDelete = dependents.stream()
						.filter(e -> e instanceof SEdge && e.getType().equals(ModelTypes.INHERITANCE))
						.map(element -> ecoreModelState.get().getIndex().get(((SEdge) element).getTargetId(),
								EClass.class))
						.filter(opt -> opt.isPresent() && !classifiersToDelete.contains(opt.get())).map(Optional::get)
						.collect(Collectors.toList());
				// remove references
				if (!referencesToDelete.isEmpty()) {
					commandService.remove(root, EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(), referencesToDelete);

				}

				// remove inheritances
				if (!inheritancesToDelete.isEmpty()) {
					inheritancesToDelete.forEach(baseClass -> {
						List<EClass> superTypesToDelete = baseClass.getESuperTypes().stream()
								.filter(e -> classifiersToDelete.contains(e)).collect(Collectors.toList());
						commandService.remove(baseClass, EcorePackage.eINSTANCE.getEClass_ESuperTypes(),
								superTypesToDelete);
					});
				}
			

				if (!classifiersToDelete.isEmpty()) {
					commandService.remove(root, EcorePackage.eINSTANCE.getEPackage_EClassifiers(), classifiersToDelete);
				}
				ecoreModelState.get().setDirty(true);
			}
		}
		return success;
	}

}
