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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.sprotty.HtmlRoot;
import org.eclipse.sprotty.PreRenderedElement;
import org.eclipse.sprotty.SCompartment;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SGraph;
import org.eclipse.sprotty.SLabel;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.model.ModelTypeConfiguration;
import com.eclipsesource.glsp.example.workflow.schema.ActivityNode;
import com.eclipsesource.glsp.example.workflow.schema.Icon;
import com.eclipsesource.glsp.example.workflow.schema.TaskNode;
import com.eclipsesource.glsp.example.workflow.schema.WeightedEdge;



public class WorkflowModelTypeConfiguration implements ModelTypeConfiguration {

	@Override
	public Map<String, Class<? extends SModelElement>> getModelTypes() {
		Map<String, Class<? extends SModelElement>> conf = new HashMap<>();
		conf.put("graph", SGraph.class);
		conf.put("label:heading", SLabel.class);
		conf.put("label:text", SLabel.class);
		conf.put("comp:comp", SCompartment.class);
		conf.put("comp:header", SCompartment.class);
		conf.put("label:icon", SLabel.class);
		conf.put("edge", SEdge.class);
		conf.put("html", HtmlRoot.class);
		conf.put("pre-rendered", PreRenderedElement.class);
		conf.put(WeightedEdge.TYPE, WeightedEdge.class);
		conf.put(Icon.TYPE, Icon.class);
		conf.put(ActivityNode.TYPE, ActivityNode.class);
		conf.put(TaskNode.TYPE, TaskNode.class);
		return conf;

	}

}
