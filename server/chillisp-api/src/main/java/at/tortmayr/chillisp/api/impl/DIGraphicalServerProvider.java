package at.tortmayr.chillisp.api.impl;

import com.google.inject.Guice;
import com.google.inject.Injector;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;

public class DIGraphicalServerProvider extends AbstractDIGraphicalServerProvider {

	private GraphicalLanguageServerModule module;

	public DIGraphicalServerProvider() {
		super(DefaultGraphicalLanguageServer.class);
		module = new GraphicalLanguageServerModule();
	}

	public DIGraphicalServerProvider(Class<? extends IGraphicalLanguageServer> serverClass,
			GraphicalLanguageServerModule module) {
		super(serverClass);
		this.module = module;
	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(module);
	}

}
