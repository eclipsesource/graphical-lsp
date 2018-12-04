/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.action.kind;

import java.util.Arrays;

import com.eclipsesource.glsp.api.action.Action;

public class DeleteElementOperationAction extends AbstractOperationAction {
	
	private String[] elementIds;
	
	public DeleteElementOperationAction() {
		super(Action.Kind.DELETE_ELEMENT_OPERATION);
	}
	
	public DeleteElementOperationAction(String[] elementIds) {
		this();
		this.elementIds = elementIds;
	}

	public String[] getElementIds() {
		return elementIds;
	}

	public void setElementIds(String[] elementIds) {
		this.elementIds = elementIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(elementIds);
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
		DeleteElementOperationAction other = (DeleteElementOperationAction) obj;
		if (!Arrays.equals(elementIds, other.elementIds))
			return false;
		return true;
	}



}
