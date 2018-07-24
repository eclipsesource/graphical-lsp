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
package at.tortmayr.chillisp.example;

import at.tortmayr.chillisp.api.GraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.GraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.ModelElementOpenListener;
import at.tortmayr.chillisp.api.ModelFactory;
import at.tortmayr.chillisp.api.PopupModelFactory;
import at.tortmayr.chillisp.api.ToolConfiguration;
import at.tortmayr.chillisp.api.di.ServerModule;

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
	public Class<? extends ToolConfiguration> bindToolConfiguration() {
		return WorkflowToolConfiguration.class;
	}

}
