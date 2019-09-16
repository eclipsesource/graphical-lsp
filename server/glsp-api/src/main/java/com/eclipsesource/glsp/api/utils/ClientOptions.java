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
package com.eclipsesource.glsp.api.utils;

import java.util.Map;
import java.util.Optional;

public final class ClientOptions {
	public static final String DIAGRAM_TYPE = "diagramType";
	public static final String SOURCE_URI = "sourceUri";
	public static final String NEEDS_CLIENT_LAYOUT = "needsClientLayout";

	private ClientOptions() {
	}

	public static Optional<String> getValue(Map<String, String> options, String key) {
		return Optional.ofNullable(options.get(key));
	}

	public static Optional<Integer> getIntValue(Map<String, String> options, String key) {
		try {
			return Optional.ofNullable(Integer.parseInt(options.get(key)));
		} catch (NumberFormatException ex) {
			return Optional.empty();
		}
	}

	public static Optional<Float> getFloatValue(Map<String, String> options, String key) {
		try {
			return Optional.ofNullable(Float.parseFloat(options.get(key)));
		} catch (NumberFormatException ex) {
			return Optional.empty();
		}
	}

	public static boolean getBoolValue(Map<String, String> options, String key) {
		return Boolean.parseBoolean(options.get(key));
	}
}
