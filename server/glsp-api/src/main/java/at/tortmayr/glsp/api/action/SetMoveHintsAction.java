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
import at.tortmayr.glsp.api.utils.DragAndDropHint;

public class SetMoveHintsAction extends Action {

	private DragAndDropHint[] hints;

	public SetMoveHintsAction() {
		super(ActionKind.SET_MOVE_HINTS);
	}

	public SetMoveHintsAction(DragAndDropHint[] hints) {
		this();
		this.hints = hints;
	}

	public DragAndDropHint[] getHints() {
		return hints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(hints);
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
		SetMoveHintsAction other = (SetMoveHintsAction) obj;
		if (!Arrays.equals(hints, other.hints))
			return false;
		return true;
	}

}
