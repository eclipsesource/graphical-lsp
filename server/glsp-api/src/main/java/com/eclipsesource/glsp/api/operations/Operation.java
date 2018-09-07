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
package com.eclipsesource.glsp.api.operations;

public class Operation {
	
	private String label;
	private String elementTypeId;
	private String operationKind;

	public Operation() {
	}

	public Operation(String label, String elementTypeId, String operationKind) {
		super();
		this.label = label;
		this.elementTypeId = elementTypeId;
		this.operationKind = operationKind;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(String elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public String getOperationKind() {
		return operationKind;
	}

	public void setOperationKind(String operationKind) {
		this.operationKind = operationKind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementTypeId == null) ? 0 : elementTypeId.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((operationKind == null) ? 0 : operationKind.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (elementTypeId == null) {
			if (other.elementTypeId != null)
				return false;
		} else if (!elementTypeId.equals(other.elementTypeId))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (operationKind == null) {
			if (other.operationKind != null)
				return false;
		} else if (!operationKind.equals(other.operationKind))
			return false;
		return true;
	}

}
