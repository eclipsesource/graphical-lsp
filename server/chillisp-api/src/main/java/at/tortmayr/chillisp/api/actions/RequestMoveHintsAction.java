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
package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.ActionKind;

public class RequestMoveHintsAction extends Action {

	public RequestMoveHintsAction() {
		super(ActionKind.REQUEST_MOVE_HINTS);
	}
}
