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
package com.eclipsesource.glsp.api.json;

import java.lang.reflect.Constructor;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.server.json.PropertyBasedTypeAdapter;

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
