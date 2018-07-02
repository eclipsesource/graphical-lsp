package at.tortmayr.chillisp.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.tortmayr.chillisp.api.impl.FileBasedModelFactory;

public class TestModelFactory extends FileBasedModelFactory {

	@Override
	protected void configureGson() {
		GsonBuilder builder = new GsonBuilder();
		SModelElementTypeAdapter.configureGson(builder);
		 gson = builder.create();
	
		
	}

}
