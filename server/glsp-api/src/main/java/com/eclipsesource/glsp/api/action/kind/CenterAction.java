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
package com.eclipsesource.glsp.api.action.kind;

import java.util.Arrays;

import com.eclipsesource.glsp.api.action.Action;

public class CenterAction extends Action {

	private String[] elementIDs;
	private boolean animate = true;

	public CenterAction() {
		super(Action.Kind.CENTER);
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
