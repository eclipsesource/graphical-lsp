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
package at.tortmayr.glsp.server;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import at.tortmayr.glsp.api.factory.GraphicalModelState;
import io.typefox.sprotty.api.SModelRoot;

public class ModelStateImpl implements GraphicalModelState {
	private Map<String, String> options;
	private String clientId;
	private SModelRoot currentModel;
	private Set<String> expandedElements;
	private Set<String> selectedElements;
	private boolean needsClientLayout;

	public ModelStateImpl() {
		expandedElements = new HashSet<>();
		selectedElements = new HashSet<>();
	}

	@Override
	public Map<String, String> getOptions() {
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
	public void setOptions(Map<String, String> options) {
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

}
