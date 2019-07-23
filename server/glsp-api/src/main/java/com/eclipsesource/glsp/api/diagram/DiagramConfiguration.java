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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import com.eclipsesource.glsp.api.operations.Operation;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;

public interface DiagramConfiguration {

	String getDiagramType();

	Map<String, EClass> getTypeMappings();

	List<NodeTypeHint> getNodeTypeHints();

	List<EdgeTypeHint> getEdgeTypeHints();

	List<Operation> getOperations();

	default Optional<EPackage> getEPackage() {
		return Optional.empty();
	}

	default EdgeTypeHint createDefaultEdgeTypeHint(String elementId) {
		return new EdgeTypeHint(elementId, true, true, true, null, null);
	}

	default NodeTypeHint createDefaultNodeTypeHint(String elementId) {
		return new NodeTypeHint(elementId, true, true, true);
	}
}
