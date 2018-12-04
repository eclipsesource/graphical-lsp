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
package com.eclipsesource.glsp.api.json;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.sprotty.server.json.PropertyBasedTypeAdapter;

import com.eclipsesource.glsp.api.action.Action;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class ActionTypeAdapter extends PropertyBasedTypeAdapter<Action> {
	private Map<String, Class<? extends Action>> actions;

	public ActionTypeAdapter(Gson gson, Map<String, Class<? extends Action>> actions) {
		super(gson, "kind");
		this.actions = actions;
	}

	@Override
	protected Action createInstance(String kind) {
		Class<? extends Action> clazz = actions.get(kind);
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
		private Map<String, Class<? extends Action>> actions;

		public Factory(Set<Action> registeredActions) {
			actions = new HashMap<>();
			registeredActions.forEach(action -> actions.put(action.getKind(), action.getClass()));
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!Action.class.isAssignableFrom(typeToken.getRawType()))
				return null;
			return (TypeAdapter<T>) new ActionTypeAdapter(gson, actions);
		}

	}

}
