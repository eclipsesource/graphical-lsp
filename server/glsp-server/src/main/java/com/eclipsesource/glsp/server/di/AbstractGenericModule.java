/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.server.di;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.google.inject.Binder;
import com.google.inject.Module;

public abstract class AbstractGenericModule implements Module {
	public static final Logger LOGGER = Logger.getLogger(AbstractGenericModule.class);

	@Override
	public void configure(Binder binder) {
		Module compound = getBindings();
		compound.configure(binder);
	}

	public final CompoundModule getBindings() {
		Method[] methods = this.getClass().getMethods();
		CompoundModule compoundModule = new CompoundModule();
		Arrays.stream(methods).forEach(method -> {
			try {
				if (method.getName().startsWith("bind") || method.getName().startsWith("inject")) {
					compoundModule.add(new MethodBasedModule(method, this) {
					});
				}
			} catch (Exception e) {
				LOGGER.warn("Trying to use method " + method.toGenericString() + " for binding failed");
			}
		});
		return compoundModule;
	}

}
