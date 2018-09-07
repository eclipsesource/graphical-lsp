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

public class FitToScreenAction extends Action {
	private String[] elementIds;
	private double padding;
	private double maxZoom;
	private boolean animate;

	public FitToScreenAction() {
		super(ActionKind.FIT_TO_SCREEN);
	}

	public FitToScreenAction(String[] elementIds, double padding, double maxZoom, boolean animate) {
		this();
		this.elementIds = elementIds;
		this.padding = padding;
		this.maxZoom = maxZoom;
		this.animate = animate;
	}

	public String[] getElementIds() {
		return elementIds;
	}

	public double getPadding() {
		return padding;
	}

	public double getMaxZoom() {
		return maxZoom;
	}

	public boolean isAnimate() {
		return animate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (animate ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(elementIds);
		long temp;
		temp = Double.doubleToLongBits(maxZoom);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(padding);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		FitToScreenAction other = (FitToScreenAction) obj;
		if (animate != other.animate)
			return false;
		if (!Arrays.equals(elementIds, other.elementIds))
			return false;
		if (Double.doubleToLongBits(maxZoom) != Double.doubleToLongBits(other.maxZoom))
			return false;
		if (Double.doubleToLongBits(padding) != Double.doubleToLongBits(other.padding))
			return false;
		return true;
	}

}
