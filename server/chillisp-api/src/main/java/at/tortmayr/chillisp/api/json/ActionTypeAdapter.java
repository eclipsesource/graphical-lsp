/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.chillisp.api.json;

import java.lang.reflect.Constructor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import at.tortmayr.chillisp.api.ActionRegistry;
import at.tortmayr.chillisp.api.actions.Action;
import io.typefox.sprotty.server.json.EnumTypeAdapter;
import io.typefox.sprotty.server.json.PropertyBasedTypeAdapter;

public class ActionTypeAdapter extends PropertyBasedTypeAdapter<Action> {

	public static GsonBuilder configureGson(GsonBuilder gsonBuilder) {
		gsonBuilder.registerTypeAdapterFactory(new ActionTypeAdapter.Factory())
				.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
		return gsonBuilder;
	}

	public ActionTypeAdapter(Gson gson) {
		super(gson, "kind");
	}

	@Override
	protected Action createInstance(String kind) {
		Class<? extends Action> clazz = ActionRegistry.getInstance().getActionClass(kind);
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

		public Factory() {
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!Action.class.isAssignableFrom(typeToken.getRawType()))
				return null;
			return (TypeAdapter<T>) new ActionTypeAdapter(gson);
		}

	}

}
