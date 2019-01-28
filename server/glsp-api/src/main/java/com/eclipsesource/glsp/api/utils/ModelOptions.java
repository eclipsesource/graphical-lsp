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

import static com.eclipsesource.glsp.api.utils.OptionsUtil.getBoolValue;
import static com.eclipsesource.glsp.api.utils.OptionsUtil.getValue;

import java.util.Map;
import java.util.Optional;

public final class ModelOptions {

	public static final String NEEDS_CLIENT_LAYOUT = "needsClientLayout";
	public static final String NEEDS_SERVER_LAYOUT = "needsServerLayout";
	public static final String SOURCE_URI = "sourceUri";

	private ModelOptions() {
	}

	public static ParsedModelOptions parse(Map<String, String> options) {
		return new ParsedModelOptions(options);
	}

	public static class ParsedModelOptions {
		boolean needsClientLayout;
		boolean needsServerLayout;
		Optional<String> sourceUri;

		private ParsedModelOptions(Map<String, String> options) {
			needsClientLayout = getBoolValue(options, NEEDS_CLIENT_LAYOUT);
			needsServerLayout= getBoolValue(options, NEEDS_SERVER_LAYOUT);
			sourceUri = getValue(options, SOURCE_URI);
		}

		public boolean needsClientLayout() {
			return needsClientLayout;
		}

		public Optional<String> getSourceUri() {
			return sourceUri;
		}
		
		public boolean needsServerLayout() {
			return needsServerLayout;
		}

	}
}
