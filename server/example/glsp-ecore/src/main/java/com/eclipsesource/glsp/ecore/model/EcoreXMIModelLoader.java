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
package com.eclipsesource.glsp.ecore.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sprotty.Dimension;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SGraph;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.ecore.emf.EcoreModelStateProvider;
import com.eclipsesource.glsp.ecore.emf.ResourceManager;
import com.eclipsesource.glsp.server.model.GLSPGraph;
import com.eclipsesource.glsp.server.model.IFileExtensionLoader;
import com.google.inject.Inject;

public class EcoreXMIModelLoader implements IFileExtensionLoader<EObject> {
	private EcoreModelStateProvider modelStateProvider;
	private ResourceManager resourceManager;
	private EcoreSModelConverter smodelConverter;

	@Inject
	public EcoreXMIModelLoader(ResourceManager resourceManager, EcoreModelStateProvider modelStateProvider,
			EcoreSModelConverter smodelConverter) {
		this.resourceManager = resourceManager;
		this.modelStateProvider = modelStateProvider;
		this.smodelConverter = smodelConverter;
	}

	@Override
	public List<String> getExtensions() {
		return Arrays.asList("ecore");
	}

	@Override
	public Optional<EObject> loadFromFile(String fileURI, String clientId) {
		Optional<Resource> resource = resourceManager.loadResource(fileURI);
		if (resource.isPresent() && !resource.get().getContents().isEmpty()) {
			Optional<EObject> model = Optional.of(resource.get().getContents().get(0));
			modelStateProvider.registerModel(clientId, model.get());
			return model;
		}
		return Optional.empty();
	}

	@Override
	public SModelRoot generate(EObject sourceModel) {
		GLSPGraph result = new GLSPGraph();
		result.setNeedsInitialLayout(false);
		result.setId("graph");
		result.setType("graph");
		result.setSize(new Dimension(10000, 8000));
		if (sourceModel instanceof EPackage) {
			fillGraph(result, (EPackage) sourceModel);
		}

		return result;
	}

	private void fillGraph(SGraph sGraph, EPackage ePackage) {
		Map<String, SModelElement> graphChildren = new LinkedHashMap<String, SModelElement>();

		for (EClassifier eClassifier : ePackage.getEClassifiers()) {
			createClassifierNode(ePackage, graphChildren, eClassifier, false);
		}
		sGraph.setChildren(new ArrayList<>(graphChildren.values()));
	}

	private void createClassifierNode(EPackage ePackage, Map<String, SModelElement> graphChildren,
			EClassifier eClassifier, boolean foreignPackage) {
		ClassNode node = smodelConverter.createClassNode(eClassifier, foreignPackage, Optional.empty());
		graphChildren.put(node.getId(), node);
		if (eClassifier instanceof EClass) {
			// create attributes
			EClass eClass = (EClass) eClassifier;
			SModelElement attributeCompartment = node.getChildren().get(1);
			List<SModelElement> attributes = new ArrayList<>();
			for (EAttribute eAttribute : eClass.getEAttributes()) {
				attributes.add(smodelConverter.createAttributeLabel(eAttribute));
			}
			attributeCompartment.setChildren(attributes);
			// we do not want to have edged from classes which are in a foreign package
			if (!foreignPackage) {
				for (EReference eReference : eClass.getEReferences()) {
					SEdge reference = smodelConverter.createReferenceEdge(eReference);
					graphChildren.put(reference.getId(), reference);

					if (eReference.getEReferenceType().getEPackage() != ePackage) {
						EClass referencedType = eReference.getEReferenceType();
						String referenedid = referencedType.getEPackage().getName() + "/" + referencedType.getName();
						if (!graphChildren.containsKey(referenedid)) {
							createClassifierNode(eReference.getEReferenceType().getEPackage(), graphChildren,
									referencedType, true);
						}
					}
				}

				for (EClass superClass : eClass.getESuperTypes()) {
					SEdge reference = smodelConverter.createInheritanceEdge(eClass, superClass);
					graphChildren.put(reference.getId(), reference);
				}
			}
		} else if (eClassifier instanceof EEnum) {
			EEnum eEnum = (EEnum) eClassifier;
			SModelElement attributeCompartment = node.getChildren().get(1);
			List<SModelElement> literals = new ArrayList<>();
			for (EEnumLiteral eliteral : eEnum.getELiterals()) {
				literals.add(smodelConverter.createEnumLiteralLabel(eliteral));
			}
			attributeCompartment.setChildren(literals);
		}
	}
}
