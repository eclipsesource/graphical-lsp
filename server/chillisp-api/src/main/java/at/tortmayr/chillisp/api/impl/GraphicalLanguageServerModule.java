package at.tortmayr.chillisp.api.impl;

import com.google.inject.AbstractModule;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IGraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.IGraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.IGraphicalModelState;
import at.tortmayr.chillisp.api.IModelElementOpenListener;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.IPopupModelFactory;
import at.tortmayr.chillisp.api.IRequestActionHandler;
import io.typefox.sprotty.api.ILayoutEngine;

public class GraphicalLanguageServerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IGraphicalLanguageServer.class).to(bindGraphicalLanguageServer());
		bind(IPopupModelFactory.class).to(bindPopupModelFactory());
		bind(IModelFactory.class).to(bindModelFactory());
		bind(IRequestActionHandler.class).to(bindRequestActionHandler());
		bind(IGraphicalModelState.class).to(bindGraphicalModelState());
		bind(IGraphicalModelSelectionListener.class).to(bindGraphicalModelSelectionListener());
		bind(IGraphicalModelExpansionListener.class).to(bindGraphicalModelExpansionListener());
		bind(IModelElementOpenListener.class).to(bindModelElementOpenListener());
		bind(ILayoutEngine.class).to(bindLayoutEngine());
	}

	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ILayoutEngine.NullImpl.class;
	}

	protected Class<? extends IModelElementOpenListener> bindModelElementOpenListener() {
		return IModelElementOpenListener.NullImpl.class;
	}

	protected Class<? extends IGraphicalModelExpansionListener> bindGraphicalModelExpansionListener() {
		return IGraphicalModelExpansionListener.NullImpl.class;
	}

	protected Class<? extends IGraphicalModelSelectionListener> bindGraphicalModelSelectionListener() {
		return IGraphicalModelSelectionListener.NullImpl.class;
	}

	protected Class<? extends IGraphicalModelState> bindGraphicalModelState() {
		return DefaultModelState.class;
	}

	protected Class<? extends IRequestActionHandler> bindRequestActionHandler() {
		return DefaultRequestActionHandler.class;
	}

	protected Class<? extends IGraphicalLanguageServer> bindGraphicalLanguageServer() {
		return DefaultGraphicalLanguageServer.class;
	}

	protected Class<? extends IModelFactory> bindModelFactory() {
		return IModelFactory.NullImpl.class;
	}

	protected Class<? extends IPopupModelFactory> bindPopupModelFactory() {
		return IPopupModelFactory.NullImpl.class;
	}
}
