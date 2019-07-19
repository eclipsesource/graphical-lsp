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
package com.eclipsesource.glsp.example.modelserver.workflow.model;

import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.ClientOptions;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.eclipsesource.glsp.graph.GraphFactory;

public class WorkflowModelServerModelFactory implements ModelFactory {

	@Override
	public GModelRoot loadModel(RequestModelAction action, GraphicalModelState modelState) {
		WorkflowModelServerAccess modelAccess = new WorkflowModelServerAccess(
				action.getOptions().get(ClientOptions.SOURCE_URI));
		WorkflowFacade workflowFacade = modelAccess.getWorkflowFacade();
		GModelRoot modelRoot = GraphFactory.eINSTANCE.createGModelRoot();
		// TODO transform to GModel based on workflow facade
		return modelRoot;
	}

}
