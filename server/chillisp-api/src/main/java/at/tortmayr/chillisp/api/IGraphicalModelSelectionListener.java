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

import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;

public interface IGraphicalModelSelectionListener {
	void selectionChanged(SelectAction acion, IGraphicalLanguageServer server);

	void selectionChanged(SelectAllAction action, IGraphicalLanguageServer server);

	public static class NullImpl implements IGraphicalModelSelectionListener {

		@Override
		public void selectionChanged(SelectAction action, IGraphicalLanguageServer server) {
		}

		@Override
		public void selectionChanged(SelectAllAction action, IGraphicalLanguageServer server) {
		}

	}

}
