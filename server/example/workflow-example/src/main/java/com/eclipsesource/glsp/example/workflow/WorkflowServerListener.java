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
package com.eclipsesource.glsp.example.workflow;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.kind.CollapseExpandAction;
import com.eclipsesource.glsp.api.action.kind.CollapseExpandAllAction;
import com.eclipsesource.glsp.api.action.kind.OpenAction;
import com.eclipsesource.glsp.api.action.kind.SelectAction;
import com.eclipsesource.glsp.api.action.kind.SelectAllAction;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;

public class WorkflowServerListener
		implements ModelSelectionListener, ModelExpansionListener, ModelElementOpenListener {
	private static Logger log = Logger.getLogger(WorkflowServerListener.class);

	@Override
	public void elementOpened(OpenAction action) {
		log.info("HANDLE: OpenAction for element: " + action.getElementId());

	}

	@Override
	public void expansionChanged(CollapseExpandAction action) {
		log.info("HANDLE: CollapseExpandAction for elements: " + action.getCollapseIds());

	}

	@Override
	public void expansionChanged(CollapseExpandAllAction action) {
		log.info("HANDLE: CollapseExpandAllAction");

	}

	@Override
	public void selectionChanged(SelectAction action) {
		log.info("HANDLE: SelectAction for elements: " + String.join(", ", action.getSelectedElementsIDs()));

	}

	@Override
	public void selectionChanged(SelectAllAction action) {
		log.info("HANDLE: SelectAllAction");

	}

}
