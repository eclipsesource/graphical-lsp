package com.eclipsesource.glsp.api.provider;

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
import java.util.Collections;
import java.util.Set;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.handler.ActionHandler;

public interface ActionHandlerProvider extends HandlerProvider<ActionHandler, Action> {

	final static class NullImpl implements ActionHandlerProvider {

		@Override
		public Set<ActionHandler> getHandlers() {
			return Collections.emptySet();
		}

	}
}
