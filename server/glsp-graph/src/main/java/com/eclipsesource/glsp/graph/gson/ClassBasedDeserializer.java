package com.eclipsesource.glsp.graph.gson;

import java.lang.reflect.Type;

import org.eclipse.emf.ecore.EObject;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ClassBasedDeserializer implements JsonDeserializer<EObject> {

	private Class<? extends EObject> clazz;

	public ClassBasedDeserializer(Class<? extends EObject> clazz) {
		this.clazz = clazz;
	}

	@Override
	public EObject deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		return context.deserialize(json, clazz);
	}

}
