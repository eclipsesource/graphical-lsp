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
package at.tortmayr.glsp.api.operations;

import java.util.Optional;

import at.tortmayr.glsp.api.action.ExecuteOperationAction;
import at.tortmayr.glsp.api.factory.GraphicalModelState;
import io.typefox.sprotty.api.SModelRoot;

public interface OperationHandler {

	boolean handles(ExecuteOperationAction action);

	Optional<SModelRoot> execute(ExecuteOperationAction action, GraphicalModelState modelState);

}
