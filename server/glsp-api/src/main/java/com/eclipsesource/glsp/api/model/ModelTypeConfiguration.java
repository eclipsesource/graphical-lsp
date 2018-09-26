/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.model;

import java.util.Collections;
import java.util.Map;

import com.eclipsesource.glsp.api.json.SModelElementTypeAdapter;
import com.google.gson.GsonBuilder;

import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.server.json.EnumTypeAdapter;

/**
 * This configuration class provides the information necessary to determine the
 * corresponding Java class for a SModelElement. For this the type property is
 * used.
 * 
 * @author Tobias Ortmayr
 *
 */
public interface ModelTypeConfiguration {

	/**
	 * Returns a map which enables the identification of the corresponding Java
	 * class for each SModelElement based on its type property. This mappings will
	 * be reused by GSON for proper JSON-to-Java conversion.
	 */
	Map<String, Class<? extends SModelElement>> getModelTypes();

	default GsonBuilder configureGSON() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new SModelElementTypeAdapter.Factory(getModelTypes()))
				.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
		return builder;

	}

	public static final class NullImpl implements ModelTypeConfiguration {

		@Override
		public Map<String, Class<? extends SModelElement>> getModelTypes() {
			return Collections.emptyMap();
		}

	}

}
