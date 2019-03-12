package com.eclipsesource.glsp.ecore.model;

public final class ModelTypes {
	private ModelTypes() {
	}
	public static final String REFERENCE="edge:association";
	public static final String ENUM="node:enum";
	public static final String ATTRIBUTE="label:prop:attr";
	public static final String ECLASS="node:class";
	public static final String ABSTRACT="node:class:abstract";
	public static final String INTERFACE="node:class:interface";
	public static final String COMPOSITION="edge:composition";
	public static final String INHERITANCE= "edge:inheritance";
	public static final String ENUMLITERAL = "label:prop:enum";
}
