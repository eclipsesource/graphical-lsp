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

import java.util.Arrays;

import org.eclipse.sprotty.ElementAndBounds;

public class ChangeBoundsOperationAction extends ExecuteOperationAction {
	private ElementAndBounds[] newBounds;

	public ChangeBoundsOperationAction() {
		super(ActionKind.CHANGE_BOUNDS);

	}

	public ChangeBoundsOperationAction(ElementAndBounds[] newBounds) {
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
		ChangeBoundsOperationAction other = (ChangeBoundsOperationAction) obj;
		if (!Arrays.equals(newBounds, other.newBounds))
			return false;
		return true;
	}

}
