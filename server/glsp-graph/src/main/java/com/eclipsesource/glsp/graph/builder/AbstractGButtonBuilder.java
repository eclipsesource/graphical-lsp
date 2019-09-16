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
package com.eclipsesource.glsp.graph.builder;

import com.eclipsesource.glsp.graph.GButton;

public abstract class AbstractGButtonBuilder<T extends GButton, E extends AbstractGButtonBuilder<T, E>>
		extends GShapeElementBuilder<T, E> {
	protected boolean enabled;

	public AbstractGButtonBuilder(String type) {
		super(type);
	}

	public E enabled(boolean enabled) {
		this.enabled = enabled;
		return self();
	}

	@Override
	public void setProperties(T button) {
		super.setProperties(button);
		button.setEnabled(enabled);
	}

}
