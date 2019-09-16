/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.server.actionhandler;

import java.util.Optional;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.SetEditLabelValidationResultAction;
import com.eclipsesource.glsp.api.action.kind.ValidateLabelEditAction;
import com.eclipsesource.glsp.api.labeledit.EditLabelValidationResult;
import com.eclipsesource.glsp.api.labeledit.LabelEditValidator;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GModelElement;
import com.google.inject.Inject;

public class ValidateLabelEditActionHandler extends AbstractActionHandler {

	@Inject
	protected LabelEditValidator editLabelValidator;

	@Override
	public boolean handles(Action action) {
		return action instanceof ValidateLabelEditAction;
	}

	@Override
	protected Optional<Action> execute(Action action, GraphicalModelState modelState) {
		ValidateLabelEditAction validateAction = (ValidateLabelEditAction) action;
		Optional<GModelElement> element = modelState.getIndex().get(validateAction.getLabelId());
		if (element.isPresent()) {
			return Optional.of(new SetEditLabelValidationResultAction(
					editLabelValidator.validate(modelState, validateAction.getValue(), element.get())));
		}
		return Optional.of(new SetEditLabelValidationResultAction(EditLabelValidationResult.OK_RESULT));
	}

}
