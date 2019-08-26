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
package com.eclipsesource.graph.builder;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.glsp.graph.GIssue;
import com.eclipsesource.glsp.graph.GIssueMarker;

public abstract class AbstractGIssueMarkerBuilder<T extends GIssueMarker, E extends AbstractGIssueMarkerBuilder<T, E>>
		extends GShapeElementBuilder<T, E> {

	protected List<GIssue> issues = new ArrayList<>();

	public AbstractGIssueMarkerBuilder(String type) {
		super(type);
	}

	public E setIssues(List<GIssue> issues) {
		this.issues = issues;
		return self();
	}

	public E addIssues(List<GIssue> issues) {
		if (this.issues == null) {
			return setIssues(issues);
		} else {
			this.issues.addAll(issues);
			return self();
		}
	}

	@Override
	protected void setProperties(T issueMarker) {
		super.setProperties(issueMarker);
		issueMarker.getIssues().addAll(issues);
	}

}
