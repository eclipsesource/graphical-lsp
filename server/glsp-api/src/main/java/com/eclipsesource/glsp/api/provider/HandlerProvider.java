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
package com.eclipsesource.glsp.api.provider;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import com.eclipsesource.glsp.api.handler.Handler;

public interface HandlerProvider<E extends Handler<T>, T> {
	
	Set<E> getHandlers();

	default boolean isHandled(T object) {
		return getHandler(object).isPresent();
	}

	default Optional<E> getHandler(T object) {
		return getHandlers().stream().sorted(Comparator.comparing(Handler::getPriority))
				.filter(ha -> ha.handles(object)).findFirst();
	}

}
