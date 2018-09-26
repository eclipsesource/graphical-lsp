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
package com.eclipsesource.glsp.server.model;

import java.util.HashSet;
import java.util.Set;

import com.eclipsesource.glsp.api.utils.ModelOptions.ParsedModelOptions;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.api.utils.SModelIndex;

import io.typefox.sprotty.api.SModelRoot;

public class ModelStateImpl implements ModelState {
	private ParsedModelOptions options;
	private String clientId;
	private SModelRoot currentModel;
	private Set<String> expandedElements;
	private Set<String> selectedElements;
	private boolean needsClientLayout;
	private SModelIndex currentModelIndex;

	public ModelStateImpl() {
		expandedElements = new HashSet<>();
		selectedElements = new HashSet<>();
	}

	@Override
	public ParsedModelOptions getOptions() {
		return options;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public SModelRoot getCurrentModel() {
		return currentModel;
	}

	@Override
	public void setCurrentModel(SModelRoot newRoot) {
		this.currentModel = newRoot;
		this.currentModelIndex= new SModelIndex(newRoot);
	}

	@Override
	public Set<String> getExpandedElements() {
		return expandedElements;
	}

	@Override
	public Set<String> getSelectedElements() {
		return selectedElements;
	}

	@Override
	public void setOptions(ParsedModelOptions options) {
		this.options = options;

	}

	@Override
	public boolean needsClientLayout() {
		return needsClientLayout;
	}

	@Override
	public void setNeedsClientLayout(boolean value) {
		this.needsClientLayout = value;

	}

	@Override
	public void setExpandedElements(Set<String> expandedElements) {
		this.expandedElements = expandedElements;

	}

	@Override
	public void setSelectedElemetns(Set<String> selectedElements) {
		this.selectedElements = selectedElements;
	}

	@Override
	public SModelIndex getCurrentModelIndex() {
		return currentModelIndex;
	}

}
