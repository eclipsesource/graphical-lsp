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

import com.eclipsesource.glsp.api.model.ModelState;

public interface ActionHandler {

	public boolean handles(Action action);

	public Optional<Action> execute(Action action, ModelState modelState);

}
