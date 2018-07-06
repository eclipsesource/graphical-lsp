package at.tortmayr.chillisp.api.di;

import com.google.inject.Binder;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IGraphicalModelState;
import at.tortmayr.chillisp.api.IRequestActionHandler;
import at.tortmayr.chillisp.api.impl.DefaultGraphicalLanguageServer;
import at.tortmayr.chillisp.api.impl.DefaultModelState;
import at.tortmayr.chillisp.api.impl.DefaultRequestActionHandler;

public abstract class DefaultServerRuntimeModule extends AbstractGenericModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
	}

	public Class<? extends IGraphicalModelState> bindGraphicalModelState() {
		return DefaultModelState.class;
	}

	public Class<? extends IRequestActionHandler> bindRequestActionHandler() {
		return DefaultRequestActionHandler.class;
	}

	public Class<? extends IGraphicalLanguageServer> bindGraphicalLanguageServer() {
		return DefaultGraphicalLanguageServer.class;
	}

}
