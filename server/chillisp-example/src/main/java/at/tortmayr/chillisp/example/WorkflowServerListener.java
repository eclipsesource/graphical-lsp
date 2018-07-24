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
package at.tortmayr.chillisp.example;

import at.tortmayr.chillisp.api.GraphicalLanguageServer;
import at.tortmayr.chillisp.api.GraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.GraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.ModelElementOpenListener;
import at.tortmayr.chillisp.api.action.CollapseExpandAction;
import at.tortmayr.chillisp.api.action.CollapseExpandAllAction;
import at.tortmayr.chillisp.api.action.OpenAction;
import at.tortmayr.chillisp.api.action.SelectAction;
import at.tortmayr.chillisp.api.action.SelectAllAction;

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
