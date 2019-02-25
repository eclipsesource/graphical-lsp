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
package com.eclipsesource.glsp.server.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.model.IModelState;
import com.eclipsesource.glsp.api.utils.ModelOptions.ParsedModelOptions;
import com.eclipsesource.glsp.api.utils.SModelIndex;

public class ModelStateImpl implements IModelState {
	private ParsedModelOptions options;
	private String clientId;
	private SModelRoot currentModel;
	private Set<String> expandedElements;
	private Set<String> selectedElements;
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
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
		this.currentModelIndex = new SModelIndex(newRoot);
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
	public void setExpandedElements(Set<String> expandedElements) {
		this.expandedElements = expandedElements;

	}

	@Override
	public void setSelectedElements(Set<String> selectedElements) {
		this.selectedElements = selectedElements;
	}

	@Override
	public SModelIndex getCurrentModelIndex() {
		return currentModelIndex;
	}

}
