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
package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.action.SelectAction;
import at.tortmayr.chillisp.api.action.SelectAllAction;

public interface GraphicalModelSelectionListener {
	void selectionChanged(SelectAction acion);

	void selectionChanged(SelectAllAction action);

	public static class NullImpl implements GraphicalModelSelectionListener {

		@Override
		public void selectionChanged(SelectAction action) {
		}

		@Override
		public void selectionChanged(SelectAllAction action) {
		}

	}

}
