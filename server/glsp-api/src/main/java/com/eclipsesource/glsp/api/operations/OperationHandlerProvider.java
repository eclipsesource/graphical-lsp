/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.operations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.eclipsesource.glsp.api.action.ExecuteOperationAction;

public interface OperationHandlerProvider {

	List<OperationHandler> getOperationHandlers();

	default boolean isHandled(ExecuteOperationAction action) {
		return getOperationHandler(action).isPresent();
	}

	default Optional<OperationHandler> getOperationHandler(ExecuteOperationAction action) {
		return getOperationHandlers(action).findFirst();
	}

	default Stream<OperationHandler> getOperationHandlers(ExecuteOperationAction action) {
		Stream<OperationHandler> handlers = getOperationHandlers().stream().filter(handler -> handler.handles(action));
		return handlers;
	}

	final static class NullOperationHandlerProvider implements OperationHandlerProvider {
		@Override
		public List<OperationHandler> getOperationHandlers() {
			return Collections.emptyList();
		}
	}

}
