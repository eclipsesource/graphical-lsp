/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/

//TODO class is heavily based on the LayoutUtil of the sprotty repository. Change copyright header accordingly 
package com.eclipsesource.glsp.api.utils;

import java.util.ArrayList;

import com.eclipsesource.glsp.api.action.kind.ComputedBoundsAction;

import io.typefox.sprotty.api.Alignable;
import io.typefox.sprotty.api.Bounds;
import io.typefox.sprotty.api.BoundsAware;
import io.typefox.sprotty.api.Dimension;
import io.typefox.sprotty.api.ElementAndAlignment;
import io.typefox.sprotty.api.ElementAndBounds;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SEdge;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelIndex;
import io.typefox.sprotty.api.SModelRoot;

public final class LayoutUtil {

	private LayoutUtil() {
	}

	/**
	 * Apply the computed bounds from the given action to the model.
	 */
	public static void applyBounds(SModelRoot root, ComputedBoundsAction action) {
		SModelIndex index = new SModelIndex(root);
		for (ElementAndBounds b : action.getBounds()) {
			SModelElement element = index.get(b.getElementId());
			if (element instanceof BoundsAware) {
				BoundsAware bae = (BoundsAware) element;
				Bounds newBounds = b.getNewBounds();
				bae.setPosition(new Point(newBounds.getX(), newBounds.getY()));
				bae.setSize(new Dimension(newBounds.getWidth(), newBounds.getHeight()));
			}
		}
		for (ElementAndAlignment a : action.getAlignments()) {
			SModelElement element = index.get(a.getElementId());
			if (element instanceof Alignable) {
				Alignable alignable = (Alignable) element;
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
			SModelElement oldElement = oldIndex.get(element.getId());
			if (oldElement instanceof BoundsAware) {
				BoundsAware newBae = (BoundsAware) element;
				BoundsAware oldBae = (BoundsAware) oldElement;
				if (oldBae.getPosition() != null)
					newBae.setPosition(new Point(oldBae.getPosition()));
				if (oldBae.getSize() != null)
					newBae.setSize(new Dimension(oldBae.getSize()));
			}
		} else if (element instanceof SEdge) {
			SModelElement oldElement = oldIndex.get(element.getId());
			if (oldElement instanceof SEdge && ((SEdge) oldElement).getRoutingPoints() != null) {
				((SEdge) element).setRoutingPoints(new ArrayList<>(((SEdge) oldElement).getRoutingPoints()));
			}
		}
		if (element.getChildren() != null) {
			for (SModelElement child : element.getChildren()) {
				copyLayoutDataRecursively(child, oldIndex);
			}
		}
	}
}
