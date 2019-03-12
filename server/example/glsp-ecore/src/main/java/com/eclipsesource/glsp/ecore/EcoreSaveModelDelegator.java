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
package com.eclipsesource.glsp.ecore;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.eclipsesource.glsp.ecore.emf.EcoreModelState;
import com.eclipsesource.glsp.ecore.emf.EcoreModelStateProvider;
import com.eclipsesource.glsp.ecore.emf.ResourceManager;
import com.eclipsesource.glsp.server.model.JSONSavemodelDelegator;
import com.google.inject.Inject;

public class EcoreSaveModelDelegator extends JSONSavemodelDelegator {
	private static Logger LOGGER = Logger.getLogger(EcoreSaveModelDelegator.class);
	@Inject
	private ResourceManager resourceManager;
	@Inject
	private EcoreModelStateProvider modelStateProvider;

	@Override
	public void save(String fileURI, String clientId) {
		super.save(fileURI, clientId);
		Optional<EcoreModelState> modelState = modelStateProvider.getModelState(clientId);
		if (modelState.isPresent() && modelState.get().isDirty()) {
			URI uri = URI.createURI(fileURI);
			Resource resource = modelState.get().getCurrentModel().eResource();
			if (resource == null || !resource.getURI().equals(uri)) {
				resource = resourceManager.createResource(uri);
				resource.getContents().add(modelState.get().getCurrentModel());
			}
			resourceManager.save(resource);
			modelState.get().setDirty(false);
		}
	}

	protected void saveToFile(String fileURI, EObject model) {
		if (!fileURI.endsWith(".ecore")) {
			fileURI = replaceExtension(fileURI, "ecore");
		}
		URI uri = URI.createURI(fileURI);
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EcorePackage.eINSTANCE.eClass();
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(model);
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

}
