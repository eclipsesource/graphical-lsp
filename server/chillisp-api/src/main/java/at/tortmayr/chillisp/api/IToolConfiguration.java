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

import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.type.Tool;

public interface IToolConfiguration {

	Tool[] getTools(RequestToolsAction action, IGraphicalLanguageServer server);

	public static class NullImpl implements IToolConfiguration {

		@Override
		public Tool[] getTools(RequestToolsAction action, IGraphicalLanguageServer server) {
			return new Tool[0];
		}

	}
}
