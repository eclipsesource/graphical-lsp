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
package com.eclipsesource.glsp.api.types;

public class ServerStatus {
	private Severity severity;
	private String message;
	private String details;

	public ServerStatus(Severity severity, String message) {
		super();
		this.severity = severity;
		this.message = message;
	}

	public ServerStatus(Severity severity, String message, String details) {
		this(severity, message);
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public Severity getSeverity() {
		return severity;
	}

	public String getDetails() {
		return details;
	}

	public enum Severity {
		FATAL, ERROR, WARNING, INFO, OK
	}
}
