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

import static com.eclipsesource.glsp.ecore.model.ModelTypes.ABSTRACT;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.ATTRIBUTE;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.COMPOSITION;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.DATATYPE;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.ECLASS;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.ENUM;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.ENUMLITERAL;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.INHERITANCE;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.INTERFACE;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.OPERATION;
import static com.eclipsesource.glsp.ecore.model.ModelTypes.REFERENCE;

import java.util.ArrayList;
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
import org.eclipse.sprotty.SNode;

import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;
import com.eclipsesource.glsp.ecore.model.ClassNode;
import com.eclipsesource.glsp.ecore.model.Icon;
import com.eclipsesource.glsp.server.model.GLSPGraph;

public class EcoreModelTypeConfigurationProvider implements IModelTypeConfigurationProvider {

	@Override
	public List<EdgeTypeHint> getEdgeTypeHints() {
		return Arrays.asList(createDefaultEdgeTypeHint(REFERENCE), createDefaultEdgeTypeHint(INHERITANCE),
				createDefaultEdgeTypeHint(COMPOSITION));
	}

	@Override
	public List<NodeTypeHint> getNodeTypeHints() {
		List<NodeTypeHint> hints = new ArrayList<>();
		hints.add(createDefaultNodeTypeHint(ECLASS));
		hints.add(createDefaultNodeTypeHint(COMPOSITION));

		NodeTypeHint enumHint = createDefaultNodeTypeHint(ENUM);
		enumHint.setContainableElementTypeIds(Arrays.asList(ENUMLITERAL));
		NodeTypeHint dataTypeHint=createDefaultNodeTypeHint(DATATYPE);
		dataTypeHint.setContainableElementTypeIds(null);
		hints.add(dataTypeHint);
		hints.add(enumHint);
		NodeTypeHint rootTypeHint = createDefaultNodeTypeHint("graph");
		rootTypeHint.setContainableElementTypeIds(Arrays.asList(ECLASS, ABSTRACT, INTERFACE, COMPOSITION, ENUM,DATATYPE));
		rootTypeHint.setDeletable(false);
		rootTypeHint.setReparentable(false);
		rootTypeHint.setRepositionable(false);
		hints.add(rootTypeHint);
		NodeTypeHint attributeNodeHint= new NodeTypeHint(ATTRIBUTE,false,true,false,true);
		NodeTypeHint operationNodeHint= new NodeTypeHint(OPERATION,false,true,false,true);
		NodeTypeHint literalNodeHint= new NodeTypeHint(ENUMLITERAL,false,true,false,true);
		hints.add(attributeNodeHint);
		hints.add(literalNodeHint);
		hints.add(operationNodeHint);
		return hints;
	}

	public NodeTypeHint createDefaultNodeTypeHint(String elementId) {
		return new NodeTypeHint(elementId, true, true, true, false, Arrays.asList(ATTRIBUTE, OPERATION));
	}

	@Override
	public EdgeTypeHint createDefaultEdgeTypeHint(String elementId) {
		List<String> allowed = Arrays.asList(ECLASS, COMPOSITION);
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

				// additional ui stuff
				put("icon", Icon.class);
				put("button:expand", SButton.class);

				// ecore stuff
				put(ECLASS, ClassNode.class);
				put(REFERENCE, SEdge.class);
				put(INHERITANCE, SEdge.class);
				put(COMPOSITION, SEdge.class);
				put(ENUM, ClassNode.class);
				put(ATTRIBUTE, SNode.class);
				put(OPERATION, SNode.class);
				put(ENUMLITERAL, SNode.class);
				put(DATATYPE, ClassNode.class);
			}
		};
	}

}
