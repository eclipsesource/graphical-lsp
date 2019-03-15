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
package com.eclipsesource.glsp.api.model;

import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.types.ElementTypeHint;

public class ModelTypeConfiguration {

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
