/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr- initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow.handler;

import static com.eclipsesource.glsp.api.utils.OptionsUtil.getValue;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.handler.ServerCommandHandler;
import com.eclipsesource.glsp.api.model.ModelState;

import io.typefox.sprotty.api.SModelElement;

public class SimulateCommandHandler implements ServerCommandHandler {
	private static Logger logger = Logger.getLogger(SimulateCommandHandler.class);
	public static final String SIMULATE_COMMAND_ID = "simulate-command";
	public static final String OPTIONS_INVOKER_ID = "invokerId";

	@Override
	public boolean handles(String commandId) {
		return SIMULATE_COMMAND_ID.equals(commandId);
	}

	@Override
	public Optional<Action> execute(String commandId, Map<String, String> options, ModelState modelState) {
		Optional<Action> result = Optional.empty();
		if (SIMULATE_COMMAND_ID.equals(commandId)) {
			getValue(options, OPTIONS_INVOKER_ID).ifPresent(id -> {
				SModelElement invoker = modelState.getCurrentModelIndex().get(id);
				if (invoker != null) {
					logger.info("Start simulation of " + invoker.getId());
					double duration = ThreadLocalRandom.current().nextDouble(0d, 10d);
					logger.info("Task simulation finished within " + duration + " seconds");
				}

			});
		}

		return result;
	}

}
