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

import java.util.List;

import com.eclipsesource.glsp.api.action.Action;

public class DeleteOperationAction extends AbstractOperationAction {

	private List<String> elementIds;

	public DeleteOperationAction() {
		super(Action.Kind.DELETE_ELEMENT_OPERATION);
	}

	public DeleteOperationAction(List<String> elementIds) {
		this();
		this.elementIds = elementIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((elementIds == null) ? 0 : elementIds.hashCode());
		return result;
	}

	public List<String> getElementIds() {
		return elementIds;
	}

	public void setElementIds(List<String> elementIds) {
		this.elementIds = elementIds;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeleteOperationAction other = (DeleteOperationAction) obj;
		if (elementIds == null) {
			if (other.elementIds != null)
				return false;
		} else if (!elementIds.equals(other.elementIds))
			return false;
		return true;
	}

}
