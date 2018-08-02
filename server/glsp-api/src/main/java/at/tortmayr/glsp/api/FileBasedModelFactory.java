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
package at.tortmayr.glsp.api;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.tortmayr.glsp.api.action.RequestModelAction;
import at.tortmayr.glsp.api.json.SModelElementTypeAdapter;
import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.server.json.EnumTypeAdapter;

public abstract class FileBasedModelFactory implements ModelFactory {
	private static Logger LOGGER = Logger.getLogger(FileBasedModelFactory.class);
	private static final String FILE_PREFIX="file://";
	private SModelRoot modelRoot;
	protected GsonBuilder builder;

	private File modelFile;

	public FileBasedModelFactory() {
		configureGson();
	}

	private void configureGson() {
		builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new SModelElementTypeAdapter.Factory(getModelTypeSchema()))
				.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
	}

	/**
	 * Returns a map which enables the identification of the corresponding Java
	 * class for each SModelElement based on its type property. This mappings will
	 * be reused by GSON for proper JSON-to-Java conversion.
	 */
	protected abstract Map<String, Class<? extends SModelElement>> getModelTypeSchema();

	@Override
	public SModelRoot loadModel( RequestModelAction action) {
		String sourceURI = action.getOptions().get(Options.SOURCE_URI);
		try {
			modelFile = convertToFile(sourceURI);
			if (modelFile != null && modelFile.exists()) {
				String json = FileUtils.readFileToString(modelFile, "UTF8");
				Gson gson = builder.create();
				modelRoot = gson.fromJson(json, SGraph.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		return modelRoot;
	}

	public boolean saveModel(SModelRoot modelRoot) {
		if (modelFile != null) {
			try {
				Gson gson = builder.setPrettyPrinting().create();
				FileUtils.writeStringToFile(modelFile, gson.toJson(modelRoot, SModelRoot.class), "UTF8");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private File convertToFile(String sourceURI) {
		if (sourceURI != null && sourceURI.startsWith(FILE_PREFIX)) {
			return new File(sourceURI.replace(FILE_PREFIX, ""));
		}
		return null;

	}

}
