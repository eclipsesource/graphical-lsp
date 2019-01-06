/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.types;

import java.util.List;

public class NodeTypeHint extends ElementTypeHint {
	private boolean resizable;
	private List<String> containableElementTypeIds;

	public NodeTypeHint() {
	}

	public NodeTypeHint(String elementTypeId, boolean repositionable, boolean deletable, boolean resizable,
			List<String> containableElementTypeIds) {
		super(elementTypeId, repositionable, deletable);
		this.resizable = resizable;
		this.containableElementTypeIds = containableElementTypeIds;
	}

	public NodeTypeHint(String elementTypeId, boolean repositionable, boolean deletable, boolean resizable) {
		super(elementTypeId, repositionable, deletable);
		this.resizable = resizable;
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
}
