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
package com.eclipsesource.glsp.api.action.kind;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.Action;

public class RequestBoundsAction extends Action {
	private SModelRoot newRoot;

	public RequestBoundsAction() {
		super(Action.Kind.REQUEST_BOUNDS);
	}

	public RequestBoundsAction(SModelRoot newRoot) {
		this();
		this.newRoot = newRoot;
	}

	public SModelRoot getNewRoot() {
		return newRoot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((newRoot == null) ? 0 : newRoot.hashCode());
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
		RequestBoundsAction other = (RequestBoundsAction) obj;
		if (newRoot == null) {
			if (other.newRoot != null)
				return false;
		} else if (!newRoot.equals(other.newRoot))
			return false;
		return true;
	}

}
