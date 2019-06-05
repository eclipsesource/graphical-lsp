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

import static com.eclipsesource.glsp.graph.DefaultTypes.EDGE;
import static com.eclipsesource.glsp.graph.DefaultTypes.GRAPH;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;

import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphPackage;
import com.eclipsesource.glsp.graph.GraphPackage;

public final class ModelTypes {

	private ModelTypes() {
	}

	public static final String LABEL_HEADING = "label:heading";
	public static final String LABEL_TEXT = "label:text";
	public static final String COMP_HEADER = "comp:header";
	public static final String LABEL_ICON = "label:icon";
	public static final String WEIGHTED_EDGE = "edge:weighted";
	public static final String ICON = "icon";
	public static final String DECISION_NODE = "activityNode:decision";
	public static final String MERGE_NODE = "activityNode:merge";
	public static final String MANUAL_TASK = "task:manual";
	public static final String AUTOMATED_TASK = "task:automated";

	public static final Map<String, EClass> TYPE_MAP;
	static {
		Map<String, EClass> map = new HashMap<>();
		map.put(GRAPH, GraphPackage.Literals.GGRAPH);
		map.put(EDGE, GraphPackage.Literals.GEDGE);
		map.put(LABEL_HEADING, WfgraphPackage.Literals.LABEL_HEADING);
		map.put(LABEL_TEXT, WfgraphPackage.Literals.LABEL_TEXT);
		map.put(LABEL_ICON, WfgraphPackage.Literals.LABEL_ICON);
		map.put(WEIGHTED_EDGE, WfgraphPackage.Literals.WEIGHTED_EDGE);
		map.put(ICON, WfgraphPackage.Literals.ICON);
		map.put(DECISION_NODE, WfgraphPackage.Literals.DECISION_NODE);
		map.put(MERGE_NODE, WfgraphPackage.Literals.MERGE_NODE);
		map.put(MANUAL_TASK, WfgraphPackage.Literals.MANUAL_TASK);
		map.put(AUTOMATED_TASK, WfgraphPackage.Literals.AUTOMATED_TASK);
		map.put(COMP_HEADER, GraphPackage.Literals.GCOMPARTMENT);
		TYPE_MAP = Collections.unmodifiableMap(map);
	}
}
