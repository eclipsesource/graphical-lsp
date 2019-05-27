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
package com.eclipsesource.glsp.example.workflow.handler;

import java.util.Optional;

import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelElement;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.workflow.schema.ModelTypes;
import com.eclipsesource.glsp.example.workflow.schema.WeightedEdge;
import com.eclipsesource.glsp.server.operationhandler.CreateConnectionOperationHandler;
import com.eclipsesource.glsp.server.util.SModelUtil;

public class CreateWeightedEdgeHandler extends CreateConnectionOperationHandler {

	public CreateWeightedEdgeHandler() {
		super(ModelTypes.WEIGHTED_EDGE);

	}

	@Override
	protected Optional<SEdge> createConnection(SModelElement source, SModelElement target,
			GraphicalModelState modelState) {
		WeightedEdge edge = new WeightedEdge();
		edge.setSourceId(source.getId());
		edge.setTargetId(target.getId());
		edge.setType(elementTypeId);
		edge.setProbability("high");
		SModelUtil.generateId(edge, "weightedEdge", modelState);
		return Optional.of(edge);
	}

}
