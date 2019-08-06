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
package com.eclipsesource.glsp.api.jsonrpc;

public class GLSPServerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@link GLSPServerException} with the specified message.
	 *
	 * @param message the error message
	 */
	public GLSPServerException(final String message) {
		super(message);
	}

	/**
	 * Constructs a {@link GLSPServerException} with the specified message and an
	 * additional exception.
	 *
	 * @param message   the error message
	 * @param throwable a throwable as hint to the original cause of the error
	 */
	public GLSPServerException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
