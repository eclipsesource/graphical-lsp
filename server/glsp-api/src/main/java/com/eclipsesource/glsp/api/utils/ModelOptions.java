/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *    
 *  Contributors:
 *  	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.utils;

import java.util.Map;
import java.util.Optional;



public final class ModelOptions {

	public static final String NEEDS_CLIENT_LAYOUT = "needsClientLayout";
	public static final Object SOURCE_URI = "sourceUri";

	private ModelOptions() {
	}

	public static ParsedModelOptions parse(Map<String, String> options) {
		return new ParsedModelOptions(options);
	}

	public static class ParsedModelOptions {
		boolean needsClientLayout;
		Optional<String> sourceUri;

		private ParsedModelOptions(Map<String, String> options) {
			if (options.containsKey(NEEDS_CLIENT_LAYOUT)) {
				needsClientLayout = Boolean.parseBoolean(options.get(NEEDS_CLIENT_LAYOUT));
			}
			if (options.containsKey(SOURCE_URI)) {
				sourceUri = Optional.of(options.get(SOURCE_URI));
			}
		}

		public boolean needsClientLayout() {
			return needsClientLayout;
		}

		public Optional<String> getSourceUri() {
			return sourceUri;
		}

	}
}
