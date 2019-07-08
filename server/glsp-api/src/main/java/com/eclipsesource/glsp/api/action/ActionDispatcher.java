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
package com.eclipsesource.glsp.api.action;

import java.util.Optional;

public interface ActionDispatcher {

	/**
	 * <p>
	 * Handle the given action, received from the specified clientId, and optionally
	 * return a reply Action
	 * </p>
	 * 
	 * @param clientId The client from which the action was received
	 * @param action   The action to dispatch
	 * @return An optional Action to be sent to the client as the result of handling
	 *         the received <code>action</code>
	 */
	Optional<Action> dispatch(String clientId, Action action);

	/**
	 * Send the given action to the specified clientId. This method is intended to
	 * be used for server-to-client communication, independently from any client
	 * request (i.e. when it is not possible to simply "reply" to a Client action)
	 * 
	 * @param clientId
	 * 	The client to which the action should be sent
	 * @param action
	 * 	The action to send to the client
	 */
	void send(String clientId, Action action);

	public static class NullImpl implements ActionDispatcher {

		@Override
		public Optional<Action> dispatch(String clientId, Action action) {
			return Optional.empty();
		}

		@Override
		public void send(String clientId, Action action) {
			return;
		}
	}
}
