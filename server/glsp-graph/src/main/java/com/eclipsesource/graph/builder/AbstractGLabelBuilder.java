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
package com.eclipsesource.graph.builder;

import com.eclipsesource.glsp.graph.GEdgePlacement;
import com.eclipsesource.glsp.graph.GLabel;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.util.GraphUtil;

public abstract class AbstractGLabelBuilder<T extends GLabel, E extends AbstractGLabelBuilder<T, E>>
		extends GShapeElementBuilder<T, E> {
	protected GPoint alignment;
	protected GEdgePlacement edgePlacement;
	protected String text;

	public AbstractGLabelBuilder(String type) {
		super(type);
	}

	public E setAlignment(GPoint alignment) {
		this.alignment = alignment;
		return self();
	}

	public E setAlignment(double x, double y) {
		return setAlignment(GraphUtil.point(x, y));
	}

	public E setEdgePlacement(GEdgePlacement edgePlacement) {
		this.edgePlacement = edgePlacement;
		return self();
	}

	public E setText(String text) {
		this.text = text;
		return self();
	}

	@Override
	public void setProperties(T label) {
		super.setProperties(label);
		label.setAlignment(alignment);
		label.setEdgePlacement(edgePlacement);
		label.setText(text);
	}

}
