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
package com.eclipsesource.glsp.api.action.kind;

import java.util.Arrays;

import com.eclipsesource.glsp.api.action.Action;

import io.typefox.sprotty.api.ElementAndAlignment;
import io.typefox.sprotty.api.ElementAndBounds;

public class ComputedBoundsAction extends Action {

	private ElementAndBounds[] bounds;
	private ElementAndAlignment[] alignments;
	private int revision;

	public ComputedBoundsAction() {
		super(ActionKind.COMPUTED_BOUNDS);
	}

	public ComputedBoundsAction(ElementAndBounds[] bounds, int revision, ElementAndAlignment[] alignments) {
		this();
		this.bounds = bounds;
		this.revision = revision;
		this.alignments = alignments;
	}

	public ElementAndBounds[] getBounds() {
		return bounds;
	}

	public ElementAndAlignment[] getAlignments() {
		return alignments;
	}

	public int getRevision() {
		return revision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(alignments);
		result = prime * result + Arrays.hashCode(bounds);
		result = prime * result + revision;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComputedBoundsAction other = (ComputedBoundsAction) obj;
		if (!Arrays.equals(alignments, other.alignments))
			return false;
		if (!Arrays.equals(bounds, other.bounds))
			return false;
		if (revision != other.revision)
			return false;
		return true;
	}
}
