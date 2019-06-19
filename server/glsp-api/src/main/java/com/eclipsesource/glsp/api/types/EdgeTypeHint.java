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
