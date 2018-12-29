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
package com.eclipsesource.glsp.api.action.kind;

import java.util.List;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.types.EdgeTypeHint;
import com.eclipsesource.glsp.api.types.NodeTypeHint;

public class SetTypeHintsAction extends Action {

	private List<NodeTypeHint> nodeHints;
	private List<EdgeTypeHint> edgeHints;

	public SetTypeHintsAction() {
		super(Action.Kind.SET_TYPE_HINTS);
	}

	public SetTypeHintsAction(List<NodeTypeHint> nodeHints, List<EdgeTypeHint> edgeHints) {
		this();
		this.nodeHints = nodeHints;
		this.edgeHints = edgeHints;
	}

	public List<NodeTypeHint> getNodeHints() {
		return nodeHints;
	}

	public void setNodeHints(List<NodeTypeHint> nodeHints) {
		this.nodeHints = nodeHints;
	}

	public List<EdgeTypeHint> getEdgeHints() {
		return edgeHints;
	}

	public void setEdgeHints(List<EdgeTypeHint> edgeHints) {
		this.edgeHints = edgeHints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((edgeHints == null) ? 0 : edgeHints.hashCode());
		result = prime * result + ((nodeHints == null) ? 0 : nodeHints.hashCode());
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
		SetTypeHintsAction other = (SetTypeHintsAction) obj;
		if (edgeHints == null) {
			if (other.edgeHints != null)
				return false;
		} else if (!edgeHints.equals(other.edgeHints))
			return false;
		if (nodeHints == null) {
			if (other.nodeHints != null)
				return false;
		} else if (!nodeHints.equals(other.nodeHints))
			return false;
		return true;
	}

	
}
