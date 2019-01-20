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
package com.eclipsesource.glsp.example.workflow.handler;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.example.workflow.schema.ModelTypes;

public class CreateAutomatedTaskHandler extends CreateTaskHandler {

	public CreateAutomatedTaskHandler() {
		super(ModelTypes.AUTOMATED_TASK, "automated", i -> "AutomatedTask" + i);
	}

	@Override
	public boolean handles(AbstractOperationAction execAction) {
		if (execAction instanceof CreateNodeOperationAction) {
			CreateNodeOperationAction action = (CreateNodeOperationAction) execAction;
			return ModelTypes.AUTOMATED_TASK.equals(action.getElementTypeId());
		}
		return false;
	}

}
