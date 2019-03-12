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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sprotty.HtmlRoot;
import org.eclipse.sprotty.PreRenderedElement;
import org.eclipse.sprotty.SButton;
import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;
import com.eclipsesource.glsp.ecore.model.ClassNode;
import com.eclipsesource.glsp.ecore.model.EcoreEdge;
import com.eclipsesource.glsp.ecore.model.Icon;
import com.eclipsesource.glsp.ecore.model.ModelTypes;
import com.eclipsesource.glsp.server.model.GLSPGraph;

public class EcoreModelTypeConfigurationProvider implements IModelTypeConfigurationProvider {

	@Override
	public List<EdgeTypeHint> getEdgeTypeHints() {
		return Arrays.asList(createDefaultEdgeTypeHint(ModelTypes.REFERENCE),
				createDefaultEdgeTypeHint(ModelTypes.INHERITANCE), createDefaultEdgeTypeHint(ModelTypes.COMPOSITION));
	}

	@Override
	public List<NodeTypeHint> getNodeTypeHints() {
		return Arrays.asList(createDefaultNodeTypeHint(ModelTypes.ECLASS),
				createDefaultNodeTypeHint(ModelTypes.COMPOSITION), createDefaultNodeTypeHint(ModelTypes.ENUM));
	}

	@Override
	public EdgeTypeHint createDefaultEdgeTypeHint(String elementId) {
		List<String> allowed = Arrays.asList(ModelTypes.ECLASS, ModelTypes.COMPOSITION);
		return new EdgeTypeHint(elementId, true, true, true, allowed, allowed);
	}

	@Override
	public Map<String, Class<? extends SModelElement>> getTypeToClassMappings() {
		return new HashMap<String, Class<? extends SModelElement>>() {
			private static final long serialVersionUID = 1L;
			{
				put("graph", GLSPGraph.class);
				put("label:heading", SLabel.class);
				put("label:text", SLabel.class);
				put("label:prop:attr", SLabel.class);
				put("label:prop:enum", SLabel.class);
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
				put(ModelTypes.ECLASS, ClassNode.class);
				put(ModelTypes.REFERENCE, EcoreEdge.class);
				put(ModelTypes.INHERITANCE, SEdge.class);
				put(ModelTypes.COMPOSITION, EcoreEdge.class);
				put(ModelTypes.ENUM, ClassNode.class);
			}
		};
	}

}
