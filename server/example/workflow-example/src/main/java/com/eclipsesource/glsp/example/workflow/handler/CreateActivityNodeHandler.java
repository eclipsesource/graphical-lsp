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
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.Optional;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.utils.ModelTypes;
import com.eclipsesource.glsp.example.workflow.utils.WorkflowBuilder.ActivityNodeBuilder;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.server.operationhandler.CreateNodeOperationHandler;

public class CreateActivityNodeHandler extends CreateNodeOperationHandler {

	public CreateActivityNodeHandler(String elementTypeId) {
		super(elementTypeId);
	}

	@Override
	protected GNode createNode(Optional<GPoint> point, GraphicalModelState modelState) {
		String nodeType = ModelTypes.toNodeType(elementTypeId);
		return new ActivityNodeBuilder(elementTypeId, nodeType) //
				.position(point.orElse(null)) //
				.build();
	}

}
