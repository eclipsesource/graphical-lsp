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

import com.eclipsesource.glsp.api.factory.IModelFactory;
import com.eclipsesource.glsp.api.factory.IPopupModelFactory;
import com.eclipsesource.glsp.api.model.IModelElementOpenListener;
import com.eclipsesource.glsp.api.model.IModelExpansionListener;
import com.eclipsesource.glsp.api.model.IModelSelectionListener;
import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.eclipsesource.glsp.ecore.diagram.EcoreLayoutEngine;
import com.eclipsesource.glsp.ecore.diagram.EcoreModelFactory;
import com.eclipsesource.glsp.server.ServerModule;
import com.eclipsesource.glsp.server.actionhandler.CollapseExpandActionHandler;
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
	public Class<? extends IModelFactory> bindModelFactory() {
//		return EcoreSprottyFileModelFactory.class;
		return EcoreModelFactory.class;
	}

	@Override
	public Class<? extends IPopupModelFactory> bindPopupModelFactory() {
		return EcorePopupFactory.class;
	}

	@Override
	public Class<? extends IModelSelectionListener> bindModelSelectionListener() {
		return EcoreServerListener.class;
	}

	@Override
	public Class<? extends IModelElementOpenListener> bindModelElementOpenListener() {
		return EcoreServerListener.class;
	}

	@Override
	public Class<? extends IModelExpansionListener> bindModelExpansionListener() {
		return EcoreServerListener.class;
	}

	@Override
	protected Class<? extends IModelTypeConfigurationProvider> bindModelTypesConfigurationProvider() {
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
