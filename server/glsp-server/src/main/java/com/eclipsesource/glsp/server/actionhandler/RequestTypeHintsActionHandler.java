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

import java.util.Optional;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestTypeHintsAction;
import com.eclipsesource.glsp.api.action.kind.SetTypeHintsAction;
import com.eclipsesource.glsp.api.diagram.DiagramHandler;
import com.eclipsesource.glsp.api.diagram.DiagramHandlerProvider;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.google.inject.Inject;

public class RequestTypeHintsActionHandler extends AbstractActionHandler {
	@Inject
	private DiagramHandlerProvider diagramHandlerProvider;

	@Override
	public boolean handles(Action action) {
		return action instanceof RequestTypeHintsAction;
	}

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		if (action instanceof RequestTypeHintsAction) {
			Optional<DiagramHandler> handler = diagramHandlerProvider
					.get(((RequestTypeHintsAction) action).getDiagramType());
			if (handler.isPresent()) {
				return Optional
						.of(new SetTypeHintsAction(handler.get().getNodeTypeHints(), handler.get().getEdgeTypeHints()));
			}
		}
		return Optional.empty();
	}
}
