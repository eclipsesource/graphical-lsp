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

public class SelectAction extends Action {
	private String[] selectedElementsIDs;
	private String[] deselectedElementsIDs;

	public SelectAction() {
		super(Action.Kind.SELECT);
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
