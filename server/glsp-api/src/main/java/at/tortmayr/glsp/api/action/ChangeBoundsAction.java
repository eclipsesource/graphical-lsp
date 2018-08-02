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
package at.tortmayr.glsp.api.action;

import java.util.Arrays;

import at.tortmayr.glsp.api.ActionKind;
import io.typefox.sprotty.api.ElementAndBounds;

public class ChangeBoundsAction extends Action {
	private ElementAndBounds[] newBounds;

	public ChangeBoundsAction() {
		super(ActionKind.CHANGE_BOUNDS);

	}

	public ChangeBoundsAction(ElementAndBounds[] newBounds) {
		this();
		this.newBounds = newBounds;
	}

	public ElementAndBounds[] getNewBounds() {
		return newBounds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(newBounds);
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
		ChangeBoundsAction other = (ChangeBoundsAction) obj;
		if (!Arrays.equals(newBounds, other.newBounds))
			return false;
		return true;
	}

}
