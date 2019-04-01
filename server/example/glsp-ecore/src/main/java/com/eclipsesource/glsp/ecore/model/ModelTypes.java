package com.eclipsesource.glsp.ecore.model;

public final class ModelTypes {
	private ModelTypes() {
	}
	public static final String REFERENCE="edge:reference";
	public static final String ENUM="node:enum";
	public static final String ATTRIBUTE="node:attribute";
	public static final String OPERATION = "node:operation";
	public static final String ECLASS="node:class";
	public static final String ABSTRACT="node:class:abstract";
	public static final String DATATYPE="node:datatype";
	public static final String INTERFACE="node:class:interface";
	public static final String COMPOSITION="edge:composition";
	public static final String INHERITANCE= "edge:inheritance";
	public static final String ENUMLITERAL = "node:enumliteral";
}
