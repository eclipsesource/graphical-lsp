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

public class SelectAction extends Action {
	private String[] selectedElementsIDs;
	private String[] deselectedElementsIDs;

	public SelectAction() {
		super(ActionKind.SELECT);
	}

	public SelectAction(String[] selectedElementsIDs, String[] deselectedElementsIDs) {
		this();
		this.selectedElementsIDs = selectedElementsIDs;
		this.deselectedElementsIDs = deselectedElementsIDs;
	}

	public String[] getSelectedElementsIDs() {
		return selectedElementsIDs;
	}

	public String[] getDeselectedElementsIDs() {
		return deselectedElementsIDs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(deselectedElementsIDs);
		result = prime * result + Arrays.hashCode(selectedElementsIDs);
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
		SelectAction other = (SelectAction) obj;
		if (!Arrays.equals(deselectedElementsIDs, other.deselectedElementsIDs))
			return false;
		if (!Arrays.equals(selectedElementsIDs, other.selectedElementsIDs))
			return false;
		return true;
	}

}
