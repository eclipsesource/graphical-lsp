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
package at.tortmayr.glsp.api.action.kind;

import at.tortmayr.glsp.api.action.Action;
import at.tortmayr.glsp.api.operations.OperationKind;

public class ExecuteOperationAction extends Action {
	
	private String operationKind;
	private String operationId;
	
	public ExecuteOperationAction() {
		super(ActionKind.EXECUTE_OPERATION);
		this.operationKind = OperationKind.GENERIC;
	}

	public ExecuteOperationAction(String operationId) {
		this();
		this.operationId = operationId;
	}

	public String getOperationKind() {
		return operationKind;
	}

	public void setOperationKind(String operationKind) {
		this.operationKind = operationKind;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operationId == null) ? 0 : operationId.hashCode());
		result = prime * result + ((operationKind == null) ? 0 : operationKind.hashCode());
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
		ExecuteOperationAction other = (ExecuteOperationAction) obj;
		if (operationId == null) {
			if (other.operationId != null)
				return false;
		} else if (!operationId.equals(other.operationId))
			return false;
		if (operationKind == null) {
			if (other.operationKind != null)
				return false;
		} else if (!operationKind.equals(other.operationKind))
			return false;
		return true;
	}

}
