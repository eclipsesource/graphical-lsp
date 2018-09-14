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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.eclipsesource.glsp.api.action.Action;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import io.typefox.sprotty.server.json.PropertyBasedTypeAdapter;

public class ActionTypeAdapter extends PropertyBasedTypeAdapter<Action> {
	private Map<String, Class<? extends Action>> actionKinds;

	public ActionTypeAdapter(Gson gson, Map<String, Class<? extends Action>> actionKinds) {
		super(gson, "kind");
		this.actionKinds=actionKinds;
	}

	@Override
	protected Action createInstance(String kind) {
		Class<? extends Action> clazz = actionKinds.get(kind);
		if (clazz == null)
			throw new IllegalArgumentException("Unknown action kind: " + kind);
		try {
			Constructor<? extends Action> constructor = clazz.getConstructor();
			return constructor.newInstance();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Action class does not have a default constructor.", e);
		} catch (Exception e) {
			throw new RuntimeException("Unable to invoke action constructor", e);
		}
	}

	public static class Factory implements TypeAdapterFactory {
		private Map<String, Class<? extends Action>> actionKinds;

		public Factory(Set<Action> registeredActions) {
			actionKinds = new HashMap<>();
			registeredActions.forEach(action -> actionKinds.put(action.getKind(), action.getClass()));
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!Action.class.isAssignableFrom(typeToken.getRawType()))
				return null;
			return (TypeAdapter<T>) new ActionTypeAdapter(gson,actionKinds);
		}

	}

}
