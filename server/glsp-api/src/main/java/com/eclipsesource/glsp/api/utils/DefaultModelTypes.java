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
package com.eclipsesource.glsp.api.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.sprotty.HtmlRoot;
import org.eclipse.sprotty.PreRenderedElement;
import org.eclipse.sprotty.SButton;
import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SGraph;
import org.eclipse.sprotty.SIssueMarker;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SNode;
import org.eclipse.sprotty.SPort;

public final class DefaultModelTypes {
	private DefaultModelTypes() {
	};

	public static final String GRAPH = "graph";
	public static final String NODE = "node";
	public static final String EDGE = "edge";
	public static final String PORT = "port";
	public static final String LABEL = "label";
	public static final String COMPARTMENT = "comp";
	public static final String BUTTON = "button";
	public static final String ISSUE_MARKER = "marker";

	public static final String HTML = "html";
	public static final String PRE_RENDERED = "pre-rendered";

	public static Map<String, Class<? extends SModelElement>> getDefaultTypeMappings() {
		Map<String, Class<? extends SModelElement>> mapping = new HashMap<>();
		mapping.put(GRAPH, SGraph.class);
		mapping.put(NODE, SNode.class);
		mapping.put(EDGE, SEdge.class);
		mapping.put(PORT, SPort.class);
		mapping.put(LABEL, SLabel.class);
		mapping.put(COMPARTMENT, SCompartment.class);
		mapping.put(BUTTON, SButton.class);
		mapping.put(ISSUE_MARKER, SIssueMarker.class);

		mapping.put(HTML, HtmlRoot.class);
		mapping.put(PRE_RENDERED, PreRenderedElement.class);

		return mapping;
	}
}
