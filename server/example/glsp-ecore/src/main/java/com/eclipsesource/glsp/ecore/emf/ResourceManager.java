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
package com.eclipsesource.glsp.ecore.emf;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.google.inject.Singleton;

@Singleton
public class ResourceManager {
	private static Logger LOGGER = Logger.getLogger(ResourceManager.class);
	private EditingDomain editingDomain;

	public ResourceManager() {
		editingDomain = new AdapterFactoryEditingDomain(new EcoreAdapterFactory(), new BasicCommandStack());
		editingDomain.getResourceSet().getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		EcorePackage.eINSTANCE.eClass();
	}

	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	public Resource createResource(URI uri) {
		return editingDomain.getResourceSet().createResource(uri);
	}

	public Optional<Resource> loadResource(String fileURI) {
		try {
			URI uri = URI.createURI(fileURI);
			Resource resource = editingDomain.loadResource(uri.toFileString());
			resource.load(Collections.EMPTY_MAP);
			EcoreUtil.resolveAll(resource);
			return Optional.of(resource);
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return Optional.empty();
	}

	public boolean save(Resource resource) {
		try {
			if (resource != null) {
				resource.save(Collections.EMPTY_MAP);
				return true;
			}
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return false;

	}
}
