/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.api.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;

public class ActionRegistry {
	static Logger log = Logger.getLogger(ActionRegistry.class.getName());
	private static String ACTION_KIND_PACKAGE_NAME = "at.tortmayr.glsp.api.action.kind";
	private static ActionRegistry INSTANCE;

	public static ActionRegistry getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ActionRegistry();
		}
		return INSTANCE;
	}

	private Map<String, Class<? extends Action>> actionKinds = new HashMap<>();
	private Map<String, Consumer<Action>> actionConsumers = new HashMap<>();

	private ActionRegistry() {
		// private constructor due to Singleton pattern
		try {
			intializeDefaultActions();
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			log.warning("Error during action registry initialization. Some action might not be registred correctly");
			e.printStackTrace();
		}

	}

	private void intializeDefaultActions() throws IOException, InstantiationException, IllegalAccessException {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		// retrieve all action classes from the default action package (i.e. classes
		// inside the package which are not abstract and subclasses of Action)
		List<Class<?>> actionClasses = ClassPath.from(loader).getTopLevelClasses(ACTION_KIND_PACKAGE_NAME).stream()
				.map(ci -> ci.load()).filter(c -> !Modifier.isAbstract(c.getModifiers()))
				.filter(Action.class::isAssignableFrom).collect(Collectors.toList());
		for (Class<?> actionClass : actionClasses) {
			Action action = (Action) actionClass.newInstance();
			if (action != null && action.getKind() != null) {
				actionKinds.put(action.getKind(), action.getClass());
			}
		}

	}

	public <T extends ActionHandler> void initialize(T actionHandler)
			throws InstantiationException, IllegalAccessException {
		// filters non-conforming available methods of the action handler (subclass).
		// Method name as to be 'handle' and only method parameter is allowed
		List<Method> conformingMethods = Arrays.stream(actionHandler.getClass().getMethods())
				.filter(m -> m.getName().equals("handle") && m.getParameterCount() == 1).collect(Collectors.toList());
		for (Method m : conformingMethods) {
			Class<?> param = m.getParameters()[0].getType();
			// Check if the first and only method parameter is an action (sub) type
			if (Action.class.isAssignableFrom(param)) {
				Action action = (Action) param.newInstance();
				if (action.getKind() != null) {
					actionConsumers.put(action.getKind(), (Action a) -> {
						if (param.isInstance(a)) {
							try {
								m.invoke(actionHandler, a);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					});

				}

			}
		}

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
	public boolean handleAction(Action action) {
		Consumer<Action> consumer = actionConsumers.get(action.getKind());
		if (consumer != null) {
			consumer.accept(action);
			return true;
		}
		return false;
	}

}
