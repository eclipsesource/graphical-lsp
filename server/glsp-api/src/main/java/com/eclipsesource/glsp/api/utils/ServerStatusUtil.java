/*******************************************************************************
 * Copyright (c) 2017-2018 TypeFox and others.
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
package com.eclipsesource.glsp.api.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.eclipsesource.glsp.api.action.kind.ServerStatusAction;
import com.eclipsesource.glsp.api.types.ServerStatus;
import com.eclipsesource.glsp.api.types.ServerStatus.Severity;

public class ServerStatusUtil {

	public static ServerStatusAction info(String message) {
		return new ServerStatusAction(new ServerStatus(Severity.INFO, message));
	}

	public static ServerStatusAction warn(String message) {
		return new ServerStatusAction(new ServerStatus(Severity.WARNING, message));
	}

	public static ServerStatusAction error(String message, String details) {
		return new ServerStatusAction(new ServerStatus(Severity.ERROR, message, details));
	}

	public static ServerStatusAction error(Exception e) {
		return error(e.getMessage(), printStacktrace(e.getCause()));
	}

	private ServerStatusUtil() {
	};

	public static String printStacktrace(Throwable t) {
		if (t == null) {
			return null;
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String result = "";
		if (t.getMessage() != null) {
			result += t.getMessage() + "\n";
		}
		return result + sw.toString();
	}

}
