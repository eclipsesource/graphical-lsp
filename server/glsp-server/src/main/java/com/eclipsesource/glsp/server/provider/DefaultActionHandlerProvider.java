/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server.provider;

import java.util.HashSet;
import java.util.Set;

import com.eclipsesource.glsp.api.action.ActionHandler;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.server.actionhandler.CollapseExpandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ComputedBoundsActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OpenActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OperationActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestOperationsHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestPopupModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SaveModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SelectActionHandler;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class DefaultActionHandlerProvider implements ActionHandlerProvider {

	@Inject
	public DefaultActionHandlerProvider(Injector injector) {
		defaultActionHandlers = new HashSet<>();
		addDefaultHandlers(injector);
	}

	private Set<ActionHandler> defaultActionHandlers;

	public DefaultActionHandlerProvider() {

	}

	private void addDefaultHandlers(Injector injector) {
		defaultActionHandlers.add(injector.getInstance(CollapseExpandActionHandler.class));
		defaultActionHandlers.add(injector.getInstance(ComputedBoundsActionHandler.class));
		defaultActionHandlers.add(injector.getInstance(OpenActionHandler.class));
		defaultActionHandlers.add(injector.getInstance(OperationActionHandler.class));
		defaultActionHandlers.add(injector.getInstance(RequestModelActionHandler.class));
		defaultActionHandlers.add(injector.getInstance(RequestOperationsHandler.class));
		defaultActionHandlers.add(injector.getInstance(RequestPopupModelActionHandler.class));
		defaultActionHandlers.add(injector.getInstance(SaveModelActionHandler.class));
		defaultActionHandlers.add(injector.getInstance(SelectActionHandler.class));
	}

	@Override
	public Set<ActionHandler> getActionHandlerHandlers() {
		return defaultActionHandlers;
	}

}
