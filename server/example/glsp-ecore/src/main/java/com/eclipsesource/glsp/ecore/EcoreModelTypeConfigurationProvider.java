/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.ecore;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sprotty.HtmlRoot;
import org.eclipse.sprotty.PreRenderedElement;
import org.eclipse.sprotty.SButton;
import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SGraph;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.provider.ModelTypeConfigurationProvider;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;
import com.eclipsesource.glsp.ecore.model.ClassNode;
import com.eclipsesource.glsp.ecore.model.EcoreEdge;
import com.eclipsesource.glsp.ecore.model.Icon;

public class EcoreModelTypeConfigurationProvider implements ModelTypeConfigurationProvider {

	@Override
	public List<EdgeTypeHint> getEdgeTypeHints() {
		return Collections.emptyList();
	}

	@Override
	public List<NodeTypeHint> getNodeTypeHints() {
		return Collections.emptyList();
	}

	@Override
	public Map<String, Class<? extends SModelElement>> getTypeToClassMappings() {
		return new HashMap<String, Class<? extends SModelElement>>() {
			private static final long serialVersionUID = 1L;
			{
				put("graph", SGraph.class);
				put("label:heading", SLabel.class);
				put("label:text", SLabel.class);
				put("comp:comp", SCompartment.class);
				put("comp:header", SCompartment.class);
				put("label:icon", SLabel.class);
				put("edge", SEdge.class);
				put("html", HtmlRoot.class);
				put("pre-rendered", PreRenderedElement.class);

				// needed?
				put("routing-point", SModelElement.class);
				put("volatile-routing-point", SModelElement.class);

				// additional ui stuff
				put("icon", Icon.class);
				put("button:expand", SButton.class);

				// ecore stuff
				put("node:class", ClassNode.class);
				put("edge:association", EcoreEdge.class);
				put("edge:inheritance", SEdge.class);
				put("edge:aggregation", EcoreEdge.class);
				put("edge:composition", EcoreEdge.class);
			}
		};
	}

}
