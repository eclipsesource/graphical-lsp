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
package com.eclipsesource.glsp.server.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.model.ModelTypeConfiguration;
import com.eclipsesource.glsp.api.utils.ModelOptions;
import com.google.gson.Gson;
import com.google.inject.Inject;

import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SModelRoot;

/**
 * A base class which can be used for all modelfactories that load an SModel
 * from a file (typically .json)
 * 
 * @author Tobias Ortmayr <tortmayr@eclipsesource.com>
 *
 */
public  class FileBasedModelFactory implements ModelFactory {
	private static Logger LOGGER = Logger.getLogger(FileBasedModelFactory.class);
	private static final String FILE_PREFIX = "file://";

	@Inject
	private ModelTypeConfiguration modelTypeConfiguration;
	private SModelRoot modelRoot;

	@Override
	public SModelRoot loadModel(RequestModelAction action) {
		String sourceURI = action.getOptions().get(ModelOptions.SOURCE_URI);
		try {
			File modelFile = convertToFile(sourceURI);
			if (modelFile != null && modelFile.exists()) {
				String json = FileUtils.readFileToString(modelFile, "UTF8");
				Gson gson = modelTypeConfiguration.configureGSON().create();
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
