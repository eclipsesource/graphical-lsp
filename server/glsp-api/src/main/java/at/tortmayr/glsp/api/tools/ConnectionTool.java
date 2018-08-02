/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.api.tools;

import java.util.Arrays;
import java.util.stream.Collectors;

import at.tortmayr.glsp.api.utils.Tool;

public abstract class ConnectionTool implements Tool {
	public static final String TYPE = "connection-tool";

	@Override
	public String getId() {
		return generateId();
	}

	protected String generateId() {
		String className = this.getClass().getSimpleName();
		// Split camel case
		String id = Arrays.stream(className.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])"))
				.collect(Collectors.joining("-"));
		return id;
	}

	@Override
	public String getToolType() {
		return ConnectionTool.TYPE;
	}

}
