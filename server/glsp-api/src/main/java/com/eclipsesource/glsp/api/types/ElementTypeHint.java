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
