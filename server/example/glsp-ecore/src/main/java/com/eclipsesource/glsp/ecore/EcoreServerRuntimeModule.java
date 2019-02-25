/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.ecore;

import org.eclipse.sprotty.ILayoutEngine;

import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;
import com.eclipsesource.glsp.api.provider.ModelTypeConfigurationProvider;
import com.eclipsesource.glsp.ecore.diagram.EcoreLayoutEngine;
import com.eclipsesource.glsp.ecore.diagram.EcoreModelFactory;
import com.eclipsesource.glsp.server.ServerModule;
import com.eclipsesource.glsp.server.actionhandler.CollapseExpandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ComputedBoundsActionHandler;
import com.eclipsesource.glsp.server.actionhandler.ExecuteServerCommandActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OpenActionHandler;
import com.eclipsesource.glsp.server.actionhandler.OperationActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestElementTypeHintsActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestOperationsHandler;
import com.eclipsesource.glsp.server.actionhandler.RequestPopupModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SaveModelActionHandler;
import com.eclipsesource.glsp.server.actionhandler.SelectActionHandler;

public class EcoreServerRuntimeModule extends ServerModule {

	@Override
	public Class<? extends ModelFactory> bindModelFactory() {
//		return EcoreSprottyFileModelFactory.class;
		return EcoreModelFactory.class;
	}

	@Override
	public Class<? extends PopupModelFactory> bindPopupModelFactory() {
		return EcorePopupFactory.class;
	}

	@Override
	public Class<? extends ModelSelectionListener> bindModelSelectionListener() {
		return EcoreServerListener.class;
	}

	@Override
	public Class<? extends ModelElementOpenListener> bindModelElementOpenListener() {
		return EcoreServerListener.class;
	}

	@Override
	public Class<? extends ModelExpansionListener> bindModelExpansionListener() {
		return EcoreServerListener.class;
	}

	@Override
	protected Class<? extends ModelTypeConfigurationProvider> bindModelTypesConfigurationProvider() {
		return EcoreModelTypeConfigurationProvider.class;
	}

	@Override
	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
		return EcoreLayoutEngine.class;
	}

	@Override
	protected void multiBindActionHandlers() {
		bindActionHandler().to(CollapseExpandActionHandler.class);
		bindActionHandler().to(EcoreComputedBoundsActionHandler.class);
		bindActionHandler().to(OpenActionHandler.class);
		bindActionHandler().to(OperationActionHandler.class);
		bindActionHandler().to(RequestModelActionHandler.class);
		bindActionHandler().to(RequestOperationsHandler.class);
		bindActionHandler().to(RequestPopupModelActionHandler.class);
		bindActionHandler().to(SaveModelActionHandler.class);
		bindActionHandler().to(SelectActionHandler.class);
		bindActionHandler().to(ExecuteServerCommandActionHandler.class);
		bindActionHandler().to(RequestElementTypeHintsActionHandler.class);
	}
}
