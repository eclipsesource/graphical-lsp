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
package com.eclipsesource.graph.builder.impl;

import com.eclipsesource.glsp.graph.GBounds;
import com.eclipsesource.glsp.graph.GDimension;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GraphFactory;
import com.eclipsesource.graph.builder.GBuilder;

public class GBoundsBuilder extends GBuilder<GBounds> {
	private double x;
	private double y;
	private double width;
	private double height;

	public GBoundsBuilder setX(double x) {
		this.x = x;
		return this;
	}

	public GBoundsBuilder setY(double y) {
		this.y = y;
		return this;
	}

	public GBoundsBuilder setWidth(double width) {
		this.width = width;
		return this;
	}

	public GBoundsBuilder setHeight(double height) {
		this.height = height;
		return this;
	}

	public GBoundsBuilder setPosition(GPoint position) {
		this.x = position.getX();
		this.y = position.getY();
		return this;
	}

	public GBoundsBuilder setDimension(GDimension dimension) {
		this.height = dimension.getHeight();
		this.width = dimension.getWidth();
		return this;
	}

	@Override
	protected GBounds instantiate() {
		return GraphFactory.eINSTANCE.createGBounds();
	}

	@Override
	protected void setProperties(GBounds element) {
		element.setX(x);
		element.setY(y);
		element.setWidth(width);
		element.setHeight(height);
	}

}
