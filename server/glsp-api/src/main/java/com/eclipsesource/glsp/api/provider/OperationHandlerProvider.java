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
package com.eclipsesource.glsp.api.provider;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.eclipsesource.glsp.api.action.kind.ExecuteOperationAction;
import com.eclipsesource.glsp.api.operations.OperationHandler;

public interface OperationHandlerProvider {

	Set<OperationHandler> getOperationHandlers();

	default int getPriority() {
		return Integer.MAX_VALUE;
	}

	default boolean isHandled(ExecuteOperationAction action) {
		return getOperationHandler(action).isPresent();
	}

	default Optional<OperationHandler> getOperationHandler(ExecuteOperationAction action) {
		return getOperationHandlers().stream().filter(ha -> ha.handles(action)).findFirst();
	}
	
	final static class NullOperationHandlerProvider implements OperationHandlerProvider {
		@Override
		public Set<OperationHandler> getOperationHandlers() {
			return Collections.emptySet();
		}
	}

}
