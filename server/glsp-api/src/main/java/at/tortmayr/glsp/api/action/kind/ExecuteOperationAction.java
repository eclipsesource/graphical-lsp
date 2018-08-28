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

public abstract class ExecuteOperationAction extends Action {
	
	private String operationKind;

	public ExecuteOperationAction(String operationKind) {
		super(ActionKind.EXECUTE_OPERATION + "_" + operationKind);
		this.operationKind = operationKind;
	}

	public String getOperationKind() {
		return operationKind;
	}

	public void setOperationKind(String operationKind) {
		this.operationKind = operationKind;
	}

}
