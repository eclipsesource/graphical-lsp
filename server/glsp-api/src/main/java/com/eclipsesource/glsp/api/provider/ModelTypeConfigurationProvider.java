/*******************************************************************************

 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.provider;

import java.util.List;
import java.util.Map;

import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.server.json.EnumTypeAdapter;

import com.eclipsesource.glsp.api.json.SModelElementTypeAdapter;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;
import com.google.gson.GsonBuilder;

public interface ModelTypeConfigurationProvider {

	Map<String, Class<? extends SModelElement>> getTypeToClassMappings();

	List<NodeTypeHint> getNodeTypeHints();

	List<EdgeTypeHint> getEdgeTypeHints();

	default GsonBuilder configureGSON() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new SModelElementTypeAdapter.Factory(getTypeToClassMappings()))
				.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
		return builder;
	}

	default EdgeTypeHint createDefaultEdgeTypeHint(String elementId) {
		return new EdgeTypeHint(elementId, true, true, true, null, null);
	}

	default NodeTypeHint createDefaultNodeTypeHint(String elementId) {
		return new NodeTypeHint(elementId, true, true, true);
	}
}
