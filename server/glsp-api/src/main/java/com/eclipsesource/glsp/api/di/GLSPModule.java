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
package com.eclipsesource.glsp.api.di;

import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.factory.PopupModelFactory;
import com.eclipsesource.glsp.api.jsonrpc.GraphicalLanguageServer;
import com.eclipsesource.glsp.api.model.ModelElementOpenListener;
import com.eclipsesource.glsp.api.model.ModelExpansionListener;
import com.eclipsesource.glsp.api.model.ModelSelectionListener;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.model.ModelTypeConfiguration;
import com.eclipsesource.glsp.api.operations.OperationConfiguration;
import com.eclipsesource.glsp.api.provider.ActionHandlerProvider;
import com.eclipsesource.glsp.api.provider.ActionProvider;
import com.eclipsesource.glsp.api.provider.OperationHandlerProvider;
import com.google.inject.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;

import io.typefox.sprotty.api.ILayoutEngine;

public abstract class GLSPModule extends AbstractModule {

	private Multibinder<ActionHandlerProvider> actionHandlerProviderBinder;
	private Multibinder<ActionProvider> actionProviderBinder;
	private Multibinder<OperationHandlerProvider> operationHandlerProviderBinder;

	@Override
	protected final void configure() {
		bindComponents();
		bindProviders();
	}

	protected void bindComponents() {
		bind(GraphicalLanguageServer.class).to(bindGraphicalLanguageServer());
		bind(PopupModelFactory.class).to(bindPopupModelFactory());
		bind(ModelFactory.class).to(bindModelFactory());
		bind(ModelState.class).to(bindModelState());
		bind(ModelSelectionListener.class).to(bindGraphicalModelSelectionListener());
		bind(ModelExpansionListener.class).to(bindGraphicalModelExpansionListener());
		bind(ModelElementOpenListener.class).to(bindModelElementOpenListener());
		bind(ILayoutEngine.class).to(bindLayoutEngine());
		bind(OperationConfiguration.class).to(bindOperationConfiguration());
		bind(ModelTypeConfiguration.class).to(bindModelTypeConfiguration());
	}

	protected void bindProviders() {
		actionProviderBinder = Multibinder.newSetBinder(binder(), ActionProvider.class);
		actionHandlerProviderBinder = Multibinder.newSetBinder(binder(), ActionHandlerProvider.class);
		operationHandlerProviderBinder = Multibinder.newSetBinder(binder(), OperationHandlerProvider.class);
		bindActionProviders();
		bindActionHandlerProviders();
		bindOperationHandlerProviders();
	}

	protected abstract Class<? extends GraphicalLanguageServer> bindGraphicalLanguageServer();

	protected abstract Class<? extends ModelState> bindModelState();

	protected abstract void bindActionProviders();

	protected abstract void bindActionHandlerProviders();

	protected abstract void bindOperationHandlerProviders();
	
	protected final LinkedBindingBuilder<ActionProvider> bindActionProvider() {
		return actionProviderBinder.addBinding();
	}

	protected final LinkedBindingBuilder<ActionHandlerProvider> bindActionHandlerProvider() {
		return actionHandlerProviderBinder.addBinding();
	}

	protected final LinkedBindingBuilder<OperationHandlerProvider> bindOperationHandlerProvider() {
		return operationHandlerProviderBinder.addBinding();
	}

	protected Class<? extends ModelFactory> bindModelFactory() {
		return ModelFactory.NullImpl.class;
	}

	protected Class<? extends ModelExpansionListener> bindGraphicalModelExpansionListener() {
		return ModelExpansionListener.NullImpl.class;
	}

	protected Class<? extends ModelSelectionListener> bindGraphicalModelSelectionListener() {
		return ModelSelectionListener.NullImpl.class;
	}

	protected Class<? extends ModelElementOpenListener> bindModelElementOpenListener() {
		return ModelElementOpenListener.NullImpl.class;
	}

	protected Class<? extends PopupModelFactory> bindPopupModelFactory() {
		return PopupModelFactory.NullImpl.class;
	}

	protected Class<? extends OperationConfiguration> bindOperationConfiguration() {
		return OperationConfiguration.NullOperationConfiguration.class;
	}

	protected Class<? extends ILayoutEngine> bindLayoutEngine() {
		return ILayoutEngine.NullImpl.class;
	}

	protected Class<? extends ModelTypeConfiguration> bindModelTypeConfiguration() {
		return ModelTypeConfiguration.NullImpl.class;
	}

}
