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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.factory.GraphGsonConfiguratorFactory;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.ClientOptions;
import com.eclipsesource.glsp.graph.GGraph;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * A base class which can be used for all model factories that load an SModel
 * from a file (typically a json file)
 * 
 * @author Tobias Ortmayr
 */
public class FileBasedModelFactory implements ModelFactory {
	private static Logger LOGGER = Logger.getLogger(FileBasedModelFactory.class);
	private static final String FILE_PREFIX = "file://";

	@Inject
	private GraphGsonConfiguratorFactory gsonConfigurationFactory;
	private GModelRoot modelRoot;

	@Override
	public GModelRoot loadModel(RequestModelAction action, GraphicalModelState modelState) {
		String sourceURI = action.getOptions().get(ClientOptions.SOURCE_URI);
		File modelFile = convertToFile(sourceURI);
		if (modelFile != null && modelFile.exists()) {
			try (Reader reader = new InputStreamReader(new FileInputStream(modelFile), StandardCharsets.UTF_8)) {
				Gson gson = gsonConfigurationFactory.configureGson().create();
				modelRoot = gson.fromJson(reader, GGraph.class);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
		return modelRoot;
	}

	protected File convertToFile(String sourceURI) {
		if (sourceURI != null) {
			return new File(sourceURI.replace(FILE_PREFIX, ""));
		}
		return null;
	}

}
