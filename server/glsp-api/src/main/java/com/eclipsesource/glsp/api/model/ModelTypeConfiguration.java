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
package com.eclipsesource.glsp.api.model;

import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.types.ElementTypeHint;

public  class ModelTypeConfiguration {

	private String elementTypeId;
	private Class<? extends SModelElement> javaClassRepresentation;
	private ElementTypeHint elementTypeHint;

	public ModelTypeConfiguration() {
	}

	public ModelTypeConfiguration(String elementTypeId, Class<? extends SModelElement> javaClassRepresentation,
			ElementTypeHint elementTypeHint) {
		super();
		this.elementTypeId = elementTypeId;
		this.javaClassRepresentation = javaClassRepresentation;
		this.elementTypeHint = elementTypeHint;
	}

	public String getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(String elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public Class<? extends SModelElement> getJavaClassRepresentation() {
		return javaClassRepresentation;
	}

	public void setJavaClassRepresentation(Class<? extends SModelElement> javaClassRepresentation) {
		this.javaClassRepresentation = javaClassRepresentation;
	}

	public ElementTypeHint getElementTypeHint() {
		return elementTypeHint;
	}

	public void setElementTypeHint(ElementTypeHint elementTypeHint) {
		this.elementTypeHint = elementTypeHint;
	}

}
