package com.eclipsesource.graph.builder;

import com.eclipsesource.glsp.graph.GPreRenderedElement;

public abstract class AbstractGPreRenderedElementBuilder<T extends GPreRenderedElement, E extends AbstractGPreRenderedElementBuilder<T, E>>
		extends GModelElementBuilder<T, E> {
	protected String code;

	public AbstractGPreRenderedElementBuilder(String type) {
		super(type);

	}

	public E setCode(String code) {
		this.code = code;
		return self();
	}

	@Override
	protected void setProperties(T preRendered) {
		super.setProperties(preRendered);
		preRendered.setCode(code);
	}

}
