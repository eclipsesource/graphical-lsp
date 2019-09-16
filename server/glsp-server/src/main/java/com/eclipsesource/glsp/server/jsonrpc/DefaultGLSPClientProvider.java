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
package com.eclipsesource.glsp.server.jsonrpc;

import java.util.HashMap;
import java.util.Map;

import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClientProvider;
import com.google.inject.Singleton;

@Singleton
public class DefaultGLSPClientProvider implements GLSPClientProvider {

	private final Map<String, GLSPClient> clients = new HashMap<>();
	
	@Override
	public void register(String clientId, GLSPClient client) {
		clients.put(clientId, client);
	}

	@Override
	public GLSPClient resolve(String clientId) {
		return clients.get(clientId);
	}

	@Override
	public void remove(String clientId) {
		clients.remove(clientId);
	}

}
