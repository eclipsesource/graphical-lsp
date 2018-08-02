/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.chillisp.api.action;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionKind;
import at.tortmayr.chillisp.api.utils.Match;
import io.typefox.sprotty.api.SModelRoot;

public class UpdateModelAction extends Action {
	private SModelRoot newRoot;
	private Match[] machtes;
	private boolean animate=true;

	public UpdateModelAction() {
		super(ActionKind.UPDATE_MODEL);
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
