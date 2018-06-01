package at.tortmayr.chillisp.api.impl;

import com.google.inject.Guice;
import com.google.inject.Injector;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.di.DefaultServerRuntimeModule;

public class DIGraphicalServerProvider extends AbstractDIGraphicalServerProvider {

	private DefaultServerRuntimeModule module;

	public DIGraphicalServerProvider(Class<? extends IGraphicalLanguageServer> serverClass,
			DefaultServerRuntimeModule module) {
		super(serverClass);
		this.module = module;
	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(module);
	}

}
