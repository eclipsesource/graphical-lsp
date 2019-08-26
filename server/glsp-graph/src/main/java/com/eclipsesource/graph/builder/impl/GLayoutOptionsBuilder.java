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

import com.eclipsesource.glsp.graph.GLayoutOptions;
import com.eclipsesource.glsp.graph.GraphFactory;
import com.eclipsesource.graph.builder.GBuilder;

public class GLayoutOptionsBuilder extends GBuilder<GLayoutOptions> {

	private Double paddingLeft;
	private Double paddingRight;
	private Double paddingTop;
	private Double paddingBottom;
	private Double paddingFactor;
	private boolean resizeContainer;
	private Double vGap;
	private Double hGap;
	private String vAlign;
	private String hAlign;

	private Double minWidth;
	private Double minHeight;

	public GLayoutOptionsBuilder setPaddingLeft(Double paddingLeft) {
		this.paddingLeft = paddingLeft;
		return this;
	}

	public GLayoutOptionsBuilder setPaddingRight(Double paddingRight) {
		this.paddingRight = paddingRight;
		return this;
	}

	public GLayoutOptionsBuilder setPaddingTop(Double paddingTop) {
		this.paddingTop = paddingTop;
		return this;
	}

	public GLayoutOptionsBuilder setPaddingBottom(Double paddingBottom) {
		this.paddingBottom = paddingBottom;
		return this;
	}

	public GLayoutOptionsBuilder setPaddingFactor(Double paddingFactor) {
		this.paddingFactor = paddingFactor;
		return this;
	}

	public GLayoutOptionsBuilder setResizeContainer(boolean resizeContainer) {
		this.resizeContainer = resizeContainer;
		return this;
	}

	public GLayoutOptionsBuilder setVGap(Double vGap) {
		this.vGap = vGap;
		return this;
	}

	public GLayoutOptionsBuilder setHGap(Double hGap) {
		this.hGap = hGap;
		return this;
	}

	public GLayoutOptionsBuilder setVAlign(String vAlign) {
		this.vAlign = vAlign;
		return this;
	}

	public GLayoutOptionsBuilder setHAlign(String hAlign) {
		this.hAlign = hAlign;
		return this;
	}

	public GLayoutOptionsBuilder setMinWidth(Double minWidth) {
		this.minWidth = minWidth;
		return this;
	}

	public GLayoutOptionsBuilder setMinHeight(Double minHeight) {
		this.minHeight = minHeight;
		return this;
	}

	@Override
	protected GLayoutOptions instantiate() {
		return GraphFactory.eINSTANCE.createGLayoutOptions();
	}

	@Override
	protected void setProperties(GLayoutOptions element) {
		element.setPaddingLeft(paddingLeft);
		element.setPaddingRight(paddingRight);
		element.setPaddingTop(paddingTop);
		element.setPaddingBottom(paddingBottom);
		element.setPaddingFactor(paddingFactor);
		element.setResizeContainer(resizeContainer);
		element.setVGap(vGap);
		element.setHGap(hGap);
		element.setVAlign(vAlign);
		element.setHAlign(hAlign);
		element.setMinHeight(minHeight);
		element.setMinWidth(minWidth);
	}

}
