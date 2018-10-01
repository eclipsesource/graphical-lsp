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
package com.eclipsesource.glsp.api.action;

import java.util.Optional;

import com.eclipsesource.glsp.api.model.ModelStateProvider;

public interface ActionHandler {
	
	public int getPriority();

	public boolean canHandle(Action action);

	public Optional<Action> handle(Action action);

	public void setModelStateProvider(ModelStateProvider provider);

}
