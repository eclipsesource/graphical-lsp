package com.eclipsesource.glsp.graph.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class EObjectExclusionStrategy implements ExclusionStrategy {

	private static final String EPACKAGE_NS = "org.eclipse.emf.ecore";

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getDeclaringClass().getPackage().getName().startsWith(EPACKAGE_NS);
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
