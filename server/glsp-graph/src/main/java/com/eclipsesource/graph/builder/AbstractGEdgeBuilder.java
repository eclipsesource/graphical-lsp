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

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.util.GraphUtil;

public abstract class AbstractGEdgeBuilder<T extends GEdge, E extends AbstractGEdgeBuilder<T, E>>
		extends GModelElementBuilder<T, E> {

	private GModelElement source;
	private GModelElement target;
	private List<GPoint> routingPoints = new ArrayList<>();

	public AbstractGEdgeBuilder(String type) {
		super(type);
	}

	public E source(GModelElement source) {
		this.source = source;
		return self();
	}

	public E target(GModelElement target) {
		this.target = target;
		return self();
	}

	public E addRoutingPoint(GPoint point) {
		this.routingPoints.add(point);
		return self();
	}

	public E addRoutingPoint(double x, double y) {
		return addRoutingPoint(GraphUtil.point(x, y));
	}

	public E addRoutingPoints(List<GPoint> routingPoints) {
		this.routingPoints.addAll(routingPoints);
		return self();
	}

	@Override
	protected void setProperties(T edge) {
		super.setProperties(edge);
		if (source != null) {
			edge.setSource(source);
			edge.setSourceId(source.getId());
		}
		if (target != null) {
			edge.setTarget(target);
			edge.setTargetId(target.getId());
		}
		edge.getRoutingPoints().addAll(routingPoints);

	}

}
