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

public class ExportSVGAction extends Action {
	private String svg;

	public ExportSVGAction() {
		super(ActionKind.EXPORT_SVG);
	}

	public ExportSVGAction(String svg) {
		this();
		this.svg = svg;
	}

	public String getSvg() {
		return svg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((svg == null) ? 0 : svg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportSVGAction other = (ExportSVGAction) obj;
		if (svg == null) {
			if (other.svg != null)
				return false;
		} else if (!svg.equals(other.svg))
			return false;
		return true;
	}

}
