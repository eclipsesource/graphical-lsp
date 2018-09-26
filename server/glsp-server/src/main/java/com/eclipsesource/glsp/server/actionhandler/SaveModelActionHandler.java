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
package com.eclipsesource.glsp.server.actionhandler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.SaveModelAction;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.model.ModelTypeConfiguration;
import com.google.gson.Gson;
import com.google.inject.Inject;

import io.typefox.sprotty.api.SModelRoot;

public class SaveModelActionHandler extends AbstractActionHandler {
	private static final Logger LOG= Logger.getLogger(SaveModelActionHandler.class);
	private static final String FILE_PREFIX = "file://";

	@Inject
	ModelTypeConfiguration modelTypeConfiguration;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new SaveModelAction());
	}

	@Override
	public Optional<Action> handle(Action action) {
		if (action instanceof SaveModelAction) {
			SaveModelAction saveAction = (SaveModelAction) action;
			if (saveAction != null) {
				saveModelState(getModelState());
			}
		}
		return Optional.empty();
	}

	private void saveModelState(ModelState modelState) {
		convertToFile(modelState).ifPresent(file -> {
			try {
				Gson gson = modelTypeConfiguration.configureGSON().create();
				FileUtils.writeStringToFile(file, gson.toJson(modelState.getCurrentModel(), SModelRoot.class), "UTF8");
			} catch (IOException e) {
				LOG.error(e);
			}
		});
	}

	private Optional<String> getSourceUri(ModelState modelState) {
		if (modelState.getOptions() != null) {
			return modelState.getOptions().getSourceUri();
		}
		return Optional.empty();
	}

	private Optional<File> convertToFile(ModelState modelState) {
		Optional<String> sourceUriOpt = getSourceUri(modelState);
		if (sourceUriOpt.isPresent()) {
			String uri = sourceUriOpt.get();
			if (uri.startsWith(FILE_PREFIX)) {
				return Optional.of(new File(uri.replace(FILE_PREFIX, "")));
			}
			LOG.warn("Could not parse the sourceUri parameter. Invalid format: " + uri);
		}
		return Optional.empty();

	}
}
