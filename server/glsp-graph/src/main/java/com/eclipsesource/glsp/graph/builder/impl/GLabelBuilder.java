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
package com.eclipsesource.glsp.graph.builder.impl;

import com.eclipsesource.glsp.graph.DefaultTypes;
import com.eclipsesource.glsp.graph.GLabel;
import com.eclipsesource.glsp.graph.GraphFactory;
import com.eclipsesource.glsp.graph.builder.AbstractGLabelBuilder;

public class GLabelBuilder extends AbstractGLabelBuilder<GLabel, GLabelBuilder> {

	public GLabelBuilder() {
		this(DefaultTypes.LABEL);
	}

	public GLabelBuilder(String type) {
		super(type);
	}

	@Override
	protected GLabel instantiate() {
		return GraphFactory.eINSTANCE.createGLabel();
	}

	@Override
	protected GLabelBuilder self() {
		return this;
	}

}
