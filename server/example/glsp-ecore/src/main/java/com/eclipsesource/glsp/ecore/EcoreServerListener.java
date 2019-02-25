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

import com.eclipsesource.glsp.api.action.kind.CollapseExpandAction;
import com.eclipsesource.glsp.api.action.kind.CollapseExpandAllAction;
import com.eclipsesource.glsp.api.action.kind.OpenAction;
import com.eclipsesource.glsp.api.action.kind.SelectAction;
import com.eclipsesource.glsp.api.action.kind.SelectAllAction;
import com.eclipsesource.glsp.api.model.IModelElementOpenListener;
import com.eclipsesource.glsp.api.model.IModelExpansionListener;
import com.eclipsesource.glsp.api.model.IModelSelectionListener;

public class EcoreServerListener
		implements IModelSelectionListener, IModelExpansionListener, IModelElementOpenListener {

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
