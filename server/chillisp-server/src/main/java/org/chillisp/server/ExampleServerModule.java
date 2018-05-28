package org.chillisp.server;

import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.impl.GraphicalLanguageServerModule;

public class ExampleServerModule extends GraphicalLanguageServerModule {

	@Override
	protected Class<? extends IModelFactory> bindModelFactory() {
		return ExampleModelFactory.class;
	}

}
