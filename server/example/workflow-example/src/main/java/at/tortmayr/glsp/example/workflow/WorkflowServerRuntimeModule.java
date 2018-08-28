/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.example.workflow;

import at.tortmayr.glsp.api.factory.ModelFactory;
import at.tortmayr.glsp.api.factory.PopupModelFactory;
import at.tortmayr.glsp.api.listener.GraphicalModelExpansionListener;
import at.tortmayr.glsp.api.listener.GraphicalModelSelectionListener;
import at.tortmayr.glsp.api.listener.ModelElementOpenListener;
import at.tortmayr.glsp.api.operations.OperationConfiguration;
import at.tortmayr.glsp.api.operations.OperationHandlerProvider;
import at.tortmayr.glsp.server.di.ServerModule;


public class WorkflowServerRuntimeModule extends ServerModule {
	@Override
	public Class<? extends ModelFactory> bindModelFactory() {
		return WorkflowModelFactory.class;
	}

	@Override
	public Class<? extends PopupModelFactory> bindPopupModelFactory() {
		return WorkflowPopupFactory.class;
	}

	@Override
	public Class<? extends GraphicalModelSelectionListener> bindGraphicalModelSelectionListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends ModelElementOpenListener> bindModelElementOpenListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends GraphicalModelExpansionListener> bindGraphicalModelExpansionListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends OperationConfiguration> bindOperationConfiguration() {
		return WorkflowOperationConfiguration.class;
	}
	
	@Override
	public Class<? extends OperationHandlerProvider> bindOperationHandlerProvider() {
		return WorkflowOperationHandlerProvider.class;
	}

}
