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

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.ClientOptions.ParsedClientOptions;
import com.eclipsesource.glsp.api.utils.ServerOptions;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.eclipsesource.glsp.graph.GModelRoot;

public class ModelStateImpl implements GraphicalModelState {

	private ParsedClientOptions options;
	private String clientId;
	private GModelRoot currentModel;
	private Set<String> expandedElements;
	private Set<String> selectedElements;
	private ServerOptions serverOptions;

	public ModelStateImpl() {
		expandedElements = new HashSet<>();
		selectedElements = new HashSet<>();
		serverOptions = new ServerOptions();
	}

	@Override
	public ParsedClientOptions getClientOptions() {
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
	public GModelRoot getRoot() {
		return currentModel;
	}

	@Override
	public void setRoot(GModelRoot newRoot) {
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
	public void setClientOptions(ParsedClientOptions options) {
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
	public GModelIndex getIndex() {
		return GModelIndex.get(currentModel);
	}

	@Override
	public ServerOptions getServerOptions() {
		return serverOptions;
	}

	@Override
	public void setServerOptions(ServerOptions serverOptions) {
		this.serverOptions = serverOptions;
	}

}
