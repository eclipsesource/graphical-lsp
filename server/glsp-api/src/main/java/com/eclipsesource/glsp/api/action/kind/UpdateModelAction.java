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

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.types.Match;

public class UpdateModelAction extends Action {
	private SModelRoot newRoot;
	private Match[] machtes;
	private boolean animate=true;

	public UpdateModelAction() {
		super(Action.Kind.UPDATE_MODEL);
	}

	public UpdateModelAction(SModelRoot newRoot, Match[] machtes, boolean animate) {
		this();
		this.newRoot = newRoot;
		this.machtes = machtes;
		this.animate = animate;
	}

	public SModelRoot getNewRoot() {
		return newRoot;
	}

	public Match[] getMachtes() {
		return machtes;
	}

	public boolean isAnimate() {
		return animate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (animate ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(machtes);
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
		UpdateModelAction other = (UpdateModelAction) obj;
		if (animate != other.animate)
			return false;
		if (!Arrays.equals(machtes, other.machtes))
			return false;
		if (newRoot == null) {
			if (other.newRoot != null)
				return false;
		} else if (!newRoot.equals(other.newRoot))
			return false;
		return true;
	}

}
