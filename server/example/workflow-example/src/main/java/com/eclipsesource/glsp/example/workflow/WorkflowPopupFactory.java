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

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.sprotty.HtmlRoot;
import org.eclipse.sprotty.PreRenderedElement;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.kind.RequestPopupModelAction;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.example.workflow.schema.TaskNode;

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
	public SModelRoot createPopuModel(SModelElement element, RequestPopupModelAction action) {
		if (element != null && element.getType().startsWith("task:")) {
			TaskNode task = (TaskNode) element;
			HtmlRoot root = new HtmlRoot();
			root.setCanvasBounds(action.getBounds());
			root.setType("html");
			root.setId("sprotty-popup");
			root.setChildren(new ArrayList<SModelElement>());
			PreRenderedElement p1 = new PreRenderedElement();
			p1.setType("pre-rendered");
			p1.setId("popup-title");
			p1.setCode("<div class=\"sprotty-popup-title\">" + generateTitle(task) + "</div>");

			PreRenderedElement p2 = new PreRenderedElement();
			p2.setType("pre-rendered");
			p2.setId("popup-body");
			p2.setCode("<div class=\"sprotty-popup-body\">" + generateBody(task) + "</div>");
			root.getChildren().addAll(Arrays.asList(p1, p2));
			return root;
		}
		return null;

	}

}
