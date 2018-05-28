package org.chillisp.server;

import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.impl.GraphicalLanguageServerModule;
import io.typefox.sprotty.api.ILayoutEngine;

public class ExampleServerModule extends GraphicalLanguageServerModule {

	@Override
	protected Class<? extends IModelFactory> bindModelFactory() {
		return ExampleModelFactory.class;
	}

	@Override
	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ExampleLayoutEngine.class;
	}

}
