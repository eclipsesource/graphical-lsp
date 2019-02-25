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
package com.eclipsesource.glsp.api.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import com.eclipsesource.glsp.api.handler.IActionHandler;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.provider.IActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.IActionProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ActionRegistry {
	static Logger log = Logger.getLogger(ActionRegistry.class.getName());

	private Set<Action> registeredActions;
	private Map<String, Class<? extends Action>> actions;
	private Map<String, IActionHandler> actionHandlers;

	@Inject
	public ActionRegistry(ActionProvider registeredActionProvider, ActionHandlerProvider registeredHandlerProvider) {
		actions = new HashMap<>();
		actionHandlers = new HashMap<>();
		registeredActions = new HashSet<>();
		initializeMaps(registeredActionProvider, registeredHandlerProvider);

	}

	private void initializeMaps(IActionProvider registeredActionProvider,
			IActionHandlerProvider registeredHandlerProvider) {
		// sort providers by priority

		registeredActionProvider.getActions().forEach(action -> {
			if (!actions.containsKey(action.getKind())) {
				actions.put(action.getKind(), action.getClass());
				registeredActions.add(action);
				if (registeredHandlerProvider.isHandled(action)) {
					IActionHandler handler = registeredHandlerProvider.getHandler(action).get();
					actionHandlers.put(action.getKind(), handler);
				}
			}
		});
	}

	public Set<Action> getAllActions() {
		return Collections.unmodifiableSet(registeredActions);
	}

	/**
	 * Queries the correspondent Java class object for the given action kind
	 * 
	 * @param kind The kind/type of action
	 * @return correspondent Class
	 */
	public Class<? extends Action> getActionClass(String kind) {
		return actions.get(kind);
	}

	/**
	 * Process the passed action by delegating it to the registered consumer (i.e.
	 * action handler method)
	 * 
	 * @param action Action which should be processed
	 * @return true if a registered consumer was found and the action was accepted
	 */
	public Optional<Action> delegateToHandler(Action action, IModelState modelState) {
		IActionHandler handler = actionHandlers.get(action.getKind());
		if (handler != null) {
			return handler.execute(action, modelState);
		}
		return Optional.empty();
	}

	public boolean hasHandler(Action action) {
		return actionHandlers.containsKey(action.getKind());
	}

}
