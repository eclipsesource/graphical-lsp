/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow;

import com.eclipsesource.glsp.api.factory.IPopupModelFactory;
import com.eclipsesource.glsp.api.language.IGraphicaLanguage;
import com.eclipsesource.glsp.api.model.IModelElementOpenListener;
import com.eclipsesource.glsp.api.model.IModelExpansionListener;
import com.eclipsesource.glsp.api.model.IModelSelectionListener;
import com.eclipsesource.glsp.api.model.ISaveModelDelegator;
import com.eclipsesource.glsp.api.operations.IOperationConfiguration;
import com.eclipsesource.glsp.api.provider.ICommandPaletteActionProvider;
import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.eclipsesource.glsp.example.workflow.handler.CreateAutomatedTaskHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateDecisionNodeHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateEdgeHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateManualTaskHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateMergeNodeHandler;
import com.eclipsesource.glsp.example.workflow.handler.CreateWeightedEdgeHandler;
import com.eclipsesource.glsp.example.workflow.handler.DeleteWorkflowElementHandler;
import com.eclipsesource.glsp.example.workflow.handler.ReconnectEdgeHandler;
import com.eclipsesource.glsp.example.workflow.handler.RerouteEdgeHandler;
import com.eclipsesource.glsp.example.workflow.handler.SimulateCommandHandler;
import com.eclipsesource.glsp.example.workflow.marker.WorkflowModelValidator;
import com.eclipsesource.glsp.server.ServerModule;
import com.eclipsesource.glsp.server.model.JSONSModelLoader;
import com.eclipsesource.glsp.server.model.JSONSavemodelDelegator;
import com.eclipsesource.glsp.server.operationhandler.ChangeBoundsOperationHandler;
import com.eclipsesource.glsp.server.operationhandler.DeleteHandler;

public class WorkflowServerRuntimeModule extends ServerModule {

	@Override
	protected Class<? extends IModelTypeConfigurationProvider> bindModelTypesConfigurationProvider() {
		return WorkflowModelTypeConfigurationProvider.class;
	}

	@Override
	public Class<? extends IPopupModelFactory> bindPopupModelFactory() {
		return WorkflowPopupFactory.class;
	}

	@Override
	public Class<? extends IModelSelectionListener> bindModelSelectionListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends IModelElementOpenListener> bindModelElementOpenListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends IModelExpansionListener> bindModelExpansionListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends IOperationConfiguration> bindOperationConfiguration() {
		return WorkflowOperationConfiguration.class;
	}

	@Override
	protected Class<? extends ICommandPaletteActionProvider> bindCommandPaletteActionProvider() {
		return WorkflowCommandPaletteActionProvider.class;
	}

	@Override
	protected void multiBindOperationHandlers() {
		bindOperationHandler().to(CreateAutomatedTaskHandler.class);
		bindOperationHandler().to(CreateManualTaskHandler.class);
		bindOperationHandler().to(CreateDecisionNodeHandler.class);
		bindOperationHandler().to(CreateMergeNodeHandler.class);
		bindOperationHandler().to(CreateWeightedEdgeHandler.class);
		bindOperationHandler().to(CreateEdgeHandler.class);
		bindOperationHandler().to(ReconnectEdgeHandler.class);
		bindOperationHandler().to(RerouteEdgeHandler.class);
		bindOperationHandler().to(DeleteWorkflowElementHandler.class);
		bindOperationHandler().to(ChangeBoundsOperationHandler.class);
		bindOperationHandler().to(DeleteHandler.class);
	}

	@Override
	protected void multiBindServerCommandHandlers() {
		bindServerCommandHandler().to(SimulateCommandHandler.class);
	}

	@Override
	protected void multiBindFileExtensionLoader() {
		bindFileExtensionLoader().to(JSONSModelLoader.class);
	}

	@Override
	protected Class<? extends ISaveModelDelegator> bindSaveModelDelegator() {
		return JSONSavemodelDelegator.class;
	}

	@Override
	protected Class<? extends IGraphicaLanguage> bindGraphicalLanguage() {
		return WorkflowLanguage.class;
	}

}
