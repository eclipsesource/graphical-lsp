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
package com.eclipsesource.glsp.api.types;

import org.eclipse.sprotty.SModelElement;

public class Match {
	private SModelElement left;
	private SModelElement right;
	private String leftParentId;
	private String rightParentId;

	public Match() {

	}

	public Match(SModelElement left, SModelElement right, String leftParentId, String rightParentId) {
		super();
		this.left = left;
		this.right = right;
		this.leftParentId = leftParentId;
		this.rightParentId = rightParentId;
	}

	public SModelElement getLeft() {
		return left;
	}

	public SModelElement getRight() {
		return right;
	}

	public String getLeftParentId() {
		return leftParentId;
	}

	public String getRightParentId() {
		return rightParentId;
	}

	public void setLeft(SModelElement left) {
		this.left = left;
	}

	public void setRight(SModelElement right) {
		this.right = right;
	}

	public void setLeftParentId(String leftParentId) {
		this.leftParentId = leftParentId;
	}

	public void setRightParentId(String rightParentId) {
		this.rightParentId = rightParentId;
	}

}
