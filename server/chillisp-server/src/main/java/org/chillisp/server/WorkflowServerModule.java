package org.chillisp.server;

import at.tortmayr.chillisp.api.IGraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.IGraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.IModelElementOpenListener;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.IPopupModelFactory;
import at.tortmayr.chillisp.api.impl.GraphicalLanguageServerModule;
import io.typefox.sprotty.api.ILayoutEngine;

public class WorkflowServerModule extends GraphicalLanguageServerModule {

	@Override
	protected Class<? extends IModelFactory> bindModelFactory() {
		return WorkflowModelFactory.class;
	}

	@Override
	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ExampleLayoutEngine.class;
	}

	@Override
	protected Class<? extends IPopupModelFactory> bindPopupModelFactory() {
		return WorkflowPopupFactory.class;
	}

	@Override
	protected Class<? extends IGraphicalModelSelectionListener> bindGraphicalModelSelectionListener() {
		return WorkflowServerListener.class;
	}

	@Override
	protected Class<? extends IModelElementOpenListener> bindModelElementOpenListener() {
		return WorkflowServerListener.class;
	}

	@Override
	protected Class<? extends IGraphicalModelExpansionListener> bindGraphicalModelExpansionListener() {
		return WorkflowServerListener.class;
	}

}
