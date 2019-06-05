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
//TODO class is heavily based on the LayoutUtil of the sprotty repository. Change copyright header accordingly 
package com.eclipsesource.glsp.api.utils;

import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.eclipsesource.glsp.api.action.kind.ComputedBoundsAction;
import com.eclipsesource.glsp.api.action.kind.ElementAndAlignment;
import com.eclipsesource.glsp.api.action.kind.ElementAndBounds;
import com.eclipsesource.glsp.graph.GAlignable;
import com.eclipsesource.glsp.graph.GBounds;
import com.eclipsesource.glsp.graph.GBoundsAware;
import com.eclipsesource.glsp.graph.GDimension;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GraphFactory;

public final class LayoutUtil {

	private LayoutUtil() {
	}

	/**
	 * Apply the computed bounds from the given action to the model.
	 */
	public static void applyBounds(GModelRoot root, ComputedBoundsAction action) {
		GModelIndex index = GModelIndex.get(root);
		for (ElementAndBounds b : action.getBounds()) {
			Optional<GModelElement> element = index.get(b.getElementId());
			if (element.isPresent() && element.get() instanceof GBoundsAware) {
				GBoundsAware bae = (GBoundsAware) element.get();
				GBounds newBounds = b.getNewBounds();
				bae.setPosition(asPoint(newBounds));
				bae.setSize(asDimension(newBounds));
			}
		}
		for (ElementAndAlignment a : action.getAlignments()) {
			Optional<GModelElement> element = index.get(a.getElementId());
			if (element.isPresent() && element.get() instanceof GAlignable) {
				GAlignable alignable = (GAlignable) element.get();
				alignable.setAlignment(a.getNewAlignment());
			}
		}
	}

	public static GPoint asPoint(GBounds bounds) {
		GPoint point = GraphFactory.eINSTANCE.createGPoint();
		point.setX(bounds.getX());
		point.setY(bounds.getY());
		return point;
	}

	public static GDimension asDimension(GBounds bounds) {
		GDimension dimension = GraphFactory.eINSTANCE.createGDimension();
		dimension.setHeight(bounds.getHeight());
		dimension.setWidth(bounds.getWidth());
		return dimension;
	}

	/**
	 * Copy the layout of one model instance to another. Model elements are matched
	 * by their id.
	 */
	public static void copyLayoutData(GModelRoot fromRoot, GModelRoot toRoot) {
		GModelIndex oldIndex = GModelIndex.get(fromRoot);
		copyLayoutDataRecursively(toRoot, oldIndex);
	}

	private static void copyLayoutDataRecursively(GModelElement element, GModelIndex oldIndex) {
		if (element instanceof GBoundsAware) {
			Optional<GModelElement> oldElement = oldIndex.get(element.getId());
			if (oldElement.isPresent() && oldElement.get() instanceof GBoundsAware) {
				GBoundsAware newBae = (GBoundsAware) element;
				GBoundsAware oldBae = (GBoundsAware) oldElement.get();
				if (oldBae.getPosition() != null)
					newBae.setPosition(EcoreUtil.copy(oldBae.getPosition()));
				if (oldBae.getSize() != null)
					newBae.setSize(EcoreUtil.copy(oldBae.getSize()));
			}
		} else if (element instanceof GEdge) {
			Optional<GModelElement> oldElement = oldIndex.get(element.getId());
			if (oldElement.isPresent() && oldElement.get() instanceof GEdge
					&& ((GEdge) oldElement.get()).getRoutingPoints() != null) {
				GEdge gEdge = (GEdge) element;
				gEdge.getRoutingPoints().clear();
				gEdge.getRoutingPoints().addAll(EcoreUtil.copyAll(((GEdge) oldElement.get()).getRoutingPoints()));
			}
		}
		if (element.getChildren() != null) {
			for (GModelElement child : element.getChildren()) {
				copyLayoutDataRecursively(child, oldIndex);
			}
		}
	}
}
