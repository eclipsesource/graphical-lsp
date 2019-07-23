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
package com.eclipsesource.glsp.graph.gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.eclipsesource.glsp.graph.GModelElement;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GModelElementTypeAdapter extends PropertyBasedTypeAdapter<GModelElement> {

	private static final Logger LOG = Logger.getLogger(GModelElementTypeAdapter.class);

	private final Gson gson;
	private final Map<String, EClass> typeMap;

	public static class Factory implements TypeAdapterFactory {

		private final String typeAttribute;
		private final Map<String, EClass> typeMap;

		public Factory(String typeAttribute, Map<String, EClass> typeMap) {
			this.typeAttribute = typeAttribute;
			this.typeMap = typeMap;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			if (!GModelElement.class.isAssignableFrom(type.getRawType()))
				return null;
			return (TypeAdapter<T>) new GModelElementTypeAdapter(gson, typeAttribute, typeMap);
		}

	}

	public GModelElementTypeAdapter(Gson gson, String typeAttribute, Map<String, EClass> typeMap) {
		super(gson, typeAttribute);
		this.gson = gson;
		this.typeMap = typeMap;
	}

	@Override
	protected GModelElement createInstance(String type) {
		EClass eClass = typeMap.get(type);
		if (eClass != null) {
			GModelElement element = (GModelElement) eClass.getEPackage().getEFactoryInstance().create(eClass);
			element.setType(type);
			return element;
		}
		LOG.error("Cannot find class for type " + type);
		return null;
	}

	@Override
	public void write(JsonWriter out, GModelElement value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			try {
				out.beginObject();
				Set<String> written = new HashSet<>();
				writeProperties(out, value, value.getClass(), written);
				out.endObject();
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void assignProperty(GModelElement instance, String propertyName, JsonReader in)
			throws IllegalAccessException {
		try {
			Field field = findField(instance.getClass(), propertyName);
			Object value = gson.fromJson(in, field.getGenericType());
			if (EList.class.isAssignableFrom(field.getType()) && value instanceof Collection) {
				Collection<?> values = (Collection<?>) value;
				EStructuralFeature feature = instance.eClass().getEStructuralFeature(propertyName);
				Object list = instance.eGet(feature);
				if (list instanceof List<?>) {
					((List<Object>) list).addAll(values);
					return;
				}
			}
			field.set(instance, value);
		} catch (NoSuchFieldException e) {
			// Ignore this property
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void assignProperty(GModelElement instance, String propertyName, JsonElement element)
			throws IllegalAccessException {
		try {
			Field field = findField(instance.getClass(), propertyName);
			Object value = gson.fromJson(element, field.getGenericType());
			if (EList.class.isAssignableFrom(field.getType()) && value instanceof Collection) {
				Collection<?> values = (Collection<?>) value;
				EStructuralFeature feature = instance.eClass().getEStructuralFeature(propertyName);
				Object list = instance.eGet(feature);
				if (list instanceof List<?>) {
					((List<Object>) list).addAll(values);
					return;
				}
			}
			field.set(instance, value);
		} catch (NoSuchFieldException e) {
			// Ignore this property
		}
	}

	@Override
	protected void writeProperties(JsonWriter out, GModelElement instance, Class<?> type, Set<String> written)
			throws IOException, IllegalAccessException {
		for (Field field : type.getDeclaredFields()) {
			if (!gson.excluder().excludeField(field, true) && isSet(instance, field)) {
				int modifiers = field.getModifiers();
				if (!Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers) && written.add(field.getName())) {
					writeProperty(out, instance, field);
				}
			}
		}
		Class<?> superType = type.getSuperclass();
		if (superType != null) {
			writeProperties(out, instance, superType, written);
		}
	}

	protected boolean isSet(GModelElement instance, Field field) {
		return instance.eIsSet(instance.eClass().getEStructuralFeature(field.getName()));
	}

}
