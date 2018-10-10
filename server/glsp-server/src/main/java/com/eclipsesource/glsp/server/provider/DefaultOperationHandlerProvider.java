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
package com.eclipsesource.glsp.server.provider;

import java.util.Set;

import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.google.inject.Inject;

public class DefaultOperationHandlerProvider implements OperationHandlerProvider {
	private Set<OperationHandler> handlers;

	@Inject
	public DefaultOperationHandlerProvider(Set<OperationHandler> handlers) {
		this.handlers = handlers;
	}

	@Override
	public Set<OperationHandler> getHandlers() {
		return handlers;
	}

}
