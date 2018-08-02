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

import at.tortmayr.glsp.api.GraphicalModelExpansionListener;
import at.tortmayr.glsp.api.GraphicalModelSelectionListener;
import at.tortmayr.glsp.api.ModelElementOpenListener;
import at.tortmayr.glsp.api.action.CollapseExpandAction;
import at.tortmayr.glsp.api.action.CollapseExpandAllAction;
import at.tortmayr.glsp.api.action.OpenAction;
import at.tortmayr.glsp.api.action.SelectAction;
import at.tortmayr.glsp.api.action.SelectAllAction;

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
