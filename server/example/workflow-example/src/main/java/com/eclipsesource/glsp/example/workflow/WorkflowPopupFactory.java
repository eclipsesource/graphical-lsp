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

import static com.eclipsesource.glsp.graph.util.GraphUtil.bounds;

import java.util.Arrays;
import java.util.Optional;

import com.eclipsesource.glsp.api.action.kind.RequestPopupModelAction;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode;
import com.eclipsesource.glsp.graph.GBounds;
import com.eclipsesource.glsp.graph.GHtmlRoot;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GPreRenderedElement;
import com.eclipsesource.glsp.graph.GraphFactory;

public class WorkflowPopupFactory implements PopupModelFactory {

	private String generateTitle(TaskNode task) {
		return task.getName();
	}

	private String generateBody(TaskNode task) {
		return String.format(NL + "Type: %s" + NL + "Duration: %s" + NL + " Reference: %s" + NL, task.getTaskType(),
				task.getDuration(), task.getReference());
	}

	private static final String NL = "<br>";

	@Override
	public Optional<GHtmlRoot> createPopupModel(GModelElement element, RequestPopupModelAction action, GraphicalModelState modelState) {
		if (element != null && element instanceof TaskNode) {
			TaskNode task = (TaskNode) element;
			GHtmlRoot root = GraphFactory.eINSTANCE.createGHtmlRoot();
			GBounds bounds = action.getBounds();
			root.setCanvasBounds(bounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
			root.setType("html");
			root.setId("sprotty-popup");
			GPreRenderedElement p1 = GraphFactory.eINSTANCE.createGPreRenderedElement();
			p1.setType("pre-rendered");
			p1.setId("popup-title");
			p1.setCode("<div class=\"sprotty-popup-title\">" + generateTitle(task) + "</div>");

			GPreRenderedElement p2 = GraphFactory.eINSTANCE.createGPreRenderedElement();
			p2.setType("pre-rendered");
			p2.setId("popup-body");
			p2.setCode("<div class=\"sprotty-popup-body\">" + generateBody(task) + "</div>");
			root.getChildren().addAll(Arrays.asList(p1, p2));
			return Optional.of(root);
		}
		return Optional.empty();

	}

}
