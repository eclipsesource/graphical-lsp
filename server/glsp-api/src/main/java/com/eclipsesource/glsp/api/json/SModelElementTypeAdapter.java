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
package com.eclipsesource.glsp.api.json;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.server.json.PropertyBasedTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class SModelElementTypeAdapter extends PropertyBasedTypeAdapter<SModelElement> {

	private final Map<String, Class<? extends SModelElement>> modelTypes;

	public SModelElementTypeAdapter(Gson gson, Map<String, Class<? extends SModelElement>> modelTypes) {
		super(gson, "type");
		this.modelTypes = modelTypes;
	}

	@Override
	protected SModelElement createInstance(String type) {
		Class<? extends SModelElement> clazz = modelTypes.get(type);
		if (clazz == null)
			throw new IllegalArgumentException("Unknown model type: " + type);
		try {
			Constructor<? extends SModelElement> constructor = clazz.getConstructor();
			SModelElement element = constructor.newInstance();
			element.setType(type);
			return element;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("SModelElement class does not have a default constructor.", e);
		} catch (Exception e) {
			throw new RuntimeException("Unable to invoke SModelElement constructor", e);
		}
	}
	
	public static class Factory implements TypeAdapterFactory {

		private final Map<String, Class<? extends SModelElement>> modelTypes;

		public Factory(Map<String, Class<? extends SModelElement>> modelTypes) {
			this.modelTypes = modelTypes;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!SModelElement.class.isAssignableFrom(typeToken.getRawType()))
				return null;
			return (TypeAdapter<T>) new SModelElementTypeAdapter(gson, modelTypes);
		}
	}

}
