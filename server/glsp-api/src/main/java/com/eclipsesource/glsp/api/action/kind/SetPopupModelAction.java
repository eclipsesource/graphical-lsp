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

import com.eclipsesource.glsp.api.action.Action;

import io.typefox.sprotty.api.Bounds;
import io.typefox.sprotty.api.SModelRoot;

public class SetPopupModelAction extends Action {
	private SModelRoot newRoot;
	private Bounds bounds;

	public SetPopupModelAction() {
		super(ActionKind.SET_POPUP_MODEL);
	}

	public SetPopupModelAction(SModelRoot newRoot, Bounds bounds) {
		this();
		this.newRoot = newRoot;
		this.bounds = bounds;
	}

	public SModelRoot getNewRoot() {
		return newRoot;
	}

	public Bounds getBounds() {
		return bounds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
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
		SetPopupModelAction other = (SetPopupModelAction) obj;
		if (bounds == null) {
			if (other.bounds != null)
				return false;
		} else if (!bounds.equals(other.bounds))
			return false;
		if (newRoot == null) {
			if (other.newRoot != null)
				return false;
		} else if (!newRoot.equals(other.newRoot))
			return false;
		return true;
	}


}
