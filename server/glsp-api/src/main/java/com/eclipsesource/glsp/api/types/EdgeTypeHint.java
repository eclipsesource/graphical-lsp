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

public class EdgeTypeHint extends ElementTypeHint {
	private boolean routable;
	private List<String> sourceElementTypeIds;
	private List<String> targetElementTypeIds;

	public EdgeTypeHint() {
	}

	public EdgeTypeHint(String elementTypeId, boolean repositionable, boolean deletable, boolean routable,
			List<String> sourceElementTypeIds, List<String> targetElementTypeIds) {
		super(elementTypeId, repositionable, deletable);
		this.routable = routable;
		this.sourceElementTypeIds = sourceElementTypeIds;
		this.targetElementTypeIds = targetElementTypeIds;
	}

	public boolean isRoutable() {
		return routable;
	}

	public void setRoutable(boolean routable) {
		this.routable = routable;
	}

	public List<String> getSourceElementTypeIds() {
		return sourceElementTypeIds;
	}

	public void setSourceElementTypeIds(List<String> sourceElementTypeIds) {
		this.sourceElementTypeIds = sourceElementTypeIds;
	}

	public List<String> getTargetElementTypeIds() {
		return targetElementTypeIds;
	}

	public void setTargetElementTypeIds(List<String> targetElementTypeIds) {
		this.targetElementTypeIds = targetElementTypeIds;
	}

}
