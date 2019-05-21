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

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestOperationsAction;
import com.eclipsesource.glsp.api.action.kind.SetOperationsAction;
import com.eclipsesource.glsp.api.diagram.DiagramConfiguration;
import com.eclipsesource.glsp.api.diagram.DiagramConfigurationProvider;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.google.inject.Inject;

public class RequestOperationsActionHandler extends AbstractActionHandler {
	private static Logger LOG = Logger.getLogger(RequestOperationsActionHandler.class);
	@Inject
	protected DiagramConfigurationProvider diagramConfigurationProvider;

	@Override
	public boolean handles(Action action) {
		return action instanceof RequestOperationsAction;
	}

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		if (action instanceof RequestOperationsAction) {
			RequestOperationsAction requestAction = (RequestOperationsAction) action;
			Optional<String> diagramType = getDiagramType(requestAction, modelState);
			if (!diagramType.isPresent()) {
				LOG.info("RequestOperationsAction failed: No diagram type is present");
				return Optional.empty();
			}
			Optional<DiagramConfiguration> configuration = diagramConfigurationProvider.get(diagramType.get());
			if (!configuration.isPresent()) {
				LOG.info("RequestOperationsAction failed: No diagram confiuration found for : " + diagramType.get());
				return Optional.empty();
			}
			return Optional.of(new SetOperationsAction(configuration.get().getOperations()));
		}

		return Optional.empty();
	}

	private Optional<String> getDiagramType(RequestOperationsAction action, GraphicalModelState modelState) {
		if (action.getDiagramType() != null && !action.getDiagramType().isEmpty()) {
			return Optional.of(action.getDiagramType());
		} else {
			return modelState.getClientOptions().getDiagramType();
		}
	}

}
