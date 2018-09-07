/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.action.kind;

import java.util.Arrays;

import com.eclipsesource.glsp.api.action.Action;

import io.typefox.sprotty.api.ElementAndBounds;

public class SetBoundsAction extends Action {
	private ElementAndBounds[] bounds;

	public SetBoundsAction() {
		super(ActionKind.SET_BOUNDS);
	}

	public SetBoundsAction(ElementAndBounds[] bounds) {
		this();
		this.bounds = bounds;
	}

	public ElementAndBounds[] getBounds() {
		return bounds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(bounds);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetBoundsAction other = (SetBoundsAction) obj;
		if (!Arrays.equals(bounds, other.bounds))
			return false;
		return true;
	}

}
