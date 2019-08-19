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
package com.eclipsesource.glsp.example.workflow.layout;

import com.eclipsesource.glsp.graph.GGraph;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.eclipsesource.glsp.layout.ElkLayoutEngine;
import com.eclipsesource.glsp.layout.GLSPLayoutConfigurator;

public class WorkflowLayoutEngine extends ElkLayoutEngine {
	@Override
	public void layout(GModelRoot root) {
		if (root instanceof GGraph) {
			GLSPLayoutConfigurator configurator = new GLSPLayoutConfigurator();
			configurator.configureByType("graph");
			this.layout((GGraph) root, configurator);
		}
	}

}
