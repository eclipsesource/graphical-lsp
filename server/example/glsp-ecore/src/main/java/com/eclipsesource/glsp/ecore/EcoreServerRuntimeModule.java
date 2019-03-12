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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.sprotty.ILayoutEngine;

import com.eclipsesource.glsp.api.factory.IPopupModelFactory;
import com.eclipsesource.glsp.api.handler.IOperationHandler;
import com.eclipsesource.glsp.api.language.IGraphicaLanguage;
import com.eclipsesource.glsp.api.model.IModelElementOpenListener;
import com.eclipsesource.glsp.api.model.IModelExpansionListener;
import com.eclipsesource.glsp.api.model.IModelSelectionListener;
import com.eclipsesource.glsp.api.model.ISaveModelDelegator;
import com.eclipsesource.glsp.api.operations.IOperationConfiguration;
import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.eclipsesource.glsp.ecore.diagram.EcoreLayoutEngine;
import com.eclipsesource.glsp.ecore.model.EcoreJSONSModelLoader;
import com.eclipsesource.glsp.ecore.model.EcoreXMIModelLoader;
import com.eclipsesource.glsp.ecore.operationhandler.CreateClassiferOperationHandler;
import com.eclipsesource.glsp.ecore.operationhandler.CreateInheritanceHandler;
import com.eclipsesource.glsp.ecore.operationhandler.CreateReferenceOperationHandler;
import com.eclipsesource.glsp.ecore.operationhandler.EcoreDeleteOperationHandler;
import com.eclipsesource.glsp.ecore.operationhandler.EcoreReconnectEdgeHandler;
import com.eclipsesource.glsp.server.ServerModule;
import com.eclipsesource.glsp.server.model.IFileExtensionLoader;
import com.eclipsesource.glsp.server.operationhandler.ChangeBoundsOperationHandler;
import com.eclipsesource.glsp.server.operationhandler.RerouteEdgeHandler;

public class EcoreServerRuntimeModule extends ServerModule {

	@Override
	protected List<Class<? extends IFileExtensionLoader<?>>> bindFileExtensionLoaders() {
		return Arrays.asList(EcoreXMIModelLoader.class, EcoreJSONSModelLoader.class);
	}

	@Override
	protected List<Class<? extends IOperationHandler>> bindOperationsHandlers() {
		 List<Class<? extends IOperationHandler>> handlers= new ArrayList<>();
		 handlers.add(ChangeBoundsOperationHandler.class);
		 handlers.add(EcoreDeleteOperationHandler.class);
		 handlers.add(CreateClassiferOperationHandler.class);
		 handlers.add(CreateReferenceOperationHandler.class);
		 handlers.add(CreateInheritanceHandler.class);
		 handlers.add(RerouteEdgeHandler.class);
		 handlers.add(EcoreReconnectEdgeHandler.class);
		 return handlers;
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

//	@Override
//	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
//		return EcoreLayoutEngine.class;
//	}

	@Override
	protected Class<? extends IGraphicaLanguage> bindGraphicalLanguage() {
		return EcoreLanguage.class;
	}

	@Override
	protected Class<? extends ISaveModelDelegator> bindSaveModelDelegator() {
		return EcoreSaveModelDelegator.class;
	}
	
	@Override
	public Class<? extends IOperationConfiguration> bindOperationConfiguration() {
		return EcoreOperationConfiguration.class;
	}
	
	

}
