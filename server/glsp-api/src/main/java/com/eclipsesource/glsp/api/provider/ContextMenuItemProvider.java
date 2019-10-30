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
package com.eclipsesource.glsp.api.provider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.types.MenuItem;
import com.eclipsesource.glsp.graph.GPoint;

@FunctionalInterface
public interface ContextMenuItemProvider {

	public static String KEY = "context-menu";

	Set<MenuItem> getItems(GraphicalModelState modelState, List<String> selectedElementIds,
			Optional<GPoint> lastMousePosition, Map<String, String> args);

	default Set<MenuItem> getItems(GraphicalModelState modelState, List<String> selectedElementIds,
			GPoint lastMousePosition, Map<String, String> args) {
		return getItems(modelState, selectedElementIds, Optional.ofNullable(lastMousePosition), args);
	}

	public static class NullImpl implements ContextMenuItemProvider {
		@Override
		public Set<MenuItem> getItems(GraphicalModelState modelState, List<String> selectedElementIds,
				Optional<GPoint> lastMousePosition, Map<String, String> args) {
			return Collections.emptySet();
		}
	}
}
