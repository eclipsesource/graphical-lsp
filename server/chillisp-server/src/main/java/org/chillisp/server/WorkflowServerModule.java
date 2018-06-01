package org.chillisp.server;

import at.tortmayr.chillisp.api.IGraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.IGraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.IModelElementOpenListener;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.IPopupModelFactory;
import at.tortmayr.chillisp.api.di.DefaultServerRuntimeModule;
import io.typefox.sprotty.api.ILayoutEngine;

public class WorkflowServerModule extends DefaultServerRuntimeModule {

	public Class<? extends IModelFactory> bindModelFactory() {
		return SimpleModelFactory.class;
	}

	@Override
	public Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ExampleLayoutEngine.class;
	}

	@Override
	public Class<? extends IPopupModelFactory> bindPopupModelFactory() {
		return WorkflowPopupFactory.class;
	}

	@Override
	public Class<? extends IGraphicalModelSelectionListener> bindGraphicalModelSelectionListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends IModelElementOpenListener> bindModelElementOpenListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends IGraphicalModelExpansionListener> bindGraphicalModelExpansionListener() {
		return WorkflowServerListener.class;
	}

}
