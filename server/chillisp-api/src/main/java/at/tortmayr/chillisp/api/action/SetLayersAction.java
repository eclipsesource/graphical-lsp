/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.chillisp.api.action;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionKind;
import at.tortmayr.chillisp.api.utils.Layer;

public class SetLayersAction extends Action {
	private Layer[] layers;

	public SetLayersAction() {
		super(ActionKind.SET_LAYERS);
	}

	public SetLayersAction(Layer[] layers) {
		this();
		this.layers = layers;
	}

	public Layer[] getLayers() {
		return layers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(layers);
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
		SetLayersAction other = (SetLayersAction) obj;
		if (!Arrays.equals(layers, other.layers))
			return false;
		return true;
	}

}
