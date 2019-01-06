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
package com.eclipsesource.glsp.api.types;

public abstract class ElementTypeHint {

	private String elementTypeId;
	private boolean repositionable;
	private boolean deletable;

	public ElementTypeHint() {
	}

	public ElementTypeHint(String elementTypeId, boolean repositionable, boolean deletable) {
		super();
		this.elementTypeId = elementTypeId;
		this.repositionable = repositionable;
		this.deletable = deletable;
	}

	public String getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(String elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public boolean isRepositionable() {
		return repositionable;
	}

	public void setRepositionable(boolean repositionable) {
		this.repositionable = repositionable;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

}
