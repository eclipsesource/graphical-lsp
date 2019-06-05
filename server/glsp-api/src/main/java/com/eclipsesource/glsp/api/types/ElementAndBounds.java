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

import java.util.function.Consumer;

import com.eclipsesource.glsp.graph.GBounds;

public class ElementAndBounds {

	private String elementId;
	private GBounds newBounds;

	public ElementAndBounds() {
	}

	public ElementAndBounds(final Consumer<ElementAndBounds> initializer) {
		initializer.accept(this);
	}

	public String getElementId() {
		return this.elementId;
	}

	public void setElementId(final String elementId) {
		this.elementId = elementId;
	}

	public GBounds getNewBounds() {
		return this.newBounds;
	}

	public void setNewBounds(final GBounds newBounds) {
		this.newBounds = newBounds;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementAndBounds other = (ElementAndBounds) obj;
		if (this.elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!this.elementId.equals(other.elementId))
			return false;
		if (this.newBounds == null) {
			if (other.newBounds != null)
				return false;
		} else if (!this.newBounds.equals(other.newBounds))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.elementId == null) ? 0 : this.elementId.hashCode());
		return prime * result + ((this.newBounds == null) ? 0 : this.newBounds.hashCode());
	}

}
