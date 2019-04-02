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
package com.eclipsesource.glsp.api.diagram;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.handler.ActionHandler;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;

public abstract class DiagramHandler {

	public abstract ActionHandlerProvider getActionHandlerProvider();

	public abstract String getDiagramType();

	public Optional<Action> execute(String clientId, Action action) {
		Optional<ActionHandler> handler = getActionHandlerProvider().getHandler(action);
		if (handler.isPresent()) {
			return handler.get().execute(clientId, action);
		}
		return Optional.empty();
	}

	public abstract Map<String, Class<? extends SModelElement>> getTypeMappings();

	public abstract List<NodeTypeHint> getNodeTypeHints();

	public abstract List<EdgeTypeHint> getEdgeTypeHints();

	public EdgeTypeHint createDefaultEdgeTypeHint(String elementId) {
		return new EdgeTypeHint(elementId, true, true, true, null, null);
	}

	public NodeTypeHint createDefaultNodeTypeHint(String elementId) {
		return new NodeTypeHint(elementId, true, true, true);
	}
}
