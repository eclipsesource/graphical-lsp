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
package com.eclipsesource.glsp.example.workflow;

import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.AUTOMATED_TASK;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.DECISION_NODE;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.ICON;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.LABEL_HEADING;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.LABEL_ICON;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.LABEL_TEXT;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.MANUAL_TASK;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.MERGE_NODE;
import static com.eclipsesource.glsp.example.workflow.schema.ModelTypes.WEIGHTED_EDGE;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;

import com.eclipsesource.glsp.api.factory.GraphGsonConfiguratorFactory;
import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphPackage;
import com.eclipsesource.glsp.graph.GraphPackage;
import com.eclipsesource.glsp.graph.gson.GGraphGsonConfigurator;

public class WorkflowGsonConfiguratorFactory implements GraphGsonConfiguratorFactory {

	public GGraphGsonConfigurator create() {
		Map<String, EClass> types = new HashMap<>();
		types.put("graph", GraphPackage.Literals.GGRAPH);
		types.put("edge", GraphPackage.Literals.GEDGE);
		types.put(LABEL_HEADING, WfgraphPackage.Literals.LABEL_HEADING);
		types.put(LABEL_TEXT, WfgraphPackage.Literals.LABEL_TEXT);
		types.put(LABEL_ICON, WfgraphPackage.Literals.LABEL_ICON);
		types.put(WEIGHTED_EDGE, WfgraphPackage.Literals.WEIGHTED_EDGE);
		types.put(ICON, WfgraphPackage.Literals.ICON);
		types.put(DECISION_NODE, WfgraphPackage.Literals.DECISION_NODE);
		types.put(MERGE_NODE, WfgraphPackage.Literals.MERGE_NODE);
		types.put(MANUAL_TASK, WfgraphPackage.Literals.MANUAL_TASK);
		types.put(AUTOMATED_TASK, WfgraphPackage.Literals.AUTOMATED_TASK);
		types.put("comp:header", GraphPackage.Literals.GCOMPARTMENT);
		return new GGraphGsonConfigurator().withEPackages(WfgraphPackage.eINSTANCE).withTypes(types);
	}

}
