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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.RequestContextActions;
import com.eclipsesource.glsp.api.action.kind.SetContextActions;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.provider.CommandPaletteActionProvider;
import com.eclipsesource.glsp.api.provider.ContextMenuItemProvider;
import com.eclipsesource.glsp.api.types.LabeledAction;
import com.google.inject.Inject;

public class RequestContextActionsHandler extends AbstractActionHandler {

	public static String UI_CONTROL_KEY = "ui-control";

	@Inject
	protected CommandPaletteActionProvider commandPaletteActionProvider;

	@Inject
	protected ContextMenuItemProvider contextMenuItemProvider;

	@Override
	public boolean handles(Action action) {
		return action instanceof RequestContextActions;
	}

	@Override
	public Optional<Action> execute(Action action, GraphicalModelState modelState) {
		if (action instanceof RequestContextActions) {
			RequestContextActions requestContextAction = (RequestContextActions) action;
			List<String> selectedElementIds = requestContextAction.getSelectedElementIds();
			Map<String, String> args = requestContextAction.getArgs();
			Set<LabeledAction> items = new HashSet<>();
			if (equalsUiControl(args, CommandPaletteActionProvider.KEY)) {
				items.addAll(commandPaletteActionProvider.getActions(modelState, selectedElementIds,
						requestContextAction.getLastMousePosition(), args));
			} else if (equalsUiControl(args, ContextMenuItemProvider.KEY)) {
				items.addAll(contextMenuItemProvider.getItems(modelState, selectedElementIds,
						requestContextAction.getLastMousePosition(), args));

			}
			return Optional.of(new SetContextActions(items, requestContextAction.getArgs()));
		}
		return Optional.empty();
	}

	protected boolean equalsUiControl(Map<String, String> args, String uiControlKey) {
		return args.containsKey(UI_CONTROL_KEY) && args.get(UI_CONTROL_KEY).equals(uiControlKey);
	}
}
