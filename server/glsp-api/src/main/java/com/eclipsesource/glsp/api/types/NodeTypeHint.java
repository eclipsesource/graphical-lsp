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

import java.util.List;

public class NodeTypeHint extends ElementTypeHint {
	private boolean resizable;
	private boolean reparentable;
	private List<String> containableElementTypeIds;

	public NodeTypeHint() {
	}

	public NodeTypeHint(String elementTypeId, boolean repositionable, boolean deletable, boolean resizable,
			boolean reparentable, List<String> containableElementTypeIds) {
		super(elementTypeId, repositionable, deletable);
		this.resizable = resizable;
		this.containableElementTypeIds = containableElementTypeIds;
		this.reparentable=reparentable;

	}

	public NodeTypeHint(String elementTypeId, boolean repositionable, boolean deletable, boolean resizable,
			boolean reparentable) {
		this(elementTypeId, reparentable, deletable, resizable, reparentable, null);
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizeable) {
		this.resizable = resizeable;
	}

	public List<String> getContainableElementTypeIds() {
		return containableElementTypeIds;
	}

	public void setContainableElementTypeIds(List<String> containableElementTypeIds) {
		this.containableElementTypeIds = containableElementTypeIds;
	}

	public boolean isReparentable() {
		return reparentable;
	}

	public void setReparentable(boolean reparentable) {
		this.reparentable = reparentable;
	}

}
