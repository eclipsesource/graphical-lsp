/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.action.kind;

import java.util.Arrays;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.operations.Operation;

public class SetOperationsAction extends Action {
	
	private Operation[] operations;

	public SetOperationsAction() {
		super(ActionKind.SET_OPERATIONS);
	}

	public SetOperationsAction(Operation[] tools) {
		this();
		this.operations = tools;
	}

	public Operation[] getOperations() {
		return operations;
	}

	public void setOperations(Operation[] operations) {
		this.operations = operations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(operations);
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
		SetOperationsAction other = (SetOperationsAction) obj;
		if (!Arrays.equals(operations, other.operations))
			return false;
		return true;
	}

}
