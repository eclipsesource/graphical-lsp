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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.SaveModelAction;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.provider.ModelTypeConfigurationProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class SaveModelActionHandler extends AbstractActionHandler {
	private static final Logger LOG = Logger.getLogger(SaveModelActionHandler.class);
	private static final String FILE_PREFIX = "file://";

	@Inject
	protected ModelTypeConfigurationProvider modelTypeConfigurationProvider;

	@Override
	protected Collection<Action> handleableActionsKinds() {
		return Arrays.asList(new SaveModelAction());
	}

	@Override
	public Optional<Action> execute(Action action, ModelState modelState) {
		if (action instanceof SaveModelAction) {
			SaveModelAction saveAction = (SaveModelAction) action;
			if (saveAction != null) {
				saveModelState(modelState);
			}
		}
		return Optional.empty();
	}

	private void saveModelState(ModelState modelState) {
		convertToFile(modelState).ifPresent(file -> {
			try {
				Gson gson = modelTypeConfigurationProvider.configureGSON().create();
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
