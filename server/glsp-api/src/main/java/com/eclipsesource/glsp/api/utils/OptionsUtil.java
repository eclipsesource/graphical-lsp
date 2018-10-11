/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.utils;

import java.util.Map;
import java.util.Optional;

/**
 * Utility class for the handling of String-based option-maps
 * 
 * @author Tobias Ortmayr
 *
 */
public class OptionsUtil {
	private OptionsUtil() {

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
