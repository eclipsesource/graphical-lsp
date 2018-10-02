/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.provider;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.eclipsesource.glsp.api.command.ServerCommandHandler;

public interface ServerCommandHandlerProvider {
	Set<ServerCommandHandler> getServerCommandHandlers();

	default boolean isHandled(String commandId) {
		return getOperationHandler(commandId).isPresent();
	}

	default Optional<ServerCommandHandler> getOperationHandler(String commandId) {
		return getServerCommandHandlers().stream().filter(ha -> ha.handles(commandId)).findFirst();
	}

	final static class NullImpl implements ServerCommandHandlerProvider {

		@Override
		public Set<ServerCommandHandler> getServerCommandHandlers() {
			return Collections.emptySet();
		}
	}
}
