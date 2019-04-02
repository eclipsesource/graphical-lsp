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

import java.util.ArrayList;
import java.util.Optional;

import org.eclipse.sprotty.Alignable;
import org.eclipse.sprotty.Bounds;
import org.eclipse.sprotty.BoundsAware;
import org.eclipse.sprotty.Dimension;
import org.eclipse.sprotty.ElementAndAlignment;
import org.eclipse.sprotty.ElementAndBounds;
import org.eclipse.sprotty.Point;
import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.kind.ComputedBoundsAction;

public final class LayoutUtil {

	private LayoutUtil() {
	}

	/**
	 * Apply the computed bounds from the given action to the model.
	 */
	public static void applyBounds(SModelRoot root, ComputedBoundsAction action) {
		SModelIndex index = new SModelIndex(root);
		for (ElementAndBounds b : action.getBounds()) {
			Optional<SModelElement> element = index.get(b.getElementId());
			if (element.isPresent() && element.get() instanceof BoundsAware) {
				BoundsAware bae = (BoundsAware) element.get();
				Bounds newBounds = b.getNewBounds();
				bae.setPosition(new Point(newBounds.getX(), newBounds.getY()));
				bae.setSize(new Dimension(newBounds.getWidth(), newBounds.getHeight()));
			}
		}
		for (ElementAndAlignment a : action.getAlignments()) {
			Optional<SModelElement> element = index.get(a.getElementId());
			if (element.isPresent() && element.get() instanceof Alignable) {
				Alignable alignable = (Alignable) element.get();
				alignable.setAlignment(a.getNewAlignment());
			}
		}
	}

	/**
	 * Copy the layout of one model instance to another. Model elements are matched
	 * by their id.
	 */
	public static void copyLayoutData(SModelRoot fromRoot, SModelRoot toRoot) {
		SModelIndex oldIndex = new SModelIndex(fromRoot);
		copyLayoutDataRecursively(toRoot, oldIndex);
	}

	private static void copyLayoutDataRecursively(SModelElement element, SModelIndex oldIndex) {
		if (element instanceof BoundsAware) {
			Optional<SModelElement> oldElement = oldIndex.get(element.getId());
			if (oldElement.isPresent() && oldElement.get() instanceof BoundsAware) {
				BoundsAware newBae = (BoundsAware) element;
				BoundsAware oldBae = (BoundsAware) oldElement.get();
				if (oldBae.getPosition() != null)
					newBae.setPosition(new Point(oldBae.getPosition()));
				if (oldBae.getSize() != null)
					newBae.setSize(new Dimension(oldBae.getSize()));
			}
		} else if (element instanceof SEdge) {
			Optional<SModelElement> oldElement = oldIndex.get(element.getId());
			if (oldElement.isPresent() && oldElement.get() instanceof SEdge
					&& ((SEdge) oldElement.get()).getRoutingPoints() != null) {
				((SEdge) element).setRoutingPoints(new ArrayList<>(((SEdge) oldElement.get()).getRoutingPoints()));
			}
		}
		if (element.getChildren() != null) {
			for (SModelElement child : element.getChildren()) {
				copyLayoutDataRecursively(child, oldIndex);
			}
		}
	}
}
