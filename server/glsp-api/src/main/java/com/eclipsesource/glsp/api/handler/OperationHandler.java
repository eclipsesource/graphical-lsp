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
package com.eclipsesource.glsp.api.handler;

import java.util.Optional;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.kind.ExecuteOperationAction;
import com.eclipsesource.glsp.api.model.ModelState;

public interface OperationHandler extends Handler<ExecuteOperationAction> {

	Optional<SModelRoot> execute(ExecuteOperationAction action, ModelState modelState);

	@Override
	default boolean handles(ExecuteOperationAction action) {
		return Optional.ofNullable(handlesActionType()) //
				.map(cl -> cl.isInstance(action)) //
				.orElse(false);
	}

	default Class<?> handlesActionType() {
		return null;
	}

}
