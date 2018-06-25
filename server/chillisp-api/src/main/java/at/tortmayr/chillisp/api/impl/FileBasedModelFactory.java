package at.tortmayr.chillisp.api.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SModelRoot;

public abstract class FileBasedModelFactory implements IModelFactory {
	private static Logger LOGGER = Logger.getLogger(FileBasedModelFactory.class);
	private SModelRoot modelRoot;
	protected Gson gson;

	public FileBasedModelFactory() {
		configureGson();
	}
	
	protected abstract void configureGson();

	@Override
	public SModelRoot loadModel(IGraphicalLanguageServer server, RequestModelAction action) {
		String sourceURI = action.getOptions().get("sourceUri");
		try {
			File modelFile = convertToFile(sourceURI);
			if (modelFile != null && modelFile.exists()) {
				String json = FileUtils.readFileToString(modelFile, "UTF8");
				modelRoot= gson.fromJson(json, SGraph.class);
				System.out.println(modelRoot);
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		return modelRoot;
	}

	private File convertToFile(String sourceURI) {
		if (sourceURI != null && sourceURI.startsWith("file://")) {
			return new File(sourceURI.replace("file://", ""));
		}
		return null;

	}


}
