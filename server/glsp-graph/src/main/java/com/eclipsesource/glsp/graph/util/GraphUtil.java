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
package com.eclipsesource.glsp.graph.util;

import com.eclipsesource.glsp.graph.GBounds;
import com.eclipsesource.glsp.graph.GDimension;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GraphFactory;

public final class GraphUtil {

	private GraphUtil() {
	}

	public static GBounds bounds(double x, double y, double width, double height) {
		GBounds bounds = GraphFactory.eINSTANCE.createGBounds();
		bounds.setX(x);
		bounds.setY(y);
		bounds.setWidth(width);
		bounds.setHeight(height);
		return bounds;
	}

	public static GPoint point(double x, double y) {
		GPoint point = GraphFactory.eINSTANCE.createGPoint();
		point.setX(x);
		point.setY(y);
		return point;
	}

	public static GDimension dimension(double width, double height) {
		GDimension dimension = GraphFactory.eINSTANCE.createGDimension();
		dimension.setWidth(width);
		dimension.setHeight(height);
		return dimension;
	}
}
