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
package com.eclipsesource.glsp.server.util;

import java.util.function.Function;
import java.util.function.Predicate;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GPort;
import com.eclipsesource.glsp.graph.GraphFactory;

public final class GModelUtil {

	private GModelUtil() {
	}

	public static GPoint point(double x, double y) {
		GPoint point = GraphFactory.eINSTANCE.createGPoint();
		point.setX(x);
		point.setY(y);
		return point;
	}

	public static int generateId(GModelElement element, String id, GraphicalModelState modelState) {
		Function<Integer, String> idProvider = i -> id + i;
		int count = modelState.getIndex().getCounter(element.eClass(), idProvider);
		element.setId(idProvider.apply(count));
		return count;
	}

	public static Predicate<GModelElement> IS_CONNECTABLE = new Predicate<GModelElement>() {
		@Override
		public boolean test(GModelElement modelElement) {
			return modelElement instanceof GPort || modelElement instanceof GNode;
		}
	};

}
