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
package com.eclipsesource.glsp.api.action.kind;

import java.util.Arrays;

import com.eclipsesource.glsp.api.action.Action;

public class CollapseExpandAction extends Action {

	private String[] expandIds;
	private String[] collapseIds;
	private boolean collapse = true;

	public CollapseExpandAction() {
		super(Action.Kind.COLLAPSE_EXPAND);
	}

	public CollapseExpandAction(String[] expandIds, String[] collapseIds, boolean collapse) {
		this();
		this.expandIds = expandIds;
		this.collapseIds = collapseIds;
		this.collapse = collapse;
	}

	public String[] getExpandIds() {
		return expandIds;
	}

	public String[] getCollapseIds() {
		return collapseIds;
	}

	public boolean isCollapse() {
		return collapse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (collapse ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(collapseIds);
		result = prime * result + Arrays.hashCode(expandIds);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollapseExpandAction other = (CollapseExpandAction) obj;
		if (collapse != other.collapse)
			return false;
		if (!Arrays.equals(collapseIds, other.collapseIds))
			return false;
		if (!Arrays.equals(expandIds, other.expandIds))
			return false;
		return true;
	}

}
