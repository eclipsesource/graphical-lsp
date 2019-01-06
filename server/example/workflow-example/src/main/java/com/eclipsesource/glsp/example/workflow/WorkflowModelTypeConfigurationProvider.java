/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sprotty.HtmlRoot;
import org.eclipse.sprotty.PreRenderedElement;
import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SGraph;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.provider.ModelTypeConfigurationProvider;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;
import com.eclipsesource.glsp.example.workflow.schema.ActivityNode;
import com.eclipsesource.glsp.example.workflow.schema.Icon;
import com.eclipsesource.glsp.example.workflow.schema.TaskNode;
import com.eclipsesource.glsp.example.workflow.schema.WeightedEdge;

public class WorkflowModelTypeConfigurationProvider implements ModelTypeConfigurationProvider {

	@Override
	public Map<String, Class<? extends SModelElement>> getTypeToClassMappings() {
		Map<String, Class<? extends SModelElement>> mapping = new HashMap<>();
		mapping.put("graph", SGraph.class);
		mapping.put("label:heading", SLabel.class);
		mapping.put("label:text", SLabel.class);
		mapping.put("comp:comp", SCompartment.class);
		mapping.put("comp:header", SCompartment.class);
		mapping.put("label:icon", SLabel.class);
		mapping.put("edge", SEdge.class);
		mapping.put("html", HtmlRoot.class);
		mapping.put("pre-rendered", PreRenderedElement.class);
		mapping.put(WeightedEdge.TYPE, WeightedEdge.class);
		mapping.put(Icon.TYPE, Icon.class);
		mapping.put(ActivityNode.TYPE, ActivityNode.class);
		mapping.put(TaskNode.TYPE, TaskNode.class);
		return mapping;
	}

	@Override
	public List<NodeTypeHint> getNodeTypeHints() {
		return Arrays.asList(createDefaultNodeTypeHint(ActivityNode.TYPE), createDefaultNodeTypeHint(TaskNode.TYPE));
	}

	@Override
	public EdgeTypeHint createDefaultEdgeTypeHint(String elementId) {
		EdgeTypeHint hint = ModelTypeConfigurationProvider.super.createDefaultEdgeTypeHint(elementId);
		hint.setSourceElementTypeIds(Arrays.asList(TaskNode.TYPE, ActivityNode.TYPE));
		hint.setTargetElementTypeIds(Arrays.asList(TaskNode.TYPE, ActivityNode.TYPE));
		return hint;
	}

	@Override
	public List<EdgeTypeHint> getEdgeTypeHints() {
		return Arrays.asList(createDefaultEdgeTypeHint(WeightedEdge.TYPE), createDefaultEdgeTypeHint("edge"));
	}
}
