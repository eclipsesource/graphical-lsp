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
package com.eclipsesource.glsp.api.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.ActionProvider;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ActionRegistry {
	static Logger log = Logger.getLogger(ActionRegistry.class.getName());

	private Set<Action> registeredActions;
	private Map<String, Class<? extends Action>> actionKinds;
	private Map<String, ActionHandler> actionHandlers;

	@Inject
	public ActionRegistry(Set<ActionProvider> registeredActionProviders,
			Set<ActionHandlerProvider> registeredHandlerProviders) {
		actionKinds = new HashMap<>();
		actionHandlers = new HashMap<>();
		registeredActions = new HashSet<>();
		initializeMaps(registeredActionProviders, registeredHandlerProviders);

	}

	private void initializeMaps(Set<ActionProvider> actionProviders,
			Set<ActionHandlerProvider> registeredHandlerProviders) {
		// sort providers by priority
		Set<ActionHandlerProvider> sportedHandlerProviders = registeredHandlerProviders.stream()
				.sorted(Comparator.comparing(ActionHandlerProvider::getPriority)).collect(Collectors.toSet());
		Set<ActionProvider> sortedActionProvider = actionProviders = actionProviders.stream()
				.sorted(Comparator.comparing(ActionProvider::getPriority)).collect(Collectors.toSet());

		sortedActionProvider.forEach(aProv -> {
			aProv.getActions().forEach(action -> {
				if (!actionKinds.containsKey(action.getKind())) {
					actionKinds.put(action.getKind(), action.getClass());
					registeredActions.add(action);

					Optional<ActionHandler> handlerOpt = getActionHandler(action, sportedHandlerProviders);
					if (handlerOpt.isPresent()) {
						actionHandlers.put(action.getKind(), handlerOpt.get());
					}

				}
			});
		});

	}

	private Optional<ActionHandler> getActionHandler(Action action, Set<ActionHandlerProvider> handlerProviders) {
		Optional<ActionHandlerProvider> handlerProviderOpt = handlerProviders.stream()
				.filter((hProv -> hProv.isHandled(action))).findFirst();
		if (handlerProviderOpt.isPresent()) {
			return handlerProviderOpt.get().getActionHandler(action);
		}
		return Optional.empty();
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
		return actionKinds.get(kind);
	}

	/**
	 * Process the passed action by delegating it to the registered consumer (i.e.
	 * action handler method)
	 * 
	 * @param action Action which should be processed
	 * @return true if a registered consumer was found and the action was accepted
	 */
	public Optional<Action> delegateToHandler(Action action, ModelStateProvider modelStateProvider) {
		ActionHandler handler = actionHandlers.get(action.getKind());
		if (handler != null) {
			handler.setModelStateProvider(modelStateProvider);
			return handler.handle(action);
		}
		return Optional.empty();
	}

	public boolean hasHandler(Action action) {
		return actionHandlers.containsKey(action.getKind());
	}

}
