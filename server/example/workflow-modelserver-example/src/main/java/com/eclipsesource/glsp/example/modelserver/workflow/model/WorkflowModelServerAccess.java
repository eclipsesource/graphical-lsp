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

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.modelserver.coffee.model.coffee.CoffeePackage;
import com.eclipsesource.modelserver.coffee.model.coffee.Flow;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;

public class WorkflowModelServerAccess {

	private static Logger LOGGER = Logger.getLogger(WorkflowModelServerAccess.class);

	private static final String FILE_PREFIX = "file://";

	private String sourceURI;
	private ResourceSet resourceSet;

	private WorkflowFacade workflowFacade;

	private Map<String, Node> idMapping;

	public WorkflowModelServerAccess(String sourceURI) {
		this.sourceURI = sourceURI;
		setupResourceSet();
	}

	public void setupResourceSet() {
		resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(CoffeePackage.eINSTANCE.getNsURI(), CoffeePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(WfnotationPackage.eINSTANCE.getNsURI(), WfnotationPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
	}

	public WorkflowFacade getWorkflowFacade() {
		if (workflowFacade == null) {
			createWorkflowFacade();
		}
		return workflowFacade;
	}

	protected WorkflowFacade createWorkflowFacade() {
		try {
			Resource notationResource = loadResource(convertToFile(sourceURI).getAbsolutePath());
			Resource semanticResource = loadResource(convertToFile(getSemanticResource(sourceURI)).getAbsolutePath());
			workflowFacade = new WorkflowFacade(semanticResource, notationResource);
			return workflowFacade;
		} catch (IOException e) {
			LOGGER.error(e);
			return null;
		}
	}

	private File convertToFile(String sourceURI) {
		if (sourceURI != null && sourceURI.startsWith(FILE_PREFIX)) {
			return new File(sourceURI.substring(FILE_PREFIX.length()));
		}
		return null;
	}

	private String getSemanticResource(String uri) {
		return uri.replaceFirst(".coffeenotation", ".coffee");
	}

	private Resource loadResource(String path) throws IOException {
		Resource resource = createResource(path);
		resource.load(Collections.EMPTY_MAP);
		return resource;
	}

	private Resource createResource(String path) {
		return resourceSet.createResource(URI.createFileURI(path));
	}

	public void setNodeMapping(Map<Node, GNode> mapping) {
		initIdMap(mapping);
	}

	private void initIdMap(Map<Node, GNode> mapping) {
		idMapping = new HashMap<>();
		mapping.entrySet().forEach(entry -> idMapping.put(entry.getValue().getId(), entry.getKey()));
	}

	public Node getNodeById(String id) {
		return idMapping.get(id);
	}

	public Optional<Flow> getFlow(Node source, Node target) {
		return this.workflowFacade.getCurrentWorkflow().getFlows().stream()
				.filter(flow -> source.equals(flow.getSource()) && target.equals(flow.getTarget())).findFirst();
	}

	public void save() throws IOException {
		workflowFacade.getSemanticResource().save(Collections.emptyMap());
		workflowFacade.getNotationResource().save(Collections.emptyMap());
	}

}
