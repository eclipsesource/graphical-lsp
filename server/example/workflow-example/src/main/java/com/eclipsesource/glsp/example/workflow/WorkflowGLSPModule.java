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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.eclipsesource.glsp.api.diagram.DiagramConfiguration;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.handler.ServerCommandHandler;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.labeledit.LabelEditValidator;
import com.eclipsesource.glsp.api.markers.ModelValidator;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;
import com.eclipsesource.glsp.api.provider.CommandPaletteActionProvider;
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
import com.eclipsesource.glsp.example.workflow.labeledit.WorkflowLabelEditValidator;
import com.eclipsesource.glsp.example.workflow.marker.WorkflowModelValidator;
import com.eclipsesource.glsp.graph.GraphExtension;
import com.eclipsesource.glsp.server.di.DefaultGLSPModule;
import com.eclipsesource.glsp.server.jsonrpc.DefaultGLSPServer;
import com.eclipsesource.glsp.server.operationhandler.ApplyLabelEditOperationHandler;
import com.eclipsesource.glsp.server.operationhandler.ChangeBoundsOperationHandler;
import com.eclipsesource.glsp.server.operationhandler.DeleteOperationHandler;

@SuppressWarnings("serial")
public class WorkflowGLSPModule extends DefaultGLSPModule {

	@Override
	protected Class<? extends GLSPServer> bindGLSPServer() {
		return WorkflowGLSPServer.class;
	}
	
	@Override
	public Class<? extends PopupModelFactory> bindPopupModelFactory() {
		return WorkflowPopupFactory.class;
	}

	@Override
	public Class<? extends ModelSelectionListener> bindModelSelectionListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends ModelElementOpenListener> bindModelElementOpenListener() {
		return WorkflowServerListener.class;
	}

	@Override
	public Class<? extends ModelExpansionListener> bindModelExpansionListener() {
		return WorkflowServerListener.class;
	}

	@Override
	protected Class<? extends CommandPaletteActionProvider> bindCommandPaletteActionProvider() {
		return WorkflowCommandPaletteActionProvider.class;
	}

	@Override
	protected Collection<Class<? extends OperationHandler>> bindOperationHandlers() {
		return new ArrayList<Class<? extends OperationHandler>>() {
			{
				add(CreateAutomatedTaskHandler.class);
				add(CreateManualTaskHandler.class);
				add(CreateDecisionNodeHandler.class);
				add(CreateMergeNodeHandler.class);
				add(CreateWeightedEdgeHandler.class);
				add(CreateEdgeHandler.class);
				add(ReconnectEdgeHandler.class);
				add(RerouteEdgeHandler.class);
				add(DeleteWorkflowElementHandler.class);
				add(ChangeBoundsOperationHandler.class);
				add(DeleteOperationHandler.class);
				add(ApplyLabelEditOperationHandler.class);
			}
		};
	}

	@Override
	protected Collection<Class<? extends ServerCommandHandler>> bindServerCommandHandlers() {
		return Arrays.asList(SimulateCommandHandler.class);
	}

	@Override
	protected Class<? extends ModelValidator> bindModelValidator() {
		return WorkflowModelValidator.class;
	}
	
	@Override
	protected Class<? extends LabelEditValidator> bindLabelEditValidator() {
		return WorkflowLabelEditValidator.class;
	}

	@Override
	protected Collection<Class<? extends DiagramConfiguration>> bindDiagramConfigurations() {
		return Arrays.asList(WorfklowDiagramConfiguration.class);
	}

	@Override
	protected Class<? extends GraphExtension> bindGraphExtension() {
		return WFGraphExtension.class;
	}
}
