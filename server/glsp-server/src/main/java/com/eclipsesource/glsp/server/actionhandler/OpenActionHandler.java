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
package com.eclipsesource.glsp.server.actionhandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.inject.Inject;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.OpenAction;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelState;

public class OpenActionHandler extends AbstractActionHandler {
	@Inject
	protected ModelElementOpenListener modelElementOpenListener;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new OpenAction());
	}

	@Override
	public Optional<Action> execute(Action action, ModelState modelState) {
		if (action instanceof OpenAction) {
			if (modelElementOpenListener != null) {
				modelElementOpenListener.elementOpened((OpenAction) action);
			}
		}
		return Optional.empty();
	}

}
