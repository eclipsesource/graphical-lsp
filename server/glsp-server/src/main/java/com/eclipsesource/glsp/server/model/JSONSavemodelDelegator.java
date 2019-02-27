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
package com.eclipsesource.glsp.server.model;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.language.IGraphicaLanguage;
import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.model.IModelStateProvider;
import com.eclipsesource.glsp.api.model.ISaveModelDelegator;
import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class JSONSavemodelDelegator implements ISaveModelDelegator {
	private static final String SCHEME_FILE = "file";
	private static Logger LOGGER = Logger.getLogger(JSONSavemodelDelegator.class);
	@Inject
	protected IModelStateProvider modelStateProvider;
	@Inject
	protected IModelTypeConfigurationProvider modelTypeConfigurationProvider;
	@Inject
	protected IGraphicaLanguage graphicalLanguage;

	@Override
	public void save(String clientId) {
		IModelState modelState = modelStateProvider.getModelState(clientId);
		URI uri = URI.create(modelState.getOptions().getSourceUri().get());
		if (uri.getScheme().equalsIgnoreCase(SCHEME_FILE)) {
			try {

				File file = new File(uri.getSchemeSpecificPart());
				String extension = FilenameUtils.getExtension(file.getName());
				if (!graphicalLanguage.getFileExtensions().contains(extension)) {
					file = new File(replaceExtension(file.getPath(), graphicalLanguage.getFileExtensions().get(0)));
				}
				Gson gson = modelTypeConfigurationProvider.configureGSON().create();
				FileUtils.writeStringToFile(file, gson.toJson(modelState.getCurrentModel(), GLSPGraph.class), "UTF8");
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
	}

	public static String replaceExtension(String filePath, String extension) {
		String baseFilePath = filePath.substring(0, filePath.lastIndexOf("."));
		return baseFilePath + "." + extension;
	}

}
