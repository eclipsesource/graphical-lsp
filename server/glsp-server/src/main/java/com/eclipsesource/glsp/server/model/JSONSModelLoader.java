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
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.sprotty.SGraph;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.provider.ModelTypeConfigurationProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * A abstract model factory for SModels that are persisted in JSON format
 * 
 * @author Tobias Ortmayr<tortmayr@eclipsesource.com>
 *
 */
public abstract class JSONSModelLoader implements IModelLoader {
	private static final String SCHEME_FILE = "file";
	private static Logger LOGGER = Logger.getLogger(FileBasedModelFactory.class);

	@Inject
	protected ModelTypeConfigurationProvider modelTypeConfigurationProvider;

	public abstract List<String> getExtensions();

	@Override
	public SModelRoot generate(URI uri) {
		if (uri.getScheme().equalsIgnoreCase(SCHEME_FILE)) {
			try {
				File modelFile = new File(uri.getSchemeSpecificPart());
				if (modelFile != null && modelFile.exists()) {
					String json = FileUtils.readFileToString(modelFile, "UTF8");
					Gson gson = modelTypeConfigurationProvider.configureGSON().create();
					return gson.fromJson(json, SGraph.class);
				}
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
		return null;
	}

}
