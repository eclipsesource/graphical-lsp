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
package com.eclipsesource.glsp.api.diagram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.lsp4j.jsonrpc.json.adapters.EnumTypeAdapter;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.json.SModelElementTypeAdapter;
import com.google.gson.GsonBuilder;

public interface DiagramHandlerProvider {

	Collection<String> getDiagramTypes();

	default Optional<DiagramManager> get(String diagramType) {
		return getAll().stream().filter(h -> h.getDiagramType().equals(diagramType)).findFirst();
	}

	Collection<DiagramManager> getAll();

	default boolean provides(String diagramType) {
		return getDiagramTypes().contains(diagramType);
	}

	default Optional<DiagramManager> createDefault() {
		if (getDiagramTypes().size() == 1) {
			String diagramType = getDiagramTypes().iterator().next();
			return get(diagramType);
		}
		return Optional.empty();
	}

	default GsonBuilder configureGSON() {
		GsonBuilder builder = new GsonBuilder();
		Map<String, Class<? extends SModelElement>> modelTypes = new HashMap<>();
		getAll().stream().map(DiagramManager::getTypeMappings).forEach(modelTypes::putAll);
		builder.registerTypeAdapterFactory(new SModelElementTypeAdapter.Factory(modelTypes))
				.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
		return builder;
	}
}
