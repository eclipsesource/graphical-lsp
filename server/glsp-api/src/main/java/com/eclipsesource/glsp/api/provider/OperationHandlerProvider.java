/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.provider;

import java.util.Collections;
import java.util.Set;

import com.eclipsesource.glsp.api.action.kind.ExecuteOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;

public interface OperationHandlerProvider extends HandlerProvider<OperationHandler, ExecuteOperationAction> {

	final static class NullImpl implements OperationHandlerProvider {

		@Override
		public Set<OperationHandler> getHandlers() {
			return Collections.emptySet();
		}
	}

}
