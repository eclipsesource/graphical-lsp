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
package com.eclipsesource.glsp.example.workflow.handler;

import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.action.kind.ExecuteOperationAction;
import com.eclipsesource.glsp.example.workflow.WorkflowOperationConfiguration;

public class CreateAutomatedTaskHandler extends CreateTaskHandler {
	
	public CreateAutomatedTaskHandler() {
		super("automated", i -> "AutomatedTask" + i);
	}

	@Override
	public boolean handles(ExecuteOperationAction execAction) {
		if (execAction instanceof CreateNodeOperationAction) {
			CreateNodeOperationAction action = (CreateNodeOperationAction) execAction;
			return WorkflowOperationConfiguration.AUTOMATED_TASK_ID.equals(action.getElementId());
		}
		return false;
	}

}
