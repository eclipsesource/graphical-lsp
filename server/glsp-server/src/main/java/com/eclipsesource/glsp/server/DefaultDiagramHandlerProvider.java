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
package com.eclipsesource.glsp.server;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import com.eclipsesource.glsp.api.diagram.DiagramManager;
import com.eclipsesource.glsp.api.diagram.DiagramHandlerProvider;
import com.google.inject.Inject;

public class DefaultDiagramHandlerProvider implements DiagramHandlerProvider {
	public static final String DEFAULT_DIAGRAM_TYPE = "default-diagram";
	private Set<DiagramManager> diagramHandlers;

	@Inject
	public DefaultDiagramHandlerProvider(Set<DiagramManager> diagramHandlers) {
		this.diagramHandlers = diagramHandlers;
	}

	@Override
	public Collection<String> getDiagramTypes() {
		return Arrays.asList(DEFAULT_DIAGRAM_TYPE);
	}

	@Override
	public Collection<DiagramManager> getAll() {
		return diagramHandlers;
	}

}
