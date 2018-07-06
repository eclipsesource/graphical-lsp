package at.tortmayr.chillisp.example;

import at.tortmayr.chillisp.api.IGraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.IGraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.IModelElementOpenListener;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.IPopupModelFactory;
import at.tortmayr.chillisp.api.di.DefaultServerRuntimeModule;

public class WorkflowServerModule extends DefaultServerRuntimeModule {

	public Class<? extends IModelFactory> bindModelFactory() {
		return WorkflowModelFactory.class;
	}

	public Class<? extends IPopupModelFactory> bindPopupModelFactory() {
		return WorkflowPopupFactory.class;
	}

	public Class<? extends IGraphicalModelSelectionListener> bindGraphicalModelSelectionListener() {
		return WorkflowServerListener.class;
	}

	public Class<? extends IModelElementOpenListener> bindModelElementOpenListener() {
		return WorkflowServerListener.class;
	}

	public Class<? extends IGraphicalModelExpansionListener> bindGraphicalModelExpansionListener() {
		return WorkflowServerListener.class;
	}

}
