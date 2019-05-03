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
package com.eclipsesource.glsp.server.websocket;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.ActionMessage;
import com.eclipsesource.glsp.api.websocket.GLSPServerEndpoint;

public class LoggingServerEndpoint extends GLSPServerEndpoint {
	private static Logger logger = Logger.getLogger(LoggingServerEndpoint.class);

	public void onOpen(Session session, EndpointConfig config) {
		logger.info("Opened connection [" + session.getId() + "]");
		super.onOpen(session, config);
	}

	public void onClose(Session session, CloseReason closeReason) {
		logger.info("Closed connection [" + session.getId() + "]");
		super.onClose(session, closeReason);
	}

	public void accept(ActionMessage message) {
		logger.info("SERVER: " + message);
		super.accept(message);
	}

	protected void fireMessageReceived(ActionMessage message) {
		logger.info("CLIENT:" + message);
		super.fireMessageReceived(message);
	}
}
