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
import java.util.UUID;

import com.eclipsesource.glsp.graph.GModelElement;

public abstract class GModelElementBuilder<T extends GModelElement, E extends GModelElementBuilder<T, E>>
		extends GBuilder<T> {

	protected String type;
	protected String id;
	protected String trace;
	protected List<String> cssClasses = new ArrayList<>();
	protected List<GModelElement> children = new ArrayList<>();

	public GModelElementBuilder(final String type) {
		this.type = type;
	}

	public E setId(final String id) {
		this.id = id;
		return self();
	}

	public E setType(final String type) {
		this.type = type;
		return self();
	}

	public E setTrace(String trace) {
		this.trace = trace;
		return self();
	}

	public E setCssClasses(final List<String> cssClasses) {
		return setCssClasses(cssClasses, true);
	}

	public E setCssClasses(final List<String> cssClasses, boolean replaceExisting) {
		if (this.cssClasses == null || replaceExisting) {
			this.cssClasses = cssClasses;
		} else {
			this.cssClasses.addAll(cssClasses);
		}
		return self();
	}

	public E addCssClass(String cssClass) {
		this.cssClasses.add(cssClass);
		return self();
	}

	public E addCssClasses(List<String> cssClasses) {
		this.cssClasses.addAll(cssClasses);
		return self();
	}

	public E addChild(GModelElement child) {
		this.children.add(child);
		return self();
	}

	public E addChildren(List<GModelElement> children) {
		this.children.addAll(children);
		return self();
	}

	@Override
	protected abstract T instantiate();

	protected T generateId(T element) {
		element.setId(UUID.randomUUID().toString());
		return element;
	}

	protected abstract E self();

	@Override
	protected void setProperties(final T element) {
		if (id == null) {
			generateId(element);
		} else {
			element.setId(id);
		}

		element.setType(type);
		element.setTrace(trace);
		if (cssClasses != null) {
			element.getCssClasses().addAll(cssClasses);
		}
		if (children != null) {
			element.getChildren().addAll(children);
		}
	}

	@Override
	public T build() {
		final T element = instantiate();
		setProperties(element);
		return element;
	}
}
