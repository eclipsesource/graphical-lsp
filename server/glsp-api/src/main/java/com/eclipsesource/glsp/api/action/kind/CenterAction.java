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

public class CenterAction extends Action {

	private String[] elementIDs;
	private boolean animate = true;

	public CenterAction() {
		super(ActionKind.CENTER);
	}

	public CenterAction(String[] elementIDs, boolean animate) {
		this();
		this.elementIDs = elementIDs;
		this.animate = animate;
	}

	public String[] getElementIDs() {
		return elementIDs;
	}

	public boolean isAnimate() {
		return animate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (animate ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(elementIDs);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CenterAction other = (CenterAction) obj;
		if (animate != other.animate)
			return false;
		if (!Arrays.equals(elementIDs, other.elementIDs))
			return false;
		return true;
	}

}
