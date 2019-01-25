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

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.sprotty.SGraph;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.provider.ModelTypeConfigurationProvider;
import com.eclipsesource.glsp.api.utils.ModelOptions;
import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * A base class which can be used for all modelfactories that load an SModel
 * from a file (typically .json)
 * 
 * @author Tobias Ortmayr
 *
 */
public class FileBasedModelFactory implements ModelFactory {
	private static Logger LOGGER = Logger.getLogger(FileBasedModelFactory.class);
	private static final String FILE_PREFIX = "file://";

	@Inject
	private ModelTypeConfigurationProvider modelTypeConfigurationProvider;
	private SModelRoot modelRoot;

	@Override
	public SModelRoot loadModel(RequestModelAction action) {
		String sourceURI = action.getOptions().get(ModelOptions.SOURCE_URI);
		try {
			File modelFile = convertToFile(sourceURI);
			if (modelFile != null && modelFile.exists()) {
				String json = FileUtils.readFileToString(modelFile, "UTF8");
				Gson gson = modelTypeConfigurationProvider.configureGSON().create();
				modelRoot = gson.fromJson(json, SGraph.class);
			}
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return modelRoot;
	}

	private File convertToFile(String sourceURI) {
		if (sourceURI != null && sourceURI.startsWith(FILE_PREFIX)) {
			return new File(sourceURI.replace(FILE_PREFIX, ""));
		}
		return null;
	}

}
