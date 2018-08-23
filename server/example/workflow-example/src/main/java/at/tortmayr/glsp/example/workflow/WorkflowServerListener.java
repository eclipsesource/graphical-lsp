/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.example.workflow;

import at.tortmayr.glsp.api.action.kind.CollapseExpandAction;
import at.tortmayr.glsp.api.action.kind.CollapseExpandAllAction;
import at.tortmayr.glsp.api.action.kind.OpenAction;
import at.tortmayr.glsp.api.action.kind.SelectAction;
import at.tortmayr.glsp.api.action.kind.SelectAllAction;
import at.tortmayr.glsp.api.listener.GraphicalModelExpansionListener;
import at.tortmayr.glsp.api.listener.GraphicalModelSelectionListener;
import at.tortmayr.glsp.api.listener.ModelElementOpenListener;

public class WorkflowServerListener
		implements GraphicalModelSelectionListener, GraphicalModelExpansionListener, ModelElementOpenListener {

	@Override
	public void elementOpened(OpenAction action) {
		System.out.println("HANDLE: OpenAction for element: " + action.getElementId());

	}

	@Override
	public void expansionChanged(CollapseExpandAction action) {
		System.out.println("HANDLE: CollapseExpandAction for elements: " + action.getCollapseIds());

	}

	@Override
	public void expansionChanged(CollapseExpandAllAction action) {
		System.out.println("HANDLE: CollapseExpandAllAction");

	}

	@Override
	public void selectionChanged(SelectAction action) {
		System.out.println("HANDLE: SelectAction for elements:" + action.getSelectedElementsIDs());

	}

	@Override
	public void selectionChanged(SelectAllAction action) {
		System.out.println("HANDLE: SelectAllAction");

	}

}
