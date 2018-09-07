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

import com.eclipsesource.glsp.api.action.Action;

public class CollapseExpandAllAction extends Action {
	public boolean expand = true;

	public CollapseExpandAllAction() {
		super(ActionKind.COLLAPSE_EXPAND_ALL);

	}

	public CollapseExpandAllAction(boolean expand) {
		this();
		this.expand = expand;
	}

	public boolean isExpand() {
		return expand;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (expand ? 1231 : 1237);
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
		CollapseExpandAllAction other = (CollapseExpandAllAction) obj;
		if (expand != other.expand)
			return false;
		return true;
	}

}
