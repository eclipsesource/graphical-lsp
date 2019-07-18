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
package com.eclipsesource.glsp.example.modelserver.workflow.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Diagram;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage;
import com.eclipsesource.modelserver.coffee.model.coffee.CoffeePackage;
import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;
import com.eclipsesource.modelserver.coffee.model.coffee.Workflow;

public class WorkflowFacadeTest {

	private static final String RESOURCE_PATH = "src/test/resources/";

	private ResourceSetImpl resourceSet;

	@BeforeEach
	void setupResourceSet() {
		resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(CoffeePackage.eINSTANCE.getNsURI(), CoffeePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(WfnotationPackage.eINSTANCE.getNsURI(), WfnotationPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
	}

	@Test
	public void readingNotation() throws IOException {
		Resource coffeeResource = loadResource("SuperBrewer3000.coffee");
		Resource notationResource = loadResource("SuperBrewer3000.coffeenotation");
		WorkflowFacade facade = new WorkflowFacade(coffeeResource, notationResource);

		Machine machine = (Machine) coffeeResource.getContents().get(0);
		Workflow workflow = machine.getWorkflows().get(0);
		Node preHeat = workflow.getNodes().get(0);

		Optional<DiagramElement> diagramElement = facade.findDiagramElement(preHeat);
		assertTrue(diagramElement.isPresent());
		assertTrue(diagramElement.get() instanceof Shape);

		Shape preHeatShape = (Shape) diagramElement.get();
		assertEquals(23.0, preHeatShape.getPosition().getX());
		assertEquals(46.0, preHeatShape.getPosition().getY());
	}

	@Test
	public void initializingNotationForSemanticModel() throws IOException {
		Resource coffeeResource = loadResource("SuperBrewer3000.coffee");
		Resource notationResource = createResource("DoesntExist.coffeenotation");
		WorkflowFacade facade = new WorkflowFacade(coffeeResource, notationResource);
		Machine machine = (Machine) coffeeResource.getContents().get(0);
		Workflow workflow = machine.getWorkflows().get(0);
		Diagram notation = facade.initializeNotation(workflow);
		notationResource.getContents().add(notation);

		assertEquals(workflow, notation.getSemanticElement().getResolvedElement());
		assertTrue(workflow.getNodes().stream().allMatch(node -> facade.findDiagramElement(node).isPresent()));
		assertTrue(workflow.getFlows().stream().allMatch(node -> facade.findDiagramElement(node).isPresent()));
	}

	@Test
	public void initializingMissing() throws IOException {
		Resource coffeeResource = loadResource("SuperBrewer3000.coffee");
		Resource notationResource = loadResource("SuperBrewer3000.coffeenotation");
		WorkflowFacade facade = new WorkflowFacade(coffeeResource, notationResource);

		Machine machine = (Machine) coffeeResource.getContents().get(0);
		Workflow workflow = machine.getWorkflows().get(0);
		Node preHeat = workflow.getNodes().get(0);

		Optional<DiagramElement> diagramElement = facade.findDiagramElement(preHeat);
		assertTrue(diagramElement.isPresent());
		assertTrue(diagramElement.get() instanceof Shape);

		EcoreUtil.remove(diagramElement.get());
		assertTrue(facade.findDiagramElement(preHeat).isEmpty());

		facade.initializeMissing(facade.findDiagram(workflow).get());

		assertTrue(facade.findDiagramElement(preHeat).isPresent());
	}

	@Test
	public void findingUnresolvableProxies() throws IOException {
		Resource coffeeResource = loadResource("SuperBrewer3000.coffee");
		Resource notationResource = loadResource("SuperBrewer3000.coffeenotation");
		WorkflowFacade facade = new WorkflowFacade(coffeeResource, notationResource);

		Machine machine = (Machine) coffeeResource.getContents().get(0);
		Workflow workflow = machine.getWorkflows().get(0);
		Node preHeat = workflow.getNodes().get(0);

		List<DiagramElement> unresolvableElements = facade.findUnresolvableElements(facade.findDiagram(workflow).get());
		Optional<DiagramElement> diagramElement = facade.findDiagramElement(preHeat);
		assertFalse(unresolvableElements.contains(diagramElement.get()));

		diagramElement.get().getSemanticElement().setResolvedElement(null);
		diagramElement.get().getSemanticElement().setUri("DOES_NOT_EXIST");

		unresolvableElements = facade.findUnresolvableElements(facade.findDiagram(workflow).get());
		assertTrue(unresolvableElements.contains(diagramElement.get()));
	}

	private Resource loadResource(String fileName) throws IOException {
		Resource resource = createResource(fileName);
		resource.load(Collections.EMPTY_MAP);
		return resource;
	}

	private Resource createResource(String fileName) {
		return resourceSet.createResource(URI.createFileURI(RESOURCE_PATH + fileName));
	}

	@AfterEach
	public void tearDownResourceSet() {
		if (resourceSet != null) {
			resourceSet.getResources().stream().forEach(Resource::unload);
		}
	}

}
